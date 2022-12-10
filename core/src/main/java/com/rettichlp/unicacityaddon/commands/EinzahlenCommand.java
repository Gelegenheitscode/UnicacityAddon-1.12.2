package com.rettichlp.unicacityaddon.commands;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.modules.CashMoneyModule;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class EinzahlenCommand extends Command {

    private static final String usage = "/einzahlen";

    @Inject
    private EinzahlenCommand() {
        super("einzahlen");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (CashMoneyModule.cashBalance > 0)
            p.sendChatMessage("/bank einzahlen " + CashMoneyModule.cashBalance);
        else
            p.sendErrorMessage("Du hast kein Geld auf der Hand!");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments).build();
    }
}