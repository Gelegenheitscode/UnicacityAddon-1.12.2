package com.rettichlp.UnicacityAddon.commands.teamspeak;

import com.rettichlp.UnicacityAddon.base.teamspeak.CommandResponse;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientMoveCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Client;
import de.fuzzlemann.ucutils.base.command.Command;
import de.fuzzlemann.ucutils.base.command.CommandParam;
import de.fuzzlemann.ucutils.base.text.TextUtils;
import de.fuzzlemann.ucutils.teamspeak.CommandResponse;
import de.fuzzlemann.ucutils.teamspeak.TSUtils;
import de.fuzzlemann.ucutils.teamspeak.commands.ClientMoveCommand;
import de.fuzzlemann.ucutils.teamspeak.objects.Client;
import de.fuzzlemann.ucutils.utils.mcapi.MojangAPI;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fuzzlemann
 */
@SideOnly(Side.CLIENT)
public class MoveCommand {

    @Command(value = "move", usage = "/%label% [Spieler...] [Ziel]", async = true)
    public boolean onCommand(@CommandParam(arrayStart = true) String[] moveArray, String moveTo) {
        List<String> moved = new ArrayList<>();
        for (String move : moveArray) {
            moved.addAll(MojangAPI.getEarlierNames(move));
        }

        List<Client> clientsMoved = TSUtils.getClientsByName(moved);
        List<Client> clientsMoveTo = TSUtils.getClientsByName(MojangAPI.getEarlierNames(moveTo));

        if (clientsMoved.isEmpty() || clientsMoveTo.isEmpty()) {
            TextUtils.error("Einer der Spieler befindet sich nicht auf dem TeamSpeak.");
            return true;
        }

        Client moveToClient = clientsMoveTo.get(0);
        CommandResponse response = new ClientMoveCommand(moveToClient.getChannelID(), clientsMoved).getResponse();
        if (!response.succeeded()) {
            TextUtils.error("Das Moven ist fehlgeschlagen.");
            return true;
        }

        TextUtils.simpleMessage("Du hast die Personen gemoved.");
        return true;
    }
}
