package org.moparscape.elysium.entity;

import org.moparscape.elysium.def.NPCLoc;
import org.moparscape.elysium.net.Session;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public interface EntityFactory {

    Npc newNpc(NPCLoc loc);

    Player newPlayer(Session session);
}
