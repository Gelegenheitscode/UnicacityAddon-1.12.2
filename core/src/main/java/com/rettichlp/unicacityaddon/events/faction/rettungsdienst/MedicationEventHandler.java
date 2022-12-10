package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptAnnehmenCommand;
import com.rettichlp.unicacityaddon.commands.faction.rettungsdienst.ARezeptCommand;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author RettichLP
 */
@UCEvent
public class MedicationEventHandler {

    private static final Timer TIMER = new Timer();
    private static long lastExecution;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();
        long timeSinceLastExecution;
        long delay = 0;

        if (PatternHandler.RECIPE_ACCEPT_PATTERN.matcher(msg).find() && ARezeptAnnehmenCommand.amount > 0) {
            timeSinceLastExecution = System.currentTimeMillis() - lastExecution;
            if (timeSinceLastExecution < 1000) delay = 1000 - timeSinceLastExecution;
            TIMER.schedule(new TimerTask() {
                @Override
                public void run() {
                    acceptRecipe();
                }
            }, delay);
            return;
        }

        if (PatternHandler.RECIPE_GIVE_PATTERN.matcher(msg).find() && ARezeptCommand.amount > 0) {
            timeSinceLastExecution = System.currentTimeMillis() - lastExecution;
            if (timeSinceLastExecution < 1000) delay = 1000 - timeSinceLastExecution;
            TIMER.schedule(new TimerTask() {
                @Override
                public void run() {
                    giveRecipe();
                }
            }, delay);
        }
    }

    public static void acceptRecipe() {
        --ARezeptAnnehmenCommand.amount;
        lastExecution = System.currentTimeMillis();
        AbstractionLayer.getPlayer().acceptOffer();
    }

    public static void giveRecipe() {
        --ARezeptCommand.amount;
        lastExecution = System.currentTimeMillis();
        AbstractionLayer.getPlayer().sellMedication(ARezeptCommand.target, ARezeptCommand.medication);
    }
}