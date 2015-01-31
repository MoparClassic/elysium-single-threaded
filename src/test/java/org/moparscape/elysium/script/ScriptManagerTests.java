package org.moparscape.elysium.script;

import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Created by daniel on 31/01/2015.
 */
public class ScriptManagerTests {

    private static final int NON_DEFAULT_PRIORITY = 10;
    private final InvItem item = new InvItem(1, 1);
    private final Npc npc = new Npc(1);
    private final Player player = new Player();

    @Test
    public void executeDefaultItemOnItemScript_InvariantsTest() {
        try {
            ScriptManager.executeDefaultItemOnItemScript(null, item, item);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"player".equals(e.getMessage())) throw new RuntimeException("player");
        }

        try {
            ScriptManager.executeDefaultItemOnItemScript(player, null, item);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"itemA".equals(e.getMessage())) throw new RuntimeException("itemA");
        }

        try {
            ScriptManager.executeDefaultItemOnItemScript(player, item, null);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"itemB".equals(e.getMessage())) throw new RuntimeException("itemB");
        }
    }

    @Test
    public void executeDefaultItemOnNpcScript_InvariantsTest() {
        try {
            ScriptManager.executeDefaultItemOnNpcScript(null, item, npc);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"player".equals(e.getMessage())) throw new RuntimeException("player");
        }

        try {
            ScriptManager.executeDefaultItemOnNpcScript(player, null, npc);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"item".equals(e.getMessage())) throw new RuntimeException("item");
        }

        try {
            ScriptManager.executeDefaultItemOnNpcScript(player, item, null);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"npc".equals(e.getMessage())) throw new RuntimeException("npc");
        }
    }

    @Test
    public void executeItemOnItemScripts_InvariantsTest() {
        try {
            ScriptManager.executeItemOnItemScripts(null, item, item);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"player".equals(e.getMessage())) throw new RuntimeException("player");
        }

        try {
            ScriptManager.executeItemOnItemScripts(player, null, item);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"itemA".equals(e.getMessage())) throw new RuntimeException("itemA");
        }

        try {
            ScriptManager.executeItemOnItemScripts(player, item, null);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"itemB".equals(e.getMessage())) throw new RuntimeException("itemB");
        }
    }

    @Test
    public void executeItemOnNpcScripts_InvariantsTest() {
        try {
            ScriptManager.executeItemOnNpcScripts(null, item, npc);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"player".equals(e.getMessage())) throw new RuntimeException("player");
        }

        try {
            ScriptManager.executeItemOnNpcScripts(player, null, npc);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"item".equals(e.getMessage())) throw new RuntimeException("item");
        }

        try {
            ScriptManager.executeItemOnNpcScripts(player, item, null);
            throw new RuntimeException();
        } catch (NullPointerException e) {
            if (!"npc".equals(e.getMessage())) throw new RuntimeException("npc");
        }
    }

    @BeforeSuite
    public void setupScripts() {
        ScriptManager.registerItemOnItemScript(1, 1, ScriptManager.PRIORITY_DEFAULT,
                (player, itemA, itemB) -> {
                    return true;
                });
        ScriptManager.registerItemOnItemScript(1, 1, NON_DEFAULT_PRIORITY,
                (player, itemA, itemB) -> {
                    return true;
                });

        ScriptManager.registerItemOnNpcScript(1, 1, ScriptManager.PRIORITY_DEFAULT,
                (player, item, npc) -> {
                    return true;
                });
        ScriptManager.registerItemOnNpcScript(1, 1, NON_DEFAULT_PRIORITY,
                (player, item, npc) -> {
                    return true;
                });
    }
}
