package com.rettichlp.unicacityaddon.listener;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.enums.Weapon;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.commands.GetGunPatternCommand;
import com.rettichlp.unicacityaddon.commands.faction.DropDrugAllCommand;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;

/**
 * @author RettichLP
 */
@UCEvent
public class ScreenRenderListener {

    public static int lastHoveredSlotNumber = -1;

    private final UnicacityAddon unicacityAddon;

    public ScreenRenderListener(UnicacityAddon unicacityAddon) {
        this.unicacityAddon = unicacityAddon;
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent e) {
        this.unicacityAddon.transportController().carInteract();
        this.unicacityAddon.transportController().processBusRouting(this.unicacityAddon);

        if (GetGunPatternCommand.armament != null) {
            Weapon weapon = GetGunPatternCommand.armament.getWeapon();
            int weaponSlotNumber = this.unicacityAddon.guiController().getSlotNumberByDisplayName(weapon.getName());

            this.unicacityAddon.guiController().inventoryClick(this.unicacityAddon, weaponSlotNumber);
            this.unicacityAddon.player().sendServerMessage("/getammo " + weapon.getName() + " " + GetGunPatternCommand.armament.getAmount());
            GetGunPatternCommand.armament = null;
        }

        if (DropDrugAllCommand.active) {
            this.unicacityAddon.guiController().updateDrugInventoryMap(this.unicacityAddon);
        }
    }

    @Subscribe
    public void onItemStackTooltip(ItemStackTooltipEvent e) {
        ItemStack itemStack = e.itemStack();
        lastHoveredSlotNumber = this.unicacityAddon.guiController().getSlotNumberByDisplayName(this.unicacityAddon.utilService().text().plain(itemStack.getDisplayName()));
    }
}