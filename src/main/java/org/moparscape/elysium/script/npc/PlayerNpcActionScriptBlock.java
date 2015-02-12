package org.moparscape.elysium.script.npc;

import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;

/**
 * Created by daniel on 11/02/2015.
 */
public interface PlayerNpcActionScriptBlock extends ScriptBlock {

    void actionRun(Player player, Npc npc);

    default void run(NpcTalkScriptContext context) {
        Player p = context.getPlayer();
        Npc n = context.getNpc();
        actionRun(p, n);
    }
}
