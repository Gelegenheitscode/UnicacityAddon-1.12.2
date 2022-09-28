package com.rettichlp.UnicacityAddon.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rettichlp.UnicacityAddon.base.abstraction.AbstractionLayer;
import com.rettichlp.UnicacityAddon.base.abstraction.UPlayer;
import com.rettichlp.UnicacityAddon.base.api.APIRequest;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.registry.annotation.UCCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.IClientCommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author RettichLP
 */
@UCCommand
public class NaviPointCommand implements IClientCommand {

    @Override
    @Nonnull
    public String getName() {
        return "navipoint";
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "/navipoint [add|remove] [Name] (x) (y) (z)";
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
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        UPlayer p = AbstractionLayer.getPlayer();

        if (args.length == 5 && args[0].equalsIgnoreCase("add")) {
            JsonObject response = APIRequest.sendNaviPointAddRequest(args[1], args[2], args[3], args[4]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
            Syncer.syncNaviPointList();
        } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
            JsonObject response = APIRequest.sendNaviPointRemoveRequest(args[1]);
            if (response == null) return;
            p.sendAPIMessage(response.get("info").getAsString(), true);
            Syncer.syncNaviPointList();
        } else {
            p.sendSyntaxMessage(getUsage(sender));
        }
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        UPlayer p = AbstractionLayer.getPlayer();
        List<String> tabCompletions = new ArrayList<>();
        if (args.length == 1) {
            tabCompletions.add("add");
            tabCompletions.add("remove");
        } else if (args.length == 2) {
            JsonArray response = APIRequest.sendNaviPointRequest();
            if (response != null) {
                response.forEach(jsonElement -> tabCompletions.add(jsonElement.getAsJsonObject().get("name").getAsString()));
            }
        } else if (args.length == 3) { // x
            tabCompletions.add(String.valueOf(p.getPosition().getX()));
        } else if (args.length == 4) { // y
            tabCompletions.add(String.valueOf(p.getPosition().getY()));
        } else if (args.length == 5) { // z
            tabCompletions.add(String.valueOf(p.getPosition().getZ()));
        }
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