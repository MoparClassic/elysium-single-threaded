package org.moparscape.elysium.task.timed;

import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Item;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.PlayerState;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.net.Packets;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ItemPickupTask extends WalkToPointTask {

    private final Item item;

    public ItemPickupTask(Player owner, Item item) {
        super(owner, item.getLocation(), 1, false);
        this.item = item;
    }

    public void arrived() {
        PlayerState state = owner.getState();
        if (item.isRemoved() ||
                state != PlayerState.ITEM_PICKUP ||
                !item.isVisibleTo(owner)) {
            return;
        }

        // If the owner of this event is on the square that
        // has the item on it, attempt to take the item
        InvItem invitem = new InvItem(item.getId(), item.getAmount());
        Inventory invent = owner.getInventory();

        // TODO: Run scripts here for any special cases (such as wine of zamorak)
        // These scripts should have the ability to stop the rest of this process

        // If the inventory can't hold the item due to a lack
        // of space, mark this task as finished and return
        if (!invent.canHold(invitem)) {
            Packets.sendMessage(owner, "You cannot pickup this item, your inventory is full!");
            return;
        }

        // We've arrived at the item, and we have space in our
        // inventory - attempt to acquire it.
        // If successful, add the item. If unsuccessful due to
        // the item already being claimed, better luck next time! :)
        boolean acquired = item.remove();
        if (acquired) {
            invent.add(invitem);
            Packets.sendSound(owner, "takeobject");
        }

        return;
    }


}
