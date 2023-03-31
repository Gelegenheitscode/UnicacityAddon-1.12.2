package com.rettichlp.unicacityaddon.commands.faction;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.AddonPlayer;
import com.rettichlp.unicacityaddon.base.builder.TabCompletionBuilder;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCCommand;
import com.rettichlp.unicacityaddon.base.utils.MathUtils;
import net.labymod.api.client.chat.command.Command;

import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class AEquipCommand extends Command {

    public static int amount = 0;

    private static final String usage = "/aequip [Menge]";

    private final UnicacityAddon unicacityAddon;

    public AEquipCommand(UnicacityAddon unicacityAddon) {
        super("aequip");
        this.unicacityAddon = unicacityAddon;
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        AddonPlayer p = this.unicacityAddon.player();

        if (arguments.length > 0) {
            if (!MathUtils.isInteger(arguments[0])) {
                p.sendSyntaxMessage(usage);
                return true;
            }
            amount = Integer.parseInt(arguments[0]);
        }

        p.sendInfoMessage("Menge für AEquip wurde eingestellt.");
        return true;
    }

    @Override
    public List<String> complete(String[] arguments) {
        return TabCompletionBuilder.getBuilder(this.unicacityAddon, arguments).build();
    }
}