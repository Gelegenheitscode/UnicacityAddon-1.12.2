package com.rettichlp.unicacityaddon.commands.house;

import com.google.inject.Inject;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.manager.HouseDataManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class HouseStorageCommand extends Command {

    private static final String usage = "/drogenlagerinfo";

    @Inject
    private HouseStorageCommand() {
        super("drogenlagerinfo", "dlagerinfo", "drugstorage");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if (arguments.length > 1 && arguments[0].equalsIgnoreCase("delete") && MathUtils.isInteger(arguments[1])) {
            HouseDataManager.deleteHouseData(Integer.parseInt(arguments[1]));
            return true;
        }

        HouseDataManager.sendAllDrugStorageMessage();
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(arguments)
                .addAtIndex(1, "delete")
                .build();
    }
}