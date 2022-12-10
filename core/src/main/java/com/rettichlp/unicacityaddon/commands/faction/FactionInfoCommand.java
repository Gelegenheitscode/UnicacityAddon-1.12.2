package com.rettichlp.unicacityaddon.commands.faction;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.events.faction.FactionInfoEventHandler;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.client.chat.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/commands/faction/CheckActiveMembersCommand.java">UCUtils by paulzhng</a>
 */
@SuppressWarnings("UnstableApiUsage")
@UCCommand
public class FactionInfoCommand extends Command {

    private static final String usage = "/checkactivemembers [Fraktion]";

    @Inject
    private FactionInfoCommand() {
        super("checkactivemembers", "cam");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        List<Faction> chosenFactions = new ArrayList<>();

        for (String s : arguments) {
            Faction faction = Faction.getFactionByFactionKey(s);
            if (faction == null)
                continue;
            chosenFactions.add(faction);
        }

        if (chosenFactions.isEmpty())
            chosenFactions.addAll(Arrays.asList(Faction.values()));

        p.sendMessage(Message.getBuilder().of("Aktive Spieler in den Fraktionen:").color(ColorCode.DARK_AQUA).bold().advance().createComponent());

        Thread thread = new Thread(() -> chosenFactions.forEach(faction -> {
            if (faction.equals(Faction.NULL))
                return;
            Map<Boolean, Integer> members = getMembers(faction);
            int activeMembers = members.get(true);
            int inactiveMembers = members.get(false);
            p.sendMessage(Message.getBuilder()
                    .of("  » ").color(ColorCode.GRAY).advance()
                    .of(faction.getDisplayName())
                    .color(ColorCode.AQUA)
                    .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder()
                            .of("/memberinfo " + faction.getFactionKey()).color(ColorCode.RED).advance()
                            .createComponent())
                    .clickEvent(ClickEvent.Action.RUN_COMMAND, "/memberinfo " + faction.getFactionKey())
                    .advance()
                    .space()
                    .of(String.valueOf(activeMembers)).color(ColorCode.GREEN).advance()
                    .of("/").color(ColorCode.DARK_GRAY).advance()
                    .of(String.valueOf(activeMembers + inactiveMembers)).color(ColorCode.GRAY).advance()
                    .createComponent());
        }));
        thread.start();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addToAllFromIndex(1, Arrays.stream(Faction.values()).map(Faction::getFactionKey).sorted().collect(Collectors.toList()))
                .build();
    }

    private Map<Boolean, Integer> getMembers(Faction faction) {
        FactionInfoEventHandler.future = new CompletableFuture<>();
        AbstractionLayer.getPlayer().sendChatMessage("/memberinfo " + faction.getFactionKey());

        return new HashMap<>();

// TODO: 11.12.2022
//        try {
//            return Uninterruptibles.getUninterruptibly(FactionInfoEventHandler.future);
//        } catch (ExecutionException e) {
//            throw new IllegalStateException(e);
//        } finally {
//            FactionInfoEventHandler.future = null;
//        }
    }
}