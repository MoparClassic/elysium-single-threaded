package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.DuelInformationMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class DuelInformationMessageHandler extends MessageHandler<DuelInformationMessage> {

    public boolean handle(Session session, Player player, DuelInformationMessage message) {
        return true;
    }
}
