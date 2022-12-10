package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.events.ShutDownEventHandler;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ShutdownJailCommand extends Command {

    private static final String usage = "/shutdownjail";

    @Inject
    private ShutdownJailCommand() {
        super("shutdownjail");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();
        ShutDownEventHandler.shutdownJail = !ShutDownEventHandler.shutdownJail;

        if (ShutDownEventHandler.shutdownJail)
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Dein Computer fährt nun herunter sobald du entlassen bist.").color(ColorCode.GRAY).advance()
                    .createComponent());
        else
            p.sendMessage(Message.getBuilder()
                    .prefix()
                    .of("Dein Computer fährt nun").color(ColorCode.GRAY).advance().space()
                    .of("nichtmehr").color(ColorCode.RED).advance().space()
                    .of("herunter sobald du entlassen bist.").color(ColorCode.GRAY).advance()
                    .createComponent());
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}