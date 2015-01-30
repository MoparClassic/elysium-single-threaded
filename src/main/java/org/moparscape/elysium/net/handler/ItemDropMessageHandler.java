package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.PlayerState;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ItemDropMessage;
import org.moparscape.elysium.task.timed.ItemDropTask;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemDropMessageHandler extends MessageHandler<ItemDropMessage> {

    @Override
    public boolean handle(Session session, Player player, ItemDropMessage message) {
        int itemIndex = message.getIndex();
        Inventory inventory = player.getInventory();

        if (itemIndex >= inventory.size()) return true;

        player.setState(PlayerState.ITEM_DROP);
        Server.getInstance().submitTimedTask(new ItemDropTask(player, itemIndex, player.getActionCount()));

        return true;
    }
}
