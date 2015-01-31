package org.moparscape.elysium.script;

import org.moparscape.elysium.entity.InvItem;
import org.moparscape.elysium.entity.Player;

/**
 * Created by daniel on 31/01/2015.
 */
public interface ItemOnItemScript extends Script {

    boolean run(Player player, InvItem a, InvItem b);
}
