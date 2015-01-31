package org.moparscape.elysium.script;

import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Npc;

/**
 * Created by daniel on 31/01/2015.
 */
public interface ItemOnNpcScript extends Script {

    boolean run(InvItem item, Npc npc);
}
