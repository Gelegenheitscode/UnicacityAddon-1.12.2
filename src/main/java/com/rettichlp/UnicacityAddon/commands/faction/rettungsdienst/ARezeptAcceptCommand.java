package com.rettichlp.UnicacityAddon.commands.faction.rettungsdienst;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * @author RettichLP
 */
public class ARezeptAcceptCommand extends CommandBase {

    public static int amount = 0;

    @Override @Nonnull public String getName() {
        return "arezeptannehmen";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/arezeptannehmen [Anzahl]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.singletonList("arannehmen");
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            Message.getBuilder()
                    .error()
                    .space()
                    .of("Syntax: " + getUsage(sender)).color(ColorCode.GRAY).advance()
                    .sendTo(p.getPlayer());
            return;
        }

        if (!MathUtils.isInteger(args[0])) return;
        amount = Integer.parseInt(args[0]) - 1;

        p.acceptOffer();
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}