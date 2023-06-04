package com.rettichlp.unicacityaddon.base.events;

import lombok.Getter;
import net.labymod.api.event.Event;

/**
 * @author RettichLP
 */
@Getter
public class BombRemovedEvent implements Event {

    private final long removeTime;

    public BombRemovedEvent() {
        this.removeTime = System.currentTimeMillis();
    }
}