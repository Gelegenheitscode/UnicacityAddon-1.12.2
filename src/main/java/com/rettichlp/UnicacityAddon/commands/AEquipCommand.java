package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class AEquipCommand implements IClientCommand {

    public static int amount = 0;

    @Override @Nonnull public String getName() {
        return "aequip";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/aequip [Menge]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return ForgeUtils.getOnlinePlayers();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length > 0) {
            if (!MathUtils.isInteger(args[0])) {
                p.sendSyntaxMessage(getUsage(sender));
                return;
            }
            amount = Integer.parseInt(args[0]);
        }

        p.sendInfoMessage("Menge für AEquip wurde eingestellt.");
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}