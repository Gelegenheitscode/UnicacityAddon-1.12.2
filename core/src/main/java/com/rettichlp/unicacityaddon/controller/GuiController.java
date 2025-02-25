package com.rettichlp.unicacityaddon.controller;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class GuiController {

    public abstract int getSlotNumberByDisplayName(String displayName);

    public abstract void inventoryClick(UnicacityAddon unicacityAddon, int slotNumber);

    public abstract void updateDrugInventoryMap(UnicacityAddon unicacityAddon);

    public abstract void setSelectedHotbarSlot(int slotNumber);
}
