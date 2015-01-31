package org.moparscape.elysium.script;

/**
 * Created by daniel on 31/01/2015.
 */
public final class ScriptHandle<T extends Script> {

    private final int priority;
    private final T script;

    public ScriptHandle(T script, int priority) {
        if (script == null) throw new IllegalArgumentException("script");
        if (priority < 0) throw new IllegalArgumentException("priority");

        this.script = script;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public T getScript() {
        return script;
    }
}
