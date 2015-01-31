package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.def.ThievableNPCDef;
import org.moparscape.elysium.entity.EntityHandler;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.Packets;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.NpcActionMessage;
import org.moparscape.elysium.net.codec.decoder.message.NpcChatMessage;

/**
 * Created by daniel on 31/01/2015.
 */
public final class NpcMessageHandlers {

    public static final class NpcActionMessageHandler extends MessageHandler<NpcActionMessage> {
        @Override
        public boolean handle(Session session, Player player, NpcActionMessage message) {
            // TODO: Determine if the short value sent to client is npc index or npc id.
            Packets.sendMessage(player, "NPC thieving needs to be validated");

            ThievableNPCDef tnpcdef = EntityHandler.getThievableNPCDef(message.getNpcIndex());
            if (tnpcdef == null) { //if tnpcdef == null then the npc in question was not meant to be able to be thieved
                return true;
            }

            int reqLevel = tnpcdef.getRequiredThievingLevel();
            int curLevel = player.getCurStat(17);
            if (curLevel < reqLevel) {
                Packets.sendMessage(player, "Your thieving level is too low. It needs to be at least " + reqLevel);
                return true;
            }

            // TODO: Finish npc thieving.

//            boolean successful = Formulae.thieveNPC(player, tnpcdef);
            return true;
        }
    }

    public static final class NpcChatMessageHandler extends MessageHandler<NpcChatMessage> {
        @Override
        public boolean handle(Session session, Player player, NpcChatMessage message) {
            return true;
        }
    }
}
