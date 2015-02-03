package org.moparscape.elysium.script.npc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 3/02/2015.
 */
public final class OptionHandle {

    private final List<OptionHandle> childOptions = new ArrayList<>();
    private final String option;
    private final NpcTalkScriptBuilder script;
    private final List<ScriptBlockHandle> scriptBlockHandles = new ArrayList<>();

    public OptionHandle(String option, NpcTalkScriptBuilder script) {
        if (option == null) throw new NullPointerException("option");
        if (script == null) throw new NullPointerException("script");

        this.option = option;
        this.script = script;
    }

    public void addOption(OptionHandle handle) {
        if (handle == null) throw new NullPointerException("handle");

        childOptions.add(handle);
    }

    public void addScriptBlock(ScriptBlockHandle handle) {
        if (handle == null) throw new NullPointerException("handle");

        scriptBlockHandles.add(handle);
    }

    public void clearScriptBlocks() {
        scriptBlockHandles.clear();
    }

    public List<OptionHandle> getChildOptions() {
        return childOptions;
    }

    public String getOption() {
        return option;
    }

    @Override
    public int hashCode() {
        return option.hashCode() + script.hashCode() * 31;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        OptionHandle other = (OptionHandle) o;
        return this.option.equals(other.option) &&
                this.script == other.script;
    }
}
