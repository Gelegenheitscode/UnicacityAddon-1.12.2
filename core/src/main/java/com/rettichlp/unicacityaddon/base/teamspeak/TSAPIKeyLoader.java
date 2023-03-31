package com.rettichlp.unicacityaddon.base.teamspeak;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import jdk.internal.joptsimple.internal.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fuzzlemann
 */
public class TSAPIKeyLoader {

    private final String[] teamSpeakClientNames = new String[]{"TS3Client", "TeamSpeak 3 Client", "TeamSpeak", "TeamSpeak 3", "ts3"};
    private final List<File> possibleConfigDirectories = new ArrayList<>();

    public void load(UnicacityAddon unicacityAddon) throws IOException {
        if (!unicacityAddon.configuration().tsApiKey().getOrDefault(Strings.EMPTY).isEmpty())
            return;

        loadPossibleConfigDirectories();
        for (File possibleConfigDirectory : possibleConfigDirectories) {
            File clientQueryIni = new File(possibleConfigDirectory, "clientquery.ini");
            if (!clientQueryIni.exists())
                continue;

            String apiKey = loadAPIKey(clientQueryIni);
            if (apiKey == null)
                continue;

            unicacityAddon.configuration().tsApiKey().set(apiKey);
            return;
        }
    }

    private String loadAPIKey(File clientQueryIni) throws IOException {
        for (String line : FileUtils.readLines(clientQueryIni, StandardCharsets.UTF_8)) {
            if (!line.startsWith("api_key"))
                continue;

            String apiKey = line.split("=")[1];
            if (apiKey.length() != 29)
                continue;

            return apiKey;
        }

        return null;
    }

    private void loadPossibleConfigDirectories() {
        List<File> possibleParentFolders = new ArrayList<>();

        if (SystemUtils.IS_OS_WINDOWS) {
            File appData = new File(System.getenv("AppData"));
            File local = new File(appData.getParentFile(), "Local");
            File programFilesX86 = new File(System.getenv("ProgramFiles(X86)"));
            File programFiles = new File(System.getenv("ProgramFiles"));

            possibleParentFolders.add(appData);
            possibleParentFolders.add(local);
            possibleParentFolders.add(programFilesX86);
            possibleParentFolders.add(programFiles);
        } else {
            File userHome = new File(System.getProperty("user.home"));
            File library = new File(userHome, "Library");
            File applicationSupport = new File(library, "Application Support");

            possibleParentFolders.add(userHome);
            possibleParentFolders.add(library);
            possibleParentFolders.add(applicationSupport);
        }

        List<File> possibleTeamSpeakFolders = new ArrayList<>();
        for (File possibleParentFolder : possibleParentFolders) {
            for (String teamSpeakClientName : teamSpeakClientNames) {
                possibleTeamSpeakFolders.add(new File(possibleParentFolder, teamSpeakClientName));
            }
        }

        for (File possibleTeamSpeakFolder : possibleTeamSpeakFolders) {
            possibleConfigDirectories.add(new File(possibleTeamSpeakFolder, "config"));
            possibleConfigDirectories.add(possibleTeamSpeakFolder);
        }

        possibleConfigDirectories.removeIf(config -> !config.exists());
    }
}
