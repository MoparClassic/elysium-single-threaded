package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ItemDoorMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemDoorMessageHandler extends MessageHandler<ItemDoorMessage> {
    @Override
    public boolean handle(Session session, Player player, ItemDoorMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
        return true;
    }
}
