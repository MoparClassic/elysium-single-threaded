package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.def.ItemDef;
import org.moparscape.elysium.entity.EntityHandler;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Bank;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.BankCloseMessage;
import org.moparscape.elysium.net.codec.decoder.message.BankDepositMessage;
import org.moparscape.elysium.net.codec.decoder.message.BankWithdrawMessage;

/**
 * Created by daniel on 31/01/2015.
 */
public final class BankMessageHandlers {

    public static final class BankCloseMessageHandler extends MessageHandler<BankCloseMessage> {

        public boolean handle(Session session, Player player, BankCloseMessage message) {
            player.resetBank();
            return true;
        }
    }

    public static final class BankDepositMessageHandler extends MessageHandler<BankDepositMessage> {

        public boolean handle(Session session, Player player, BankDepositMessage message) {
            Inventory inventory = player.getInventory();

            int itemId = message.getItemId();
            int amount = message.getAmount();
            if (amount < 1 || inventory.countById(itemId) < amount) {
                // Cheater?
                return true;
            }

            ItemDef def = EntityHandler.getItemDef(itemId);
            Bank bank = player.getBank();
            if (def.isStackable()) {
                InvItem item = new InvItem(itemId, amount);
                if (bank.canHold(item) && inventory.remove(item) > -1) {
                    bank.add(item);
                } else {
                    Packets.sendMessage(player, "You don't have room for that in your bank");
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    InvItem item = inventory.getLast(itemId);
                    if (item == null) break;

                    if (bank.canHold(item) && inventory.remove(item) > -1) {
                        bank.add(item);
                    } else {
                        Packets.sendMessage(player, "You don't have room for that in your bank");
                        break;
                    }
                }
            }

            int itemIndex = bank.getFirstIndexById(itemId);
            if (itemIndex > -1) {
                Packets.sendInventory(player);
                Packets.updateBankItem(player, itemIndex, itemId, bank.countById(itemId));
            }

            return true;
        }
    }

    public static final class BankWithdrawMessageHandler extends MessageHandler<BankWithdrawMessage> {

        public boolean handle(Session session, Player player, BankWithdrawMessage message) {
            Bank bank = player.getBank();

            int itemId = message.getItemId();
            int amount = message.getAmount();
            if (amount < 1 || bank.countById(itemId) < amount) {
                // Cheater?
                return true;
            }

            Inventory inventory = player.getInventory();
            ItemDef def = EntityHandler.getItemDef(itemId);
            int itemIndex = bank.getFirstIndexById(itemId);
            if (def.isStackable()) {
                InvItem item = new InvItem(itemId, amount);
                if (inventory.canHold(item) && bank.remove(item) > -1) {
                    inventory.add(item);
                } else {
                    Packets.sendMessage(player, "You don't have room for that in your inventory");
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    InvItem item = bank.getLast(itemId);
                    if (item == null) break;

                    if (inventory.canHold(item) && bank.remove(item) > -1) {
                        inventory.add(item);
                    } else {
                        Packets.sendMessage(player, "You don't have room for that in your inventory");
                        break;
                    }
                }
            }

            if (itemIndex > -1) {
                Packets.sendInventory(player);
                Packets.updateBankItem(player, itemIndex, itemId, bank.countById(itemId));
            }

            return true;
        }
    }
}
