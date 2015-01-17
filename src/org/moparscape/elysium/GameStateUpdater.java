package org.moparscape.elysium;

import org.moparscape.elysium.entity.ChatMessage;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.UpdateProxy;
import org.moparscape.elysium.util.EntityList;
import org.moparscape.elysium.world.Region;
import org.moparscape.elysium.world.World;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class GameStateUpdater {

    private final EntityList<Npc> npcList = World.getInstance().getNpcs();

    private final EntityList<Player> playerList = World.getInstance().getPlayers();

    public void updateCollections() throws Exception {
        // TODO: Unregister any players that have logged out
        for (Player p : playerList) {
            if (p == null) break;
        }

        // Update the player collections, and clear their display lists
        for (Player p : playerList) {
            if (p == null) break;

            UpdateProxy proxy = p.getUpdateProxy();

            proxy.updateEntityLists();
            proxy.clearDisplayLists();
            proxy.clearChatLists();

            proxy.resetSpriteChanged();
            proxy.setAppearanceChanged(false);
        }

        // TODO: Reset the npcs
        for (Npc n : npcList) {
            if (n == null) break;


        }
    }

    private void updateNpcPositions() throws Exception {
        for (Npc n : npcList) {
            if (n == null) break;

            n.resetMoved();
            n.updatePosition();
            // TODO: Is updating the npc's appearance id necessary here?
        }
    }

    private void updatePlayers() throws Exception {
        for (Player p : playerList) {
            if (p == null) break;

            UpdateProxy proxy = p.getUpdateProxy();

            proxy.resetMoved();
            proxy.updatePosition();
            proxy.updateAppearanceId();
        }

        for (Player p : playerList) {
            if (p == null) break;

            UpdateProxy proxy = p.getUpdateProxy();

            proxy.revalidateWatchedEntities();
            proxy.updateWatchedEntities();
        }

        for (Player p : playerList) {
            if (p == null) break;

            try {
                UpdateProxy proxy = p.getUpdateProxy();
                ChatMessage message = proxy.getNextChatMessage();

                if (message == null || !p.isLoggedIn()) {
                    continue;
                }

                for (Player target : Region.getViewablePlayers(p, 16)) {
                    UpdateProxy targetProxy = target.getUpdateProxy();
                    targetProxy.informOfChatMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateState() throws Exception {
        updateNpcPositions();
        updatePlayers();
    }
}
