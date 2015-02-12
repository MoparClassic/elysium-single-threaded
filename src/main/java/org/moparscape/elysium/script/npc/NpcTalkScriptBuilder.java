package org.moparscape.elysium.script.npc;

import org.moparscape.elysium.entity.ChatMessage;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.script.NpcTalkScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by daniel on 2/02/2015.
 */
public final class NpcTalkScriptBuilder {

    private final List<Integer> boundNpcs = new CopyOnWriteArrayList<>();
    private final List<OptionHandle> conversationOptions = new CopyOnWriteArrayList<>();
    private final List<ScriptBlockHandle> scriptBlockHandles = new CopyOnWriteArrayList<>();
    private OptionHandle currentlySelectedOption;

    public NpcTalkScriptBuilder() {

    }

    public OptionHandle addChildOption(OptionHandle parent, String childOption) {
        if (parent == null) throw new NullPointerException("parent");
        if (childOption == null) throw new NullPointerException("childOption");

        OptionHandle existing = findOption(parent.getOption());
        checkOption(existing);

        OptionHandle child = new OptionHandle(childOption, this);
        return child;
    }

    public NpcTalkScriptBuilder addChildOptions(String baseOption, String... childOptions) {
        if (baseOption == null) throw new NullPointerException("baseOption");
        if (childOptions == null) throw new NullPointerException("childOptions");

        OptionHandle existing = findOption(baseOption);
        checkOption(existing);

        for (String child : childOptions) {
            existing.addOption(new OptionHandle(child, this));
        }

        return this;
    }

    public OptionHandle addOption(String option) {
        if (option == null) throw new NullPointerException("option");

        OptionHandle handle = new OptionHandle(option, new NpcTalkScriptBuilder());
        return handle;
    }

    public NpcTalkScriptBuilder addOptions(String... options) {
        if (options == null) throw new NullPointerException("options");

        for (String s : options) {
            conversationOptions.add(new OptionHandle(s, new NpcTalkScriptBuilder()));
        }

        return this;
    }

    public NpcTalkScriptBuilder bindNpcs(int... npcIds) {
        if (npcIds == null) throw new NullPointerException("npcIds");

        for (int id : npcIds) {
            boundNpcs.add(id);
        }

        return this;
    }

    private void checkOption(OptionHandle option) {
        if (option == null) {
            throw new IllegalStateException(
                    String.format("Option '%s' not exist", option));
        }
    }

    public NpcTalkScriptBuilder executeDelayed(String option, long delay, ScriptBlock block) {
        if (option == null) throw new NullPointerException("option");
        if (delay < 0) throw new IllegalArgumentException("Negative delay");
        if (block == null) throw new NullPointerException("block");

        // Set the currently selected option. This will be the option
        // that we work on configuring until another option is selected.
        currentlySelectedOption = findOption(option);
        checkOption(currentlySelectedOption);

        // Reset the current script blocks.
        currentlySelectedOption.clearScriptBlocks();

        ScriptBlockHandle blockHandle = new ScriptBlockHandle(block);
        currentlySelectedOption.addScriptBlock(blockHandle);

        return this;
    }

    public NpcTalkScriptBuilder executeDelayed(long delay, ScriptBlock block) {
        if (delay < 0) throw new IllegalArgumentException("Negative delay");
        if (block == null) throw new NullPointerException("block");

        // Clear the currently selected option. We're going to work on
        // configuring the root script blocks (those executed before the
        // player can start selecting conversation options).
        currentlySelectedOption = null;

        // Reset the current script blocks.
        scriptBlockHandles.clear();

        ScriptBlockHandle blockHandle = new ScriptBlockHandle(block);
        scriptBlockHandles.add(blockHandle);

        return this;
    }

    public NpcTalkScriptBuilder executeImmediately(String option, ScriptBlock mainBlock) {
        if (option == null) throw new NullPointerException("option");
        if (mainBlock == null) throw new NullPointerException("mainBlock");

        currentlySelectedOption = findOption(option);
        checkOption(currentlySelectedOption);

        currentlySelectedOption.clearScriptBlocks();

        ScriptBlockHandle blockHandle = new ScriptBlockHandle(mainBlock);
        currentlySelectedOption.addScriptBlock(blockHandle);

        return this;
    }

