//package com.rettichlp.unicacityaddon.modules;
//
//import com.rettichlp.unicacityaddon.UnicacityAddon;
//import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
//import com.rettichlp.unicacityaddon.base.registry.HudWidgetRegistry;
//import com.rettichlp.unicacityaddon.base.registry.annotation.UCModule;
//import com.rettichlp.unicacityaddon.base.text.ColorCode;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
//import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
//import net.labymod.ingamegui.ModuleCategory;
//import net.labymod.ingamegui.moduletypes.SimpleModule;
//import net.labymod.settings.elements.ControlElement;
//import net.labymod.utils.Material;
//
//import static com.rettichlp.unicacityaddon.base.utils.MathUtils.HEART_DECIMAL_FORMAT;
//
///**
// * @author Dimiikou
// */
//@UCModule
//public class HearthAmountHudWidget extends TextHudWidget<TextHudWidgetConfig> {
//
//    @Override public String getControlName() {
//        return "Herzen";
//    }
//
//    @Override public String getSettingName() {
//        return null;
//    }
//
//    @Override public String getDisplayName() {
//        return "Herzen";
//    }
//
//    @Override public String getDisplayValue() {
//        return HEART_DECIMAL_FORMAT.format(UnicacityAddon.PLAYER.getPlayer().getHealth() / 2) + ColorCode.RED.getCode() + "❤";
//    }
//
//    @Override public String getDefaultValue() {
//        return "0";
//    }
//
//    @Override public String getDescription() {
//        return "Zeigt an wie viele Herzen du hast";
//    }
//
//    @Override public ControlElement.IconData getIconData() {
//        return new ControlElement.IconData(Material.RED_ROSE);
//    }
//
//    @Override public ModuleCategory getCategory() {
//        return HudWidgetRegistry.UNICACITY;
//    }
//
//    @Override public boolean isShown() {
//        return UnicacityAddon.isUnicacity();
//    }
//
//    @Override public int getSortingId() {
//        return 0;
//    }
//
//    @Override public void loadSettings() {
//    }
//}