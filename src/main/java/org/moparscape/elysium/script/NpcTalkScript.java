package org.moparscape.elysium.script;

import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;

/**
 * Created by daniel on 4/02/2015.
 */
public interface NpcTalkScript extends Script {

    boolean run(Player player, Npc npc);
}
