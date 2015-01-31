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

    public void updateCollections() throws Exception {
        World world = World.getInstance();

        // Update the player collections, and clear their display lists
        for (Player p : world.getPlayers()) {
            UpdateProxy proxy = p.getUpdateProxy();

            proxy.updateEntityLists();
            proxy.clearDisplayLists();
            p.clearChatLists();

            p.resetSpriteChanged();
            p.setAppearanceChanged(false);
        }

        // TODO: Reset the npcs
        for (Npc n : world.getNpcs()) {
            n.resetSpriteChanged();
        }
    }

    private void updateNpcPositions() throws Exception {
        World world = World.getInstance();

        for (Npc n : world.getNpcs()) {
            n.resetMoved();
            n.updatePosition();
            // TODO: Is updating the npc's appearance id necessary here?
        }
    }

    private void updatePlayers() {
        EntityList<Player> playerList = World.getInstance().getPlayers();

        for (Player p : playerList) {
            UpdateProxy proxy = p.getUpdateProxy();

            proxy.resetMoved();
            proxy.updatePosition();
            p.updateAppearanceId();
        }

        for (Player p : playerList) {
            UpdateProxy proxy = p.getUpdateProxy();

            proxy.updateViewableEntities();
            proxy.revalidateWatchedEntities();
            proxy.updateWatchedEntities();
            proxy.cleanupViewableEntities();
        }

        for (Player p : playerList) {
            try {
                ChatMessage message = p.getNextChatMessage();

                if (message == null || !p.isLoggedIn()) {
                    continue;
                }

                for (Player target : Region.getViewablePlayers(p, 16)) {
                    target.informOfChatMessage(message);
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
