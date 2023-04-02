package com.rettichlp.unicacityaddon.base.config;

import com.rettichlp.unicacityaddon.base.config.atm.ATMSetting;
import com.rettichlp.unicacityaddon.base.config.equip.EquipSetting;
import com.rettichlp.unicacityaddon.base.config.hotkey.HotkeySetting;
import com.rettichlp.unicacityaddon.base.config.join.CommandSetting;
import com.rettichlp.unicacityaddon.base.config.join.PasswordSetting;
import com.rettichlp.unicacityaddon.base.config.message.FactionMessageSetting;
import com.rettichlp.unicacityaddon.base.config.message.ReportMessageSetting;
import com.rettichlp.unicacityaddon.base.config.nametag.NameTagSetting;
import com.rettichlp.unicacityaddon.base.config.ownUse.OwnUseSetting;
import com.rettichlp.unicacityaddon.base.config.reinforcement.ReinforcementSetting;
import com.rettichlp.unicacityaddon.base.config.sloc.SlocSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/**
 * @author RettichLP
 */
public interface UnicacityAddonConfiguration {

    HotkeySetting hotkeySetting();

    NameTagSetting nameTagSetting();

    ReinforcementSetting reinforcementSetting();

    SlocSetting slocSetting();

    FactionMessageSetting factionMessageSetting();

    ReportMessageSetting reportMessageSetting();

    PasswordSetting passwordSetting();

    CommandSetting commandSetting();

    ConfigProperty<Boolean> texturePack();

    ATMSetting atmSetting();

    ConfigProperty<Boolean> bombScreenshot();

    ConfigProperty<Boolean> carRoute();

    ConfigProperty<Boolean> update();

    ConfigProperty<String> tsApiKey();

    ConfigProperty<Boolean> tsNotificationPublic();

    ConfigProperty<Boolean> tsNotificationSupport();

    EquipSetting equipSetting();

    OwnUseSetting ownUseSetting();

    ConfigProperty<Boolean> orderedTablist();

    ConfigProperty<Boolean> despawnTime();

//    SoundSetting soundSetting();

    ConfigProperty<Boolean> addonTag();
}