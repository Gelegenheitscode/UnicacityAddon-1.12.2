package com.rettichlp.UnicacityAddon.base.abstraction;

import com.rettichlp.UnicacityAddon.UnicacityAddon;
import com.rettichlp.UnicacityAddon.base.api.Syncer;
import com.rettichlp.UnicacityAddon.base.faction.Faction;
import com.rettichlp.UnicacityAddon.base.faction.rettungsdienst.Medication;
import com.rettichlp.UnicacityAddon.base.location.NavigationUtils;
import com.rettichlp.UnicacityAddon.base.text.ColorCode;
import com.rettichlp.UnicacityAddon.base.text.Message;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author RettichLP
 * @see <a href="https://github.com/paulzhng/UCUtils/blob/master/src/main/java/de/fuzzlemann/ucutils/base/abstraction/UPlayerImpl.java">UCUtils by paulzhng</a>
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
        if (!UnicacityAddon.ADDON.getApi().isIngame()) {
            UnicacityAddon.LOGGER.warn("UPlayer not in game! Aborting message: " + textComponent.getUnformattedText());
            return;
        }
        getPlayer().sendMessage(textComponent);
    }

    @Override
    public void sendMessageAsString(String message) {
        sendMessage(new TextComponentString(message));
    }

    @Override
    public void sendErrorMessage(String message) {
        sendMessage(Message.getBuilder()
                .error().space()
                .of(message).color(ColorCode.GRAY).advance()
                .createComponent());
    }

    @Override
    public void sendInfoMessage(String message) {
        sendMessage(Message.getBuilder()
                .info().space()
                .of(message).color(ColorCode.WHITE).advance()
                .createComponent());
    }

    @Override
    public void sendSyntaxMessage(String message) {
        sendErrorMessage("Syntax: " + message);
    }

    @Override
    public void sendAPIMessage(String message, boolean success) {
        sendMessage(Message.getBuilder()
                .prefix()
                .of("API Response:").color(ColorCode.GRAY).advance().space()
                .of(message).color(success ? ColorCode.GREEN : ColorCode.RED).advance()
                .createComponent());
    }

    @Override
    public void sendEmptyMessage() {
        sendMessage(Message.getBuilder().createComponent());
    }

    @Override
    public void sendChatMessage(String message) {
        getPlayer().sendChatMessage(message);
        UnicacityAddon.LOGGER.info("UPlayer sent chat message: " + message);
    }

    @Override
    public void playSound(String soundIn) {
        ResourceLocation resourcelocation = new ResourceLocation(soundIn);
        getPlayer().playSound(Objects.requireNonNull(SoundEvent.REGISTRY.getObject(resourcelocation)), 1, 1);
    }

    @Override
    public String getName() {
        return UnicacityAddon.MINECRAFT.getSession().getUsername();
    }

    @Override
    public NetworkPlayerInfo getNetworkPlayerInfo() {
        if (UnicacityAddon.MINECRAFT.getConnection() == null) return null;
        return UnicacityAddon.MINECRAFT.getConnection().getPlayerInfo(getUniqueID());
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
    public double getDistanceToEntity(Entity entity) {
        return getDistanceToCoords(entity.posX, entity.posY, entity.posZ);
    }

    @Override
    public double getDistanceToCoords(double posX, double posY, double posZ) {
        return getPosition().getDistance((int) posX, (int) posY, (int) posZ);
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
        return Syncer.PLAYERFACTIONMAP.getOrDefault(getName(), Faction.NULL);
    }

    @Override
    public int getRank() {
        return Syncer.PLAYERRANKMAP.getOrDefault(getName(), -1);
    }

    @Override
    public void sellMedication(String target, Medication medication) {
        sendChatMessage("/rezept " + target + " " + medication.name());
    }

    @Override
    public void acceptOffer() {
        sendChatMessage("/annehmen");
    }

    @Override
    public void stopRoute() {
        sendChatMessage("/stoproute");
    }

    @Override
    public void setNaviRoute(int x, int y, int z) {
        setNaviRoute(new BlockPos(x, y, z));
    }

    @Override
    public void setNaviRoute(BlockPos blockPos) {
        NavigationUtils.stopRoute();
        sendChatMessage("/navi " + blockPos.getX() + "/" + blockPos.getY() + "/" + blockPos.getZ());
    }

    @Override
    public void copyToClipboard(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}