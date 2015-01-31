package org.moparscape.elysium.script;

import java.util.Comparator;

/**
 * Created by daniel on 31/01/2015.
 */
public final class ScriptPriorityComparator implements Comparator<ScriptHandle> {

    public int compare(ScriptHandle a, ScriptHandle b) {
        if (a == null || b == null) throw new IllegalArgumentException("nulls not permitted");

        // Notice that we switch them - we want highest priorities first.
        return Integer.compare(b.getPriority(), a.getPriority());
    }
}
