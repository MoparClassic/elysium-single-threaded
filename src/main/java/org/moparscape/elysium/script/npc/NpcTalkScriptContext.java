package org.moparscape.elysium.script.npc;

import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;

/**
 * Created by daniel on 2/02/2015.
 */
public final class NpcTalkScriptContext {

    private final Npc npc;
    private final String option;
    private final Player player;

    public NpcTalkScriptContext(Player player, Npc npc, String option) {
        if (player == null) throw new NullPointerException("player");
        if (npc == null) throw new NullPointerException("npc");

        this.player = player;
        this.npc = npc;
        this.option = option;
    }

    public Npc getNpc() {
        return npc;
    }

    public String getOption() {
        return option;
    }

    public Player getPlayer() {
        return player;
    }
}
