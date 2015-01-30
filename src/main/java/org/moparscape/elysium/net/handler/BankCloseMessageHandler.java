package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.BankCloseMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class BankCloseMessageHandler extends MessageHandler<BankCloseMessage> {

    public boolean handle(Session session, Player player, BankCloseMessage message) {
        return true;
    }
}