    public NpcTalkScriptBuilder executeImmediately(ScriptBlock block) {
        if (block == null) throw new NullPointerException("block");

        // Clear the currently selected option. We're going to work on
        // configuring the root script blocks (those executed before the
        // player can start selecting conversation options).
        currentlySelectedOption = null;

        // Reset the current script blocks.
        scriptBlockHandles.clear();

        ScriptBlockHandle blockHandle = new ScriptBlockHandle(block);
        scriptBlockHandles.add(blockHandle);

        return this;
    }

    public NpcTalkScript finaliseScript() {
        return new ComplexNpcTalkScript();
    }

    private OptionHandle findOption(String option) {
        if (option == null) throw new NullPointerException("option");

        for (OptionHandle handle : getAllOptionHandles()) {
            if (option.equals(handle.getOption())) return handle;
        }

        return null;
    }

    private List<OptionHandle> getAllOptionHandles() {
        Stack<OptionHandle> handles = new Stack<>();
        for (OptionHandle handle : conversationOptions) {
            handles.add(handle);
        }

        List<OptionHandle> allHandles = new ArrayList<>();
        while (handles.size() > 0) {
            OptionHandle currentHandle = handles.pop();
            allHandles.add(currentHandle);

            List<OptionHandle> children = currentHandle.getChildOptions();
            for (OptionHandle child : children) {
                handles.add(child);
            }
        }

        return allHandles;
    }

    public NpcTalkScriptBuilder npcSays(String message) {
        if (message == null) throw new NullPointerException("message");

        ScriptBlockHandle handle = new ScriptBlockHandle((ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();

            p.informOfNpcChatMessage(new ChatMessage(n, p, message));
        });

        scriptBlockHandles.add(handle);

        return this;
    }

    public NpcTalkScriptBuilder npcSays(PlayerNpcActionScriptBlock block) {
        if (block == null) throw new NullPointerException("block");

        ScriptBlockHandle handle = new ScriptBlockHandle(block);
        scriptBlockHandles.add(handle);
        return this;
    }

    public NpcTalkScriptBuilder playerAction(PlayerActionScriptBlock block) {
        if (block == null) throw new NullPointerException("block");

        ScriptBlockHandle handle = new ScriptBlockHandle(block);
        return this;
    }

    public NpcTalkScriptBuilder playerBusy(boolean busy) {
        ScriptBlockHandle handle = new ScriptBlockHandle((ctx) -> {
            Player p = ctx.getPlayer();
            p.setBusy(busy);
        });

        scriptBlockHandles.add(handle);

        return this;
    }

    public NpcTalkScriptBuilder playerSays(String message) {
        if (message == null) throw new NullPointerException("message");

        ScriptBlockHandle handle = new ScriptBlockHandle((ctx) -> {
            Player p = ctx.getPlayer();
            Npc n = ctx.getNpc();

            p.informOfChatMessage(new ChatMessage(p, n, message));
        });

        scriptBlockHandles.add(handle);

        return this;
    }

    public NpcTalkScriptBuilder sleep(long milliseconds) {
        return this;
    }

    public NpcTalkScriptBuilder thenExecuteDelayed(long delay, ScriptBlock block) {
        return this;
    }

    public NpcTalkScriptBuilder thenExecuteDelayedLoop(long delay, ScriptBlock block) {
        return this;
    }

    public NpcTalkScriptBuilder thenExecuteImmediately(String option, ScriptBlock mainBlock) {
        if (option == null) throw new NullPointerException("option");
        if (mainBlock == null) throw new NullPointerException("mainBlock");

        OptionHandle handle = findOption(option);
        checkOption(handle);

        ScriptBlockHandle blockHandle = new ScriptBlockHandle(mainBlock);
        handle.addScriptBlock(blockHandle);

        return this;
    }

    public NpcTalkScriptBuilder thenExecuteImmediately(ScriptBlock block) {
        return this;
    }
}
