package com.rettichlp.UnicacityAddon.commands.teamspeak;

import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.config.ConfigElements;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.TSUtils;
import com.rettichlp.UnicacityAddon.base.teamspeak.commands.ChannelListCommand;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Channel;
import com.rettichlp.UnicacityAddon.base.teamspeak.objects.Client;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import com.rettichlp.UnicacityAddon.base.utils.ForgeUtils;
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
 * @author Fuzzlemann, RettichLP
 */
@UCCommand
public class TSFindCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "tsfind";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/tsfind [Spieler]";
    }

    @Override
    @Nonnull
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length < 1) {
            p.sendSyntaxMessage(getUsage(sender));
            return;
        }

        if (!ConfigElements.getTeamspeakAPIKey().matches("([A-Z0-9]{4}(-*)){6}")) {
            p.sendErrorMessage("Teamspeak API Key ist nicht gültig!");
            return;
        }

        String name = args[0];

        List<Client> clients = TSUtils.getClientsByName(Collections.singletonList(name));
        if (clients.isEmpty()) {
            p.sendErrorMessage("Es wurde kein Spieler auf dem TeamSpeak mit diesem Namen gefunden.");
            return;
        }

        Client client = clients.get(0);

        ChannelListCommand.Response channelListResponse = new ChannelListCommand().getResponse();
        Channel channel = channelListResponse.getChannels().stream().filter(channelIter -> client.getChannelID() == channelIter.getChannelID()).findFirst().orElse(null);

        if (channel == null) throw new IllegalStateException();

        Message.getBuilder()
                .prefix()
                .of(name).color(ColorCode.AQUA).advance().space()
                .of("befindet sich im Channel").color(ColorCode.GRAY).advance().space()
                .of(channel.getName()).color(ColorCode.AQUA).advance()
                .of(".").color(ColorCode.GRAY).advance()
                .sendTo(p.getPlayer());
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> tabCompletions = ForgeUtils.getOnlinePlayers();
        String input = args[args.length - 1].toLowerCase();
        tabCompletions.removeIf(tabComplete -> !tabComplete.toLowerCase().startsWith(input));
        return tabCompletions;
    }

    @Override
    public boolean isUsernameIndex(@Nonnull String[] args, int index) {
        return false;
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