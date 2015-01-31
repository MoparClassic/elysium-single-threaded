package org.moparscape.elysium.script;

import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by daniel on 31/01/2015.
 */
public final class ScriptManager {

    public static final int PRIORITY_DEFAULT = 0;
    public static final Comparator<ScriptHandle> SCRIPT_COMPARATOR =
            new ScriptPriorityComparator();

    private static final Map<Key, List<ScriptHandle<ItemOnItemScript>>> itemOnItemScriptMap = new HashMap<>();
    private static final Map<Key, List<ScriptHandle<ItemOnNpcScript>>> itemOnNpcScriptMap = new HashMap<>();

    private static <S extends Script> List<ScriptHandle<S>> checkAndGetScriptList(
            Key key, Map<Key, List<ScriptHandle<S>>> map) {
        List<ScriptHandle<S>> scripts = map.get(key);
        int size = scripts.size();
        if (size == 0) throw new IllegalStateException("No scripts registered");

        return scripts;
    }

    private static <S extends Script> void checkForExistingDefaultScript(
            List<ScriptHandle<S>> scriptList, int insertionPriority) {
        if (scriptList == null) throw new NullPointerException("scriptList");

        if (PRIORITY_DEFAULT != insertionPriority) return;

        for (ScriptHandle<S> h : scriptList) {
            if (h.getPriority() == PRIORITY_DEFAULT) {
                throw new IllegalArgumentException("Default script already set: " +
                        h.getScript().getClass().getName());
            }
        }
    }

    private static <S extends Script> ScriptHandle<S> checkLastIndexForDefaultScript(
            List<ScriptHandle<S>> scripts) {
        int scriptIndex = scripts.size() - 1;
        ScriptHandle<S> handle = scripts.get(scriptIndex);

        if (handle.getPriority() != PRIORITY_DEFAULT) {
            throw new IllegalStateException("No default script registered");
        }

        return handle;
    }

    public static void executeDefaultItemOnItemScript(Player player, InvItem itemA, InvItem itemB) {
        if (player == null) throw new NullPointerException("player");
        if (itemA == null) throw new NullPointerException("itemA");
        if (itemB == null) throw new NullPointerException("itemB");

        Key key = new ItemOnItemKey(itemA.getItemId(), itemB.getItemId());
        List<ScriptHandle<ItemOnItemScript>> scripts = checkAndGetScriptList(key, itemOnItemScriptMap);
        ScriptHandle<ItemOnItemScript> handle = checkLastIndexForDefaultScript(scripts);
        handle.getScript().run(player, itemA, itemB);
    }

    public static void executeDefaultItemOnNpcScript(Player player, InvItem item, Npc npc) {
        if (player == null) throw new NullPointerException("player");
        if (item == null) throw new NullPointerException("item");
        if (npc == null) throw new NullPointerException("npc");

        Key key = new ItemOnNpcKey(item.getItemId(), npc.getId());
        List<ScriptHandle<ItemOnNpcScript>> scripts = checkAndGetScriptList(key, itemOnNpcScriptMap);
        ScriptHandle<ItemOnNpcScript> handle = checkLastIndexForDefaultScript(scripts);
        handle.getScript().run(player, item, npc);
    }

    public static void executeItemOnItemScripts(Player player, InvItem itemA, InvItem itemB) {
        if (player == null) throw new NullPointerException("player");
        if (itemA == null) throw new NullPointerException("itemA");
        if (itemB == null) throw new NullPointerException("itemB");

        Key key = new ItemOnItemKey(itemA.getItemId(), itemB.getItemId());
        List<ScriptHandle<ItemOnItemScript>> scripts = checkAndGetScriptList(key, itemOnItemScriptMap);

        for (ScriptHandle<ItemOnItemScript> handle : scripts) {
            boolean continueExecuting = handle.getScript().run(player, itemA, itemB);

            if (!continueExecuting) return;
        }
    }

    public static void executeItemOnNpcScripts(Player player, InvItem item, Npc npc) {
        if (player == null) throw new NullPointerException("player");
        if (item == null) throw new NullPointerException("item");
        if (npc == null) throw new NullPointerException("npc");

        Key key = new ItemOnNpcKey(item.getItemId(), npc.getId());
        List<ScriptHandle<ItemOnNpcScript>> scripts = checkAndGetScriptList(key, itemOnNpcScriptMap);

        for (ScriptHandle<ItemOnNpcScript> handle : scripts) {
            boolean continueExecuting = handle.getScript().run(player, item, npc);

            if (!continueExecuting) return;
        }
    }

    private static <S extends Script> List<ScriptHandle<S>> getExistingOrCreateList(
            Key key, Map<Key, List<ScriptHandle<S>>> map) {
        List<ScriptHandle<S>> scriptList = map.get(key);
        if (scriptList == null) {
            scriptList = new CopyOnWriteArrayList<>();
            map.put(key, scriptList);
        }

        return scriptList;
    }

    public static void registerItemOnItemScript(int itemIdA, int itemIdB, int priority, ItemOnItemScript script) {
        if (script == null) throw new NullPointerException("script");
        if (priority < 0) throw new IllegalArgumentException("priority");

        ScriptHandle<ItemOnItemScript> handle = new ScriptHandle<>(script, priority);
        Key key = new ItemOnItemKey(itemIdA, itemIdB);
        registerScript(key, handle, itemOnItemScriptMap, priority);
    }

    public static void registerItemOnNpcScript(int itemId, int npcId, int priority, ItemOnNpcScript script) {
        if (script == null) throw new NullPointerException("script");
        if (priority < 0) throw new IllegalArgumentException("priority");

        ScriptHandle<ItemOnNpcScript> handle = new ScriptHandle<>(script, priority);
        Key key = new ItemOnNpcKey(itemId, npcId);
        registerScript(key, handle, itemOnNpcScriptMap, priority);
    }

    private static <S extends Script> void registerScript(Key key, ScriptHandle<S> handle,
                                                          Map<Key, List<ScriptHandle<S>>> map,
                                                          int priority) {
        List<ScriptHandle<S>> scriptList = getExistingOrCreateList(key, map);
        checkForExistingDefaultScript(scriptList, priority);

        scriptList.add(handle);
        scriptList.sort(SCRIPT_COMPARATOR);
    }
}
