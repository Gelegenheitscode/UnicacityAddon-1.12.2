package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author Dimiikou
 */
public class PlantWaterTimerModule extends SimpleModule {

    public static int currentCount = 0;
    public static final int timeNeeded = 3000;
    public static String timer = "";

    @Override public String getControlName() {
        return "Plantage Wässern";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "Wässern";
    }

    @Override public String getDisplayValue() {
        return timer;
    }

    @Override public String getDefaultValue() {
        return "00:00";
    }

    @Override public String getDescription() {
        return "Zeigt einen Timer an, welcher die Zeit zum nächsten Wässern beschreibt.";
    }

    @Override public ControlElement.IconData getIconData() { return new ControlElement.IconData(Material.WATER_BUCKET); }

    @Override public ModuleCategory getCategory() {
        return UCModuleHandler.UNICACITY;
    }

    @Override public boolean isShown() {
        return PlantFertilizeTimerModule.plantRunning;
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }

    public static void stopPlant() {
        currentCount = 0;
        timer = "";
    }
}
