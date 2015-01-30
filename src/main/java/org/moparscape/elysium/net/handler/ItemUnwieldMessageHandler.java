package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.def.ItemWieldableDef;
import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ItemUnwieldMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemUnwieldMessageHandler extends MessageHandler<ItemUnwieldMessage> {

    @Override
    public boolean handle(Session session, Player player, ItemUnwieldMessage message) {
        int itemIndex = message.getItemIndex();
        Inventory inventory = player.getInventory();

        if (itemIndex >= inventory.size()) return true;

        InvItem item = inventory.get(itemIndex);
        if (item == null || !item.isWielded()) return true;

        if (!item.isWieldable()) {
            // Might want to mark this as cheating.
            return true;
        }

        ItemWieldableDef wieldableDef = item.getWieldableDef();
        Appearance appearance = player.getAppearance();

        item.setWielded(false);
        player.setWornItem(wieldableDef.getWieldPos(), appearance.getSprite(wieldableDef.getWieldPos()));
        Packets.sendSound(player, "click");
        Packets.sendInventory(player);

        return true;
    }
}
