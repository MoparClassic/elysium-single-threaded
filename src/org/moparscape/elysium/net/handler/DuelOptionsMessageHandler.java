package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.DuelOptionsMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class DuelOptionsMessageHandler extends MessageHandler<DuelOptionsMessage> {

    public boolean handle(Session session, Player player, DuelOptionsMessage message) {
        return true;
    }
}
