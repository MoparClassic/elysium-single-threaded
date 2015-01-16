package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ItemItemMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemItemMessageHandler extends MessageHandler<ItemItemMessage> {
    @Override
    public boolean handle(Session session, Player player, ItemItemMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
        return true;
    }
}
