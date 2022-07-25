package com.rettichlp.UnicacityAddon.commands.faction.terrorist;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.modules.ExplosiveBeltTimerModule;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

/**
 * @author Dimiikou
 **/
public class ExplosiveBeltCommand extends CommandBase {

    @Override @Nonnull
    public String getName() {
        return "sprenggürtel";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/sprenggürtel [Countdown]";
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) { return true; }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        // /sprenggürtel 10
        ExplosiveBeltTimerModule.explosiveBeltStarted = true;

        try {
            ExplosiveBeltTimerModule.currentCount = parseInt(args[0]);
        } catch (NumberInvalidException e) {
            Message.getBuilder().error().space().of("Der Countdown muss eine Zahl sein.");
            return;
        }

        ExplosiveBeltTimerModule.timer = args[0];
        AbstractionLayer.getPlayer().sendChatMessage("/sprenggürtel " + args[0]);
    }
}
