package com.rettichlp.UnicacityAddon.events.teamspeak;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ClientVariableCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.events.ClientMovedEvent;
import de.fuzzlemann.ucutils.Main;
import de.fuzzlemann.ucutils.base.abstraction.AbstractionLayer;
import de.fuzzlemann.ucutils.base.text.Message;
import de.fuzzlemann.ucutils.config.UCUtilsConfig;
import de.fuzzlemann.ucutils.teamspeak.commands.ClientVariableCommand;
import de.fuzzlemann.ucutils.teamspeak.events.ClientMovedEvent;
import de.fuzzlemann.ucutils.utils.faction.Faction;
import de.fuzzlemann.ucutils.utils.sound.SoundUtil;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Fuzzlemann
 */
@Mod.EventBusSubscriber
public class WaitingRoomEventHandler {

    private static final Table<Integer, Integer, Long> COOLDOWN_TABLE = HashBasedTable.create();

    @SubscribeEvent
    public static void onClientMoved(ClientMovedEvent e) {
        if (!AbstractionLayer.getPlayer().isConnected()) return;

        boolean supportNotification = UCUtilsConfig.notifyWaitingSupport;
        boolean publicNotification = UCUtilsConfig.notifyWaitingPublic;
        if (!supportNotification && !publicNotification) return;

        int targetChannelID = e.getTargetChannelID();
        boolean support;
        if (targetChannelID == 41) {
            if (!supportNotification) return;
            support = true;
        } else if (Faction.getFactionOfPlayer() != null && targetChannelID == Faction.getFactionOfPlayer().getPublicChannelID()) {
            if (!publicNotification) return;
            support = false;
        } else {
            return;
        }

        int clientID = e.getClientID();

        Map<Integer, Long> row = COOLDOWN_TABLE.row(clientID);
        Long lastTime = row.get(targetChannelID);
        if (lastTime != null && (System.currentTimeMillis() - lastTime) < TimeUnit.MINUTES.toMillis(5)) return;

        COOLDOWN_TABLE.put(clientID, targetChannelID, System.currentTimeMillis());

        new Thread(() -> {
            ClientVariableCommand.Response response = new ClientVariableCommand(clientID).getResponse();
            String name = response.getDescription();

            Message.Builder builder = Message.builder()
                    .prefix()
                    .of(name).color(TextFormatting.BLUE).advance()
                    .space();

            if (support) {
                builder.of("hat das Wartezimmer betreten.").color(TextFormatting.GRAY).advance();
            } else {
                builder.of("hat den Öffentlich-Channel betreten.").color(TextFormatting.GRAY).advance()
                        .space()
                        .of("[↑]").color(TextFormatting.BLUE)
                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.builder().of("Betritt den Öffentlich-Channel").color(TextFormatting.GRAY).advance().build())
                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/tsjoin Öffentlich")
                        .advance()
                        .space();
            }

            builder.space()
                    .of("[↓]").color(TextFormatting.BLUE)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.builder().of("Move ").color(TextFormatting.GRAY).advance().of(name).color(TextFormatting.BLUE).advance().of(" zu dir").color(TextFormatting.GRAY).advance().build())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/movehere " + name).advance()
                    .send();

            Main.MINECRAFT.addScheduledTask(() -> AbstractionLayer.getPlayer().playSound(Objects.requireNonNull(SoundUtil.getSoundEvent("block.note.pling")), 1, 1));
        }).start();
    }
}
