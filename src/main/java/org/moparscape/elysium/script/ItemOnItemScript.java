package org.moparscape.elysium.script;

import org.moparscape.elysium.entity.InvItem;

/**
 * Created by daniel on 31/01/2015.
 */
public interface ItemOnItemScript extends Script {

    boolean run(InvItem a, InvItem b);
}
