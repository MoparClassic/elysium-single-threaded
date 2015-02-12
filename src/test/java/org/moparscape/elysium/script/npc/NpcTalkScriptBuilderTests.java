package org.moparscape.elysium.script.npc;

import org.moparscape.elysium.entity.ChatMessage;
import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Inventory;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.script.NpcTalkScript;
import org.testng.annotations.Test;

/**
 * Created by daniel on 2/02/2015.
 */
public class NpcTalkScriptBuilderTests {

    @Test
    public void bankerScriptExperimentation() {
        NpcTalkScriptBuilder deeplyNestedOptions = new NpcTalkScriptBuilder();
        deeplyNestedOptions.bindNpcs(95, 224, 268, 485, 540, 617, 792);
        deeplyNestedOptions.addOptions("Option 1", "Option 2", "Option 3");
        deeplyNestedOptions.addChildOptions("Option 1", "O1C1", "O1C2");
        deeplyNestedOptions.addChildOptions("Option 2", "O2C1", "O2C2");
        deeplyNestedOptions.addChildOptions("O2C2", "O2C2-1", "O2C2-2");
        deeplyNestedOptions.addChildOptions("O2C2-1", "Lol", "Hi");
        deeplyNestedOptions.addChildOptions("Lol", "Noobs", "Okay guy");

        NpcTalkScriptBuilder bankerScriptBuilder = new NpcTalkScriptBuilder();
        bankerScriptBuilder.bindNpcs(95, 224, 268, 485, 540, 617, 792);
        bankerScriptBuilder
                .playerBusy(true)
                .playerSays("I'd like to access my bank account please")
                .sleep(1500)
//                .npcSays((p, n) -> "Certainly")
                .sleep(1500)
                .playerBusy(false)
                .playerAction((x) -> x.setBusy(false));

        bankerScriptBuilder.thenExecuteDelayed(1500, (ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();

            p.informOfNpcChatMessage(new ChatMessage(n, p,
                    "Certainly " + (p.getAppearance().isMale() ? "sir" : "miss")));
        });
        bankerScriptBuilder.thenExecuteDelayed(1500,
                (ctx) -> {
                    Player p = ctx.getPlayer();

                    p.setBusy(false);
                    p.setAccessingBank(true);
                    Packets.showBank(p);
                });
        NpcTalkScript bankerScript = bankerScriptBuilder.finaliseScript();

        NpcTalkScriptBuilder tannerScriptBuilder = new NpcTalkScriptBuilder();
        tannerScriptBuilder.bindNpcs(172);

        // Set the root options.
        tannerScriptBuilder.addOptions(
                "Can I buy some leather then?",
                "Here's some cow hides, can I buy some leather now?",
                "Leather is rather weak stuff");

        // Add script blocks to be executed before displaying the options.
        tannerScriptBuilder.executeImmediately((ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();

            p.setBusy(true);
            p.informOfNpcChatMessage(new ChatMessage(n, p, "Greeting friend. I'm a manufacturer of leather."));
        }).thenExecuteDelayed(1500, (ctx) -> {
            Player p = ctx.getPlayer();
            p.setBusy(false);
            // Menu with options automatically sent at this point.
        });

        // Add script blocks to be executed if user selects option
        // "Can I buy some leather then?"
        tannerScriptBuilder.executeImmediately("Can I buy some leather then?", (ctx) -> {
            Player p = ctx.getPlayer();
            if (p.isBusy()) throw new PlayerBusyException();

            Npc n = ctx.getNpc();
            String option = ctx.getOption();
            p.informOfChatMessage(new ChatMessage(p, n, option));
            p.setBusy(true);
        }).thenExecuteDelayed(1500, (ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();
            p.informOfNpcChatMessage(new ChatMessage(n, p,
                    "I make leather from cow hides."));
        }).thenExecuteDelayed(1500, (ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();
            p.informOfNpcChatMessage(new ChatMessage(n, p,
                    "Bring me some of them and a gold coin per hide."));
            n.unblock();
        });

        // Add script blocks to be executed if user selects option
        // "Here's some cow hides, can I buy some leather now?"
        tannerScriptBuilder.executeImmediately("Here's some cow hides, can I buy some leather now?", (ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();

            p.informOfNpcChatMessage(new ChatMessage(n, p, "Okay."));
            n.unblock();
        }).thenExecuteDelayedLoop(500, (ctx) -> {
            Player p = ctx.getPlayer();
            Inventory inventory = p.getInventory();
            InvItem lastHides = inventory.getLast(147);

            try {
                if (lastHides == null) {
                    Packets.sendMessage(p, "You have run out of cow hides.");
                    throw new NpcTalkInterruptedException();
                } else if (inventory.countById(10) < 1) {
                    Packets.sendMessage(p, "You have run out of coins.");
                    throw new NpcTalkInterruptedException();
                } else if (inventory.remove(lastHides) > -1 && inventory.remove(10, 1) > -1) {
                    inventory.add(new InvItem(148, 1));
                    Packets.sendInventory(p);
                } else {
                    throw new NpcTalkInterruptedException();
                }
            } finally {
                p.setBusy(false);
            }
        });

        // Add script blocks to be executed if user selects option
        // "Leather is rather weak stuff"
        tannerScriptBuilder.executeImmediately("Leather is rather weak stuff", (ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();

            p.setBusy(false);
            n.unblock();
            p.informOfNpcChatMessage(new ChatMessage(n, p,
                    "Well yes, if all you're concerned about is how much it " +
                            "will protect you in a fight."));
        });

        NpcTalkScript tannerScript = tannerScriptBuilder.finaliseScript();

        int x = 1;
    }
}
