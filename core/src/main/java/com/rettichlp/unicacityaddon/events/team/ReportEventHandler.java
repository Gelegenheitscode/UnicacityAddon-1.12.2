package com.rettichlp.unicacityaddon.events.team;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.config.message.ReportMessageSetting;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import jdk.internal.joptsimple.internal.Strings;
import lombok.NoArgsConstructor;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Dimiikou
 */
@UCEvent
@NoArgsConstructor
public class ReportEventHandler {

    private static final Timer t = new Timer();
    private long lastExecution = -1;
    private boolean isReport = false;

    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ChatMessage chatMessage = e.chatMessage();
        String formattedMsg = chatMessage.getFormattedText();
        String unformattedMsg = chatMessage.getPlainText();
        ReportMessageSetting reportMessageSetting = UnicacityAddon.configuration.reportMessageSetting();

        if (PatternHandler.REPORT_ACCEPTED_PATTERN.matcher(unformattedMsg).find()) {
            isReport = true;

            if (reportMessageSetting.greeting().getOrDefault(Strings.EMPTY).isEmpty())
                return;
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - lastExecution > 1000L) {
                        p.sendChatMessage(reportMessageSetting.greeting().get());
                        lastExecution = System.currentTimeMillis();
                    }
                }
            }, 1000);
            return;
        }

        if (PatternHandler.REPORT_END_PATTERN.matcher(unformattedMsg).find()) {
            isReport = false;
            return;
        }

        if (formattedMsg.startsWith(ColorCode.DARK_PURPLE.getCode()) && isReport) {
            e.setMessage(Message.getBuilder()
                    .add(reportMessageSetting.prefix().getOrDefault(Strings.EMPTY).replaceAll("&", "§"))
                    .add(formattedMsg)
                    .createComponent());
        }

        if (PatternHandler.REPORT_PATTERN.matcher(unformattedMsg).find()) {
            // TODO: 09.12.2022 p.playSound(SoundRegistry.REPORT_SOUND, 1, 1);
        }
    }
}