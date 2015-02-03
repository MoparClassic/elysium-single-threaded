package org.moparscape.elysium.script.npc;

/**
 * Created by daniel on 3/02/2015.
 */
public final class ScriptBlockHandle {

    private final ScriptBlock block;

    public ScriptBlockHandle(ScriptBlock block) {
        if (block == null) throw new NullPointerException("block");

        this.block = block;
    }

    public ScriptBlock getBlock() {
        return block;
    }
}
