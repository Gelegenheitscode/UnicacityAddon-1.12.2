package com.rettichlp.unicacityaddon.events.faction;

import com.rettichlp.unicacityaddon.base.abstraction.AbstractionLayer;
import com.rettichlp.unicacityaddon.base.abstraction.UPlayer;
import com.rettichlp.unicacityaddon.base.api.request.APIRequest;
import com.rettichlp.unicacityaddon.base.enums.api.StatisticType;
import com.rettichlp.unicacityaddon.base.enums.location.ServiceCallBox;
import com.rettichlp.unicacityaddon.base.registry.SoundRegistry;
import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import com.rettichlp.unicacityaddon.base.text.ColorCode;
import com.rettichlp.unicacityaddon.base.text.Message;
import com.rettichlp.unicacityaddon.base.text.PatternHandler;
import com.rettichlp.unicacityaddon.commands.faction.ServiceCountCommand;
import com.rettichlp.unicacityaddon.modules.EmergencyServiceModule;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author RettichLP
 */
@UCEvent
public class EmergencyServiceEventHandler {

    private static final List<ServiceCallBox> activeEmergencyCallBoxList = new ArrayList<>();

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        UPlayer p = AbstractionLayer.getPlayer();
        ITextComponent msg = e.getMessage();
        String unformattedMsg = msg.getUnformattedText();

        if (PatternHandler.SERVICE_ARRIVED_PATTERN.matcher(unformattedMsg).find()) {
            p.playSound(SoundRegistry.SERVICE_SOUND, 1, 1);
            EmergencyServiceModule.currentCount++;
            return;
        }

        if (PatternHandler.SERVICE_REQUEUED_PATTERN.matcher(unformattedMsg).find()) {
            EmergencyServiceModule.currentCount++;
            return;
        }

        if (PatternHandler.SERVICE_ACCEPTED_PATTERN.matcher(unformattedMsg).find() && EmergencyServiceModule.currentCount > 0) {
            EmergencyServiceModule.currentCount--;
            return;
        }

        if (PatternHandler.SERVICE_DELETED_PATTERN.matcher(unformattedMsg).find() && EmergencyServiceModule.currentCount > 0) {
            EmergencyServiceModule.currentCount--;
            return;
        }

        if (PatternHandler.SERVICE_NO_SERVICE_PATTERN.matcher(unformattedMsg).find()) {
            EmergencyServiceModule.currentCount = 0;
            return;
        }

        if (PatternHandler.SERVICE_DONE_PATTERN.matcher(unformattedMsg).find()) {
            ServiceCountCommand.addService();
            APIRequest.sendStatisticAddRequest(StatisticType.SERVICE);
        }

        Matcher serviceOverviewMatcher = PatternHandler.SERVICE_OVERVIEW_PATTERN.matcher(unformattedMsg);
        if (serviceOverviewMatcher.find()) {
            String openServices = serviceOverviewMatcher.group(1);
            EmergencyServiceModule.currentCount = Integer.parseInt(openServices);
        }

        Matcher serviceCallBoxMatcher = PatternHandler.SERVICE_CALL_BOX_PATTERN.matcher(unformattedMsg);
        if (serviceCallBoxMatcher.find()) {
            ServiceCallBox serviceCallBox = ServiceCallBox.getServiceCallBoxByLocationName(serviceCallBoxMatcher.group(2));
            if (serviceCallBox != null) {
                activeEmergencyCallBoxList.add(serviceCallBox);
                e.setMessage(Message.getBuilder()
                        .add(msg.getFormattedText())
                        .space()
                        .of("[").color(ColorCode.DARK_GRAY).advance()
                        .of("Unterwegs - " + serviceCallBox.getDistance(p.getPosition()) + "m").color(ColorCode.RED)
                                .clickEvent(ClickEvent.Action.RUN_COMMAND, serviceCallBox.getNaviCommand())
                                .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Unterwegs").color(ColorCode.RED).advance().createComponent())
                                .advance()
                        .of("]").color(ColorCode.DARK_GRAY).advance()
                        .createComponent());
            }
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent e) {
        Optional<ServiceCallBox> serviceCallBoxOptional = activeEmergencyCallBoxList.stream()
                .filter(serviceCallBox -> serviceCallBox.getNaviCommand().equals(e.getMessage()))
                .findAny();

        if (serviceCallBoxOptional.isPresent()) {
            ServiceCallBox serviceCallBox = serviceCallBoxOptional.get();
            AbstractionLayer.getPlayer().sendChatMessage("/f ➡ Unterwegs zur Notrufsäule (" + serviceCallBox.getLocationName() + ")");
            activeEmergencyCallBoxList.remove(serviceCallBox);
        }
    }
}