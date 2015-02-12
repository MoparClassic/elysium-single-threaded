package org.moparscape.elysium.script.npc;

import org.moparscape.elysium.entity.Npc;

/**
 * Created by daniel on 11/02/2015.
 */
public interface NpcActionScriptBlock extends ScriptBlock {

    void actionRun(Npc npc);

    default void run(NpcTalkScriptContext context) {
        actionRun(context.getNpc());
    }
}
