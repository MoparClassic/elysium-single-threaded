package org.moparscape.elysium.script.npc;

import org.moparscape.elysium.entity.Player;

/**
 * Created by daniel on 11/02/2015.
 */
public interface PlayerActionScriptBlock extends ScriptBlock {

    void actionRun(Player player);

    default void run(NpcTalkScriptContext context) {
        actionRun(context.getPlayer());
    }
}
