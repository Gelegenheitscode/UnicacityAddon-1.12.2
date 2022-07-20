package com.rettichlp.UnicacityAddon.base.abstraction;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.FactionHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.inventory.Container;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * @author Fuzzlemann
 */
public class UPlayerImpl implements UPlayer {

    @Override
    public EntityPlayerSP getPlayer() {
        return UnicacityAddon.MINECRAFT.player;
    }

    @Override
    public boolean isConnected() {
        return getPlayer() != null;
    }

    @Override
    public void sendMessage(ITextComponent textComponent) {
        getPlayer().sendMessage(textComponent);
    }

    @Override
    public void sendMessageAsString(String message) {
        getPlayer().sendMessage(new TextComponentString(message));
    }

    @Override
    public void sendChatMessage(String message) {
        getPlayer().sendChatMessage(message);
    }

    @Override
    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        getPlayer().playSound(soundIn, volume, pitch);
    }

    @Override
    public String getName() {
        return UnicacityAddon.MINECRAFT.getSession().getUsername();
    }

    @Override
    public UUID getUniqueID() {
        return UnicacityAddon.MINECRAFT.getSession().getProfile().getId();
    }

    @Override
    public BlockPos getPosition() {
        return getPlayer().getPosition();
    }

    @Override
    public Team getTeam() {
        return getPlayer().getTeam();
    }

    @Override
    public Scoreboard getWorldScoreboard() {
        return getPlayer().getWorldScoreboard();
    }

    @Override
    public World getWorld() {
        return getPlayer().getEntityWorld();
    }

    @Override
    public Container getOpenContainer() {
        return getPlayer().openContainer;
    }

    @Override
    public Container getInventoryContainer() {
        return getPlayer().inventoryContainer;
    }

    @Override
    public AxisAlignedBB getEntityBoundingBox() {
        return getPlayer().getEntityBoundingBox();
    }

    @Override
    public NetHandlerPlayClient getConnection() {
        return getPlayer().connection;
    }

    @Override
    public Faction getFaction() {
        return FactionHandler.getPlayerFactionMap().get(getName());
    }
}