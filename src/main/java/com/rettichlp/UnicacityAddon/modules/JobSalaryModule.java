package com.rettichlp.UnicacityAddon.modules;

import com.rettichlp.UnicacityAddon.base.module.UCModule;
import com.rettichlp.UnicacityAddon.base.module.UCModuleHandler;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

/**
 * @author Dimiikou
 */
@UCModule
public class JobSalaryModule extends SimpleModule {

    public static int currentSalary = 0;

    @Override public String getControlName() {
        return "Job Gehalt";
    }

    @Override public String getSettingName() {
        return null;
    }

    @Override public String getDisplayName() {
        return "Job-Gehalt";
    }

    @Override public String getDisplayValue() { return ""+currentSalary; }

    @Override public String getDefaultValue() {
        return "0";
    }

    @Override public String getDescription() {
        return "Zeigt dein Gehalt durch Jobs bis zum nächsten PayDay an.";
    }

    @Override public ControlElement.IconData getIconData() { return new ControlElement.IconData(Material.GOLD_INGOT); }

    @Override public ModuleCategory getCategory() {
        return UCModuleHandler.UNICACITY;
    }

    @Override public boolean isShown() {
        return true;
    }

    @Override public int getSortingId() {
        return 0;
    }

    @Override public void loadSettings() {
    }

    public static void resetSalary() {
        currentSalary = 0;
    }
}
