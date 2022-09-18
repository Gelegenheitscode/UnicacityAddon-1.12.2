package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.events.ShutDownEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class ShutdownJailCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "shutdownjail";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/shutdownjail";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("shutdownj");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        ShutDownEventHandler.shutdownJail = !ShutDownEventHandler.shutdownJail;

        if (ShutDownEventHandler.shutdownJail)
            Message.getBuilder().prefix().of("Dein Computer fährt nun herunter sobald du entlassen bist.").color(ColorCode.GRAY)
                    .advance().sendTo(p.getPlayer());
        else
            Message.getBuilder().prefix().of("Dein Computer fährt nun").color(ColorCode.GRAY).advance().space()
                    .of("nichtmehr").color(ColorCode.RED).advance().space()
                    .of("herunter sobald du entlassen bist.").color(ColorCode.GRAY)
                    .advance().sendTo(p.getPlayer());
    }
}