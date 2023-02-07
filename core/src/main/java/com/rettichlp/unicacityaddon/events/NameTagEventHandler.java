package com.rettichlp.unicacityaddon.events;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.api.Syncer;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.AllianceFactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.FactionNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.SpecificNameTagSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.setting.StreetwarNameTagSetting;
import com.rettichlp.unicacityaddon.base.enums.faction.Faction;
import com.rettichlp.unicacityaddon.base.manager.FactionManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.FormattingCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.events.faction.ContractEventHandler;
import com.rettichlp.unicacityaddon.events.faction.badfaction.blacklist.BlacklistEventHandler;
import com.rettichlp.unicacityaddon.events.faction.polizei.WantedEventHandler;
import lombok.NoArgsConstructor;
import net.labymod.api.client.entity.player.Player;

import java.util.List;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class NameTagEventHandler {

    /**
     * Quote: "Wenn ich gleich nicht mehr antworte, einfach laut meinen Namen sagen." - Lou, 02.10.2022
     * "Fällst du dann aus dem Bett?" - RettichLP und Ullrich, 02.10.2022
     */
//    @Subscribe
//    public void onRenderNameTag(PlayerEvent.NameFormat e) {
//        if (!UnicacityAddon.isUnicacity())
//            return;
//        EntityPlayer entityPlayer = e.getEntityPlayer();
//        String playerUsername = e.getUsername();
//        String displayName = ScorePlayerTeam.formatPlayerName(entityPlayer.getTeam(), playerUsername);
//        if (displayName.contains(FormattingCode.OBFUSCATED.getCode()))
//            return;
//
//        String houseBan = getHouseBan(playerUsername);
//        String outlaw = getOutlaw(playerUsername);
//        String prefix = getPrefix(playerUsername, false);
//        String factionInfo = getFactionInfo(playerUsername);
//        String duty = getDuty(playerUsername);
//
//        e.setDisplayname(houseBan + outlaw + prefix + playerUsername + factionInfo + duty);
//        entityPlayer.setGlowing(RenderTagEventHandler.showPlayerInfo && !AbstractionLayer.getPlayer().getPlayer().canEntityBeSeen(entityPlayer));
//    }

    private String getHouseBan(String playerName) {
        StringBuilder houseBan = new StringBuilder();
        houseBan.append(FormattingCode.RESET.getCode());

        if (UnicacityAddon.configuration.nameTagSetting().houseBan().get()) {
            if (Syncer.HOUSEBANENTRYLIST.stream().anyMatch(houseBanEntry -> houseBanEntry.getName().equals(playerName)))
                houseBan.append(Message.getBuilder()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("HV").color(ColorCode.RED).advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance().space()
                        .add(FormattingCode.RESET.getCode())
                        .create());
        }

        return houseBan.toString();
    }

    private String getOutlaw(String playerName) {
        StringBuilder outlaw = new StringBuilder();
        outlaw.append(FormattingCode.RESET.getCode());

        if (UnicacityAddon.configuration.nameTagSetting().specificNameTagSetting().enabled().get()) {
            if (BlacklistEventHandler.BLACKLIST_MAP.containsKey(playerName)) {
                if (BlacklistEventHandler.BLACKLIST_MAP.get(playerName))
                    outlaw.append(Message.getBuilder()
                            .of("[").color(ColorCode.DARK_GRAY).advance()
                            .of("V").color(ColorCode.RED).advance()
                            .of("]").color(ColorCode.DARK_GRAY).advance()
                            .add(FormattingCode.RESET.getCode())
                            .create());
            }
        }

        return outlaw.toString();
    }

    public static String getPrefix(String playerName, boolean isCorpse) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(FormattingCode.RESET.getCode());
        if (isCorpse)
            prefix.append(ColorCode.GRAY.getCode());

        if (Syncer.PLAYERFACTIONMAP.containsKey(playerName)) {
            Faction targetPlayerFaction = Syncer.PLAYERFACTIONMAP.get(playerName);

            FactionNameTagSetting factionNameTagSetting = UnicacityAddon.configuration.nameTagSetting().factionNameTagSetting();
            if (factionNameTagSetting.enabled().get()) {
                if (targetPlayerFaction.equals(AbstractionLayer.getPlayer().getFaction()))
                    prefix.append(factionNameTagSetting.color().getOrDefault(ColorCode.BLUE).getCode());
            }

            AllianceFactionNameTagSetting allianceFactionNameTagSetting = UnicacityAddon.configuration.nameTagSetting().allianceFactionNameTagSetting();
            if (allianceFactionNameTagSetting.enabled().get()) {
                ColorCode allianceColor = allianceFactionNameTagSetting.color().getOrDefault(ColorCode.DARK_PURPLE);
                Faction allianceFaction1 = allianceFactionNameTagSetting.faction1().getOrDefault(Faction.NULL);
                Faction allianceFaction2 = allianceFactionNameTagSetting.faction2().getOrDefault(Faction.NULL);
                if (targetPlayerFaction.equals(allianceFaction1) || targetPlayerFaction.equals(allianceFaction2))
                    prefix.append(allianceColor.getCode());
            }

            StreetwarNameTagSetting streetwarNameTagSetting = UnicacityAddon.configuration.nameTagSetting().streetwarNameTagSetting();
            if (streetwarNameTagSetting.enabled().get()) {
                ColorCode streetwarColor = streetwarNameTagSetting.color().getOrDefault(ColorCode.RED);
                Faction streetwarFaction1 = streetwarNameTagSetting.faction1().getOrDefault(Faction.NULL);
                Faction streetwarFaction2 = streetwarNameTagSetting.faction2().getOrDefault(Faction.NULL);
                if (targetPlayerFaction.equals(streetwarFaction1) || targetPlayerFaction.equals(streetwarFaction2))
                    prefix.append(streetwarColor.getCode());
            }
        }

        SpecificNameTagSetting specificNameTagSetting = UnicacityAddon.configuration.nameTagSetting().specificNameTagSetting();
        if (specificNameTagSetting.enabled().get()) {
            WantedEventHandler.Wanted wanted = WantedEventHandler.WANTED_MAP.get(playerName);
            if (wanted != null) {
                int amount = wanted.getAmount();
                ColorCode color;

                if (amount == 1)
                    color = ColorCode.DARK_GREEN;
                else if (amount < 15)
                    color = ColorCode.GREEN;
                else if (amount < 25)
                    color = ColorCode.YELLOW;
                else if (amount < 50)
                    color = ColorCode.GOLD;
                else if (amount < 60)
                    color = ColorCode.RED;
                else
                    color = ColorCode.DARK_RED;

                prefix.append(color.getCode());
            }

            if (BlacklistEventHandler.BLACKLIST_MAP.get(playerName) != null)
                prefix.append(specificNameTagSetting.color().getOrDefault(ColorCode.DARK_RED).getCode());

            if (ContractEventHandler.CONTRACT_LIST.contains(playerName))
                prefix.append(specificNameTagSetting.color().getOrDefault(ColorCode.DARK_RED).getCode());
        }

        return prefix.toString();
    }

    public static String getFactionInfo(String playerName) {
        StringBuilder suffix = new StringBuilder();
        suffix.append(FormattingCode.RESET.getCode());

        if (Syncer.PLAYERFACTIONMAP.containsKey(playerName)) {
            Faction targetPlayerFaction = Syncer.PLAYERFACTIONMAP.get(playerName);
            if (UnicacityAddon.configuration.nameTagSetting().factionInfo().get())
                suffix.append(" ").append(targetPlayerFaction.getNameTagSuffix());
        }

        return suffix.toString();
    }

    private String getDuty(String playerName) {
        StringBuilder duty = new StringBuilder();
        duty.append(FormattingCode.RESET.getCode());

        if (UnicacityAddon.configuration.nameTagSetting().duty().get()) {
            if (FactionManager.checkPlayerDuty(playerName))
                duty.append(Message.getBuilder()
                        .of(" \u25cf ").color(ColorCode.GREEN).advance()
                        .add(FormattingCode.RESET.getCode())
                        .create());
        }

        return duty.toString();
    }

    public static void refreshAllDisplayNames() {
        if (UnicacityAddon.MINECRAFT.clientWorld() == null)
            return;
        List<Player> playerList = UnicacityAddon.MINECRAFT.clientWorld().getPlayers();
        //playerList.forEach(player -> player::);
    }
}