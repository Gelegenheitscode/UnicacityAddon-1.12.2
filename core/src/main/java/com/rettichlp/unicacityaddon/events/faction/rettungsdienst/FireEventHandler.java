package com.rettichlp.unicacityaddon.events.faction.rettungsdienst;

import com.rettichlp.unicacityaddon.base.registry.annotation.UCEvent;
import lombok.NoArgsConstructor;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

/**
 * @author RettichLP
 */
@UCEvent
@NoArgsConstructor
public class FireEventHandler {

    /**
     * Quote: "Meine Öffi-Nachricht geht nicht... oh... ich habe den Imgur-Link eingefügt..." - [UC]laaurin_, 02.10.2022
     */
    @Subscribe
    public void onChatReceive(ChatReceiveEvent e) {
//        UPlayer p = AbstractionLayer.getPlayer();
//        String msg = e.chatMessage().getPlainText();
//
//        if (!msg.equals("Fehler: Es brennt noch!")) return;
//
//        World world = UnicacityAddon.MINECRAFT.world;
//        BlockPos pos = p.getPosition();
//        BlockPos blockPos1 = new FloatVector3(pos.getX() - 30, pos.getY() - 30, pos.getZ() - 30);
//        BlockPos blockPos2 = new FloatVector3(pos.getX() + 30, pos.getY() + 30, pos.getZ() + 30);
//
//        List<BlockPos> fireBlocks = StreamSupport.stream(FloatVector3.getAllInBox(FloatVector31, blockPos2).spliterator(), false)
//                .collect(Collectors.toList())
//                .stream()
//                .filter(FloatVector3 -> world.getBlockState(floatVector3).getMaterial().equals(Material.FIRE))
//                .collect(Collectors.toList());
//
//        if (fireBlocks.isEmpty()) return;
//
//        p.sendMessage(Message.getBuilder()
//                .of("Feuer in der Nähe:").color(ColorCode.DARK_RED).advance()
//                .createComponent());
//
//        fireBlocks.forEach(floatVector3 -> p.sendMessage(Message.getBuilder()
//                .of("»").color(ColorCode.GRAY).advance().space()
//                .of("X: " + blockPos.getX() + " | Y: " + blockPos.getY() + " | Z: " + blockPos.getZ()).color(ColorCode.RED).advance().space()
//                .of("[➤]").color(ColorCode.GREEN)
//                        .hoverEvent(HoverEvent.Action.SHOW_TEXT, Message.getBuilder().of("Route anzeigen").color(ColorCode.RED).advance().createComponent())
//                        .clickEvent(ClickEvent.Action.RUN_COMMAND, "/navi " + blockPos.getX() + "/" + blockPos.getY() + "/" + blockPos.getZ())
//                .advance()
//                .createComponent()));
    }
}