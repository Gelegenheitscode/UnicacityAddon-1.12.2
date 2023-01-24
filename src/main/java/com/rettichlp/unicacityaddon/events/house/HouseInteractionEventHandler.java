package com.rettichlp.unicacityaddon.events.house;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class HouseInteractionEventHandler {

    /**
     * Progress array holds data for akku and heal progresses:
     * <ol>
     *     <li>akku progress</li>
     *     <li>heal progress</li>
     * </ol>
     */
    public static int[] progress = {-1, -1};

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = e.getMessage().getUnformattedText();

        Matcher houseAkkuMatcher = PatternHandler.HOUSE_AKKU_PATTERN.matcher(msg);
        if (houseAkkuMatcher.find()) {
            progress[0] = 0;
            setMessage(progress[0]);
        }

        Matcher houseHealMatcher = PatternHandler.HOUSE_HEAL_PATTERN.matcher(msg);
        if (houseHealMatcher.find()) {
            progress[1] = 0;
            setMessage(progress[1]);
        }
    }

    public static void increaseProgress(int progressIndex) {
        switch (progress[progressIndex]) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                progress[progressIndex]++;
                setMessage(progress[progressIndex]);
                break;
            case 10:
                progress[progressIndex] = -1;
                break;
        }
    }

    private static void setMessage(int progress) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder
                    .append(i < progress ? ColorCode.GREEN.getCode() : ColorCode.GRAY.getCode())
                    .append("█");
        }
        UnicacityAddon.MINECRAFT.ingameGUI.setOverlayMessage(stringBuilder.toString(), true);
    }
}