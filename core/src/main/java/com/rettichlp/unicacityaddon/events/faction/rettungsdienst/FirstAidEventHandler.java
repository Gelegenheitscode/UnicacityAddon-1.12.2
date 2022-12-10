package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.base.manager.FileManager;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.base.utils.TextUtils;
import net.kyori.adventure.text.event.HoverEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author Dimiikou
 */
@UCEvent
public class FirstAidEventHandler {

    public static long firstAidIssuingTime;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        String msg = e.chatMessage().getPlainText();

        if (PatternHandler.FIRST_AID_RECEIVE_PATTERN.matcher(msg).find()) {
            firstAidIssuingTime = System.currentTimeMillis();
            FileManager.saveData();
            return;
        }

        if (PatternHandler.FIRST_AID_LICENCE_PATTERN.matcher(msg).find()) {
            long expirationTime = firstAidIssuingTime + TimeUnit.DAYS.toMillis(14); // Erhaltsdatum + 14 Tage = Auslaufdatum
            long timeLeft = expirationTime - System.currentTimeMillis(); // Auslaufdatum - aktuelle Datum = Dauer des Scheins
            e.setMessage(Message.getBuilder()
                    .space().space()
                    .of("-").color(ColorCode.GRAY).advance().space()
                    .of("Erste-Hilfe-Schein").color(ColorCode.BLUE).advance()
                    .of(":").color(ColorCode.DARK_GRAY).advance().space()
                    .of("Vorhanden").color(ColorCode.AQUA)
                            .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of(TextUtils.parseTime(TimeUnit.MILLISECONDS, timeLeft)).color(ColorCode.RED).advance().createComponent()).advance()
                    .createComponent());
        }
    }
}
