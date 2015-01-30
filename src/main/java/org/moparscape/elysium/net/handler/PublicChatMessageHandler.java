package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.ChatMessage;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.PublicChatMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PublicChatMessageHandler extends MessageHandler<PublicChatMessage> {
    @Override
    public boolean handle(Session session, Player player, PublicChatMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("Public chat message received!");
        ChatMessage cm = new ChatMessage(player, message.getMessagePayload());
        player.addChatMessage(cm);

        return true;
    }
}
