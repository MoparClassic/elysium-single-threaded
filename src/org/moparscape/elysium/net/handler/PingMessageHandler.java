package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.PingMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PingMessageHandler extends MessageHandler<PingMessage> {
    @Override
    public boolean handle(Session session, Player player, PingMessage message) {
        // Nothing needs to be done here.
        return true;
    }
}
