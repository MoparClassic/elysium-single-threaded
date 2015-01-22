package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.def.ItemWieldableDef;
import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.entity.component.PlayerSprite;
import org.moparscape.elysium.entity.component.Skills;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.ItemWieldMessage;
import org.moparscape.elysium.util.Formulae;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemWieldMessageHandler extends MessageHandler<ItemWieldMessage> {

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
        Skills skills = player.getSkills();
        StringBuilder outer = null;
        StringBuilder inner = null;
        for (Map.Entry<Integer, Integer> e : wieldableDef.getStatsRequired()) {
            if (skills.getMaxStat(e.getKey()) < e.getValue()) {
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
        PlayerSprite sprite = player.getSprite();
        Appearance appearance = sprite.getAppearance();
        if (wieldableDef.femaleOnly() && appearance.isMale()) {
            Packets.sendMessage(player, "Only females can wear that item.");
            return true;
        }

        List<InvItem> items = inventory.getItems();
        for (InvItem i : items) {
            if (i.isWielded() && item.wieldingAffectsItem(i)) {
                i.setWielded(false);
                ItemWieldableDef def = i.getWieldableDef();
                sprite.setWornItem(def.getWieldPos(), appearance.getSprite(def.getWieldPos()));
            }
        }

        item.setWielded(true);
        sprite.setWornItem(wieldableDef.getWieldPos(), wieldableDef.getSprite());
        Packets.sendSound(player, "click");
        inventory.sendInventory();

        return true;
    }
}
