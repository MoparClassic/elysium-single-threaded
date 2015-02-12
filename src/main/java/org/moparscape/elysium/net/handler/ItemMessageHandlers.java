package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.def.ItemWieldableDef;
import org.moparscape.elysium.entity.*;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.*;
import org.moparscape.elysium.task.timed.ItemDropTask;
import org.moparscape.elysium.task.timed.ItemPickupTask;
import org.moparscape.elysium.util.Formulae;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.Region;

import java.util.List;
import java.util.Map;

/**
 * Created by daniel on 13/02/2015.
 */
public final class ItemMessageHandlers {

    public static final class ItemDoorMessageHandler extends MessageHandler<ItemDoorMessage> {
        @Override
        public boolean handle(Session session, Player player, ItemDoorMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ItemDropMessageHandler extends MessageHandler<ItemDropMessage> {

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

    public static final class ItemGameObjectMessageHandler extends MessageHandler<ItemGameObjectMessage> {
        @Override
        public boolean handle(Session session, Player player, ItemGameObjectMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ItemGroundItemMessageHandler extends MessageHandler<ItemGroundItemMessage> {
        @Override
        public boolean handle(Session session, Player player, ItemGroundItemMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ItemItemMessageHandler extends MessageHandler<ItemItemMessage> {
        @Override
        public boolean handle(Session session, Player player, ItemItemMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ItemNpcMessageHandler extends MessageHandler<ItemNpcMessage> {
        @Override
        public boolean handle(Session session, Player player, ItemNpcMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ItemPickupMessageHandler extends MessageHandler<ItemPickupMessage> {

        @Override
        public boolean handle(Session session, Player player, ItemPickupMessage message) {
            Point itemLoc = message.getLocation();
            Region r = Region.getRegion(itemLoc);
            Item targetItem = r.getItem(message.getItemId(), itemLoc);

            if (targetItem != null && targetItem.isVisibleTo(player)) {
                player.setState(PlayerState.ITEM_PICKUP);
                Server.getInstance().submitTimedTask(new ItemPickupTask(player, targetItem));
            }

            return true;
        }
    }

    public static final class ItemPlayerMessageHandler extends MessageHandler<ItemPlayerMessage> {
        @Override
        public boolean handle(Session session, Player player, ItemPlayerMessage message) {
            //To change body of implemented methods use File | Settings | File Templates.
            return true;
        }
    }

    public static final class ItemUnwieldMessageHandler extends MessageHandler<ItemUnwieldMessage> {

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

    public static final class ItemWieldMessageHandler extends MessageHandler<ItemWieldMessage> {

        @Override
        public boolean handle(Session session, Player player, ItemWieldMessage message) {
            int itemIndex = message.getItemIndex();
            Inventory inventory = player.getInventory();

            if (itemIndex >= inventory.size()) return true;

            InvItem item = inventory.get(itemIndex);
            if (item == null || item.isWielded()) return true;

            if (!item.isWieldable()) {
                // Might want to mark this as cheating.
                return true;
            }

            ItemWieldableDef wieldableDef = item.getWieldableDef();

            // Check stats required.
            StringBuilder outer = null;
            StringBuilder inner = null;
            for (Map.Entry<Integer, Integer> e : wieldableDef.getStatsRequired()) {
                if (player.getMaxStat(e.getKey()) < e.getValue()) {
                    if (outer == null) {
                        outer = new StringBuilder(100);
                        inner = new StringBuilder(50);

                        outer.append("You must have at least ");
                    }

                    inner.append(e.getValue()).append(" ")
                            .append(Formulae.statArray[e.getKey()])
                            .append(", ");
                }
            }

            // If they lack one or more stats, message them the requirements.
            if (outer != null) {
                outer.append(inner.substring(0, inner.length() - 2))
                        .append(" to use this item.");

                Packets.sendMessage(player, outer.toString());
                return true;
            }

            // If the item is female-only and they're male then tell them
            // that they can't wear the item.
            Appearance appearance = player.getAppearance();
            if (wieldableDef.femaleOnly() && appearance.isMale()) {
                Packets.sendMessage(player, "Only females can wear that item.");
                return true;
            }

            List<InvItem> items = inventory.getItems();
            for (InvItem i : items) {
                if (i.isWielded() && item.wieldingAffectsItem(i)) {
                    i.setWielded(false);
                    ItemWieldableDef def = i.getWieldableDef();
                    player.setWornItem(def.getWieldPos(), appearance.getSprite(def.getWieldPos()));
                }
            }

            item.setWielded(true);
            player.setWornItem(wieldableDef.getWieldPos(), wieldableDef.getSprite());
            Packets.sendSound(player, "click");
            Packets.sendInventory(player);

            return true;
        }
    }
}
