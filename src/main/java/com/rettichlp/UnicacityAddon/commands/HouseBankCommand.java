package com.rettichlp.UnicacityAddon.commands;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.request.TabCompletionBuilder;
import com.rettichlp.UnicacityAddon.base.json.HouseBankEntry;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.MathUtils;
import com.rettichlp.UnicacityAddon.events.HouseBankEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dimiikou
 */
@UCCommand
public class HouseBankCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "hauskasseninfo";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/hauskasseninfo";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Arrays.asList("hkasseninfo");
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args, @Nullable BlockPos targetPos) {
        return TabCompletionBuilder.getBuilder(args)
                .addAtIndex(1, "delete")
                .build();
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("delete") && MathUtils.isInteger(args[1])) {
                for (HouseBankEntry houseBankEntry : HouseBankEventHandler.houseBanks) {
                    if (houseBankEntry.getHouseNumber() == Integer.parseInt(args[1])) {
                        HouseBankEventHandler.houseBanks.remove(houseBankEntry);
                        break;
                    }
                }
                return;
            }
        }

        houseBanks();
    }

    private void houseBanks() {
        UPlayer p = AbstractionLayer.getPlayer();
        p.sendEmptyMessage();
        p.sendMessage(Message.getBuilder()
                .of("Hauskassen:").color(ColorCode.DARK_AQUA).bold().advance()
                .createComponent());
        HouseBankEventHandler.houseBanks.forEach(houseBankEntry -> p.sendMessage(Message.getBuilder()
                .of("» " + houseBankEntry.getHouseNumber() + ": ").color(ColorCode.GRAY).advance()
                .of(houseBankEntry.getValue() + "$").color(ColorCode.AQUA).advance()
                .createComponent()));
        p.sendEmptyMessage();
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public int compareTo(@Nonnull ICommand o) {
        return 0;
    }
}