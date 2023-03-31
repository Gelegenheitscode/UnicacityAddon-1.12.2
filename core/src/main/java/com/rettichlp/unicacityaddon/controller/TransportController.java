package com.rettichlp.unicacityaddon.controller;

import com.rettichlp.unicacityaddon.base.AddonPlayer;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/**
 * @author RettichLP
 */
@Nullable
@Referenceable
public abstract class TransportController {

    public abstract void processBusRouting(AddonPlayer p);

    public abstract void carInteract();
}
