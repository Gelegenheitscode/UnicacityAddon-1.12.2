package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
import com.rettichlp.UnicacityAddon.base.utils.TextUtils;
import com.rettichlp.UnicacityAddon.events.MobileEventHandler;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

/**
 * @author RettichLP
 */
public class ASMSCommand extends CommandBase {

    final Timer timer = new Timer();
    public static boolean isActive;

    @Override @Nonnull public String getName() {
        return "asms";
    }

    @Override @Nonnull public String getUsage(@Nonnull ICommandSender sender) {
        return "/asms [Spielername] [Nachricht]";
    }

    @Override @Nonnull public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();
        if (args.length < 2) p.sendMessage(Message.getBuilder()
                .error()
                .of("Syntax: " + getUsage(sender)).color(ColorCode.GRAY).advance()
                .createComponent());

        isActive = true;
        p.sendChatMessage("/nummer " + args[0]);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int number = MobileEventHandler.lastCheckedNumber;
                if (number == 0) {
                    p.sendMessage(Message.getBuilder()
                            .error()
                            .of("Der Spieler wurde nicht gefunden.").color(ColorCode.GRAY).advance()
                            .createComponent());
                    return;
                }

                String message = TextUtils.makeStringByArgs(args, " ").replace(args[0], "");
                p.sendChatMessage("/sms " + number + message);
            }
        }, 250L);
    }

    @Override @Nonnull public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        String input = args[args.length - 1].toLowerCase().replace('-', ' ');
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }
}