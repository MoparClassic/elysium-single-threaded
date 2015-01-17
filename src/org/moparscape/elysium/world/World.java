package org.moparscape.elysium.world;

import org.moparscape.elysium.entity.DefaultEntityFactory;
import org.moparscape.elysium.entity.EntityFactory;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.util.EntityList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class World {

    /**
     * The maximum height of the map (944 squares per level)
     */
    public static final int MAX_HEIGHT = 3776;
    public static final int MAX_NPCS = 5000;
    public static final int MAX_PLAYERS = 1000;
    public static final int MAX_WIDTH = 944;
    private static final EntityFactory ENTITY_FACTORY = new DefaultEntityFactory();
    private static final World INSTANCE;
    private final EntityList<Npc> npcList = new EntityList<>(MAX_NPCS);

    static {
        INSTANCE = new World();
    }

    private final TileValue outsideWorld = new TileValue();
    private final EntityList<Player> playerList = new EntityList<>(MAX_PLAYERS);
    private final TileValue[][] tileType = new TileValue[MAX_WIDTH][MAX_HEIGHT];

    private int currentPlayerCount = 0;

    private World() {
        this.outsideWorld.mapValue = Byte.MAX_VALUE;
        this.outsideWorld.objectValue = Byte.MAX_VALUE;
    }

    public static EntityFactory getEntityFactory() {
        return ENTITY_FACTORY;
    }

    public static World getInstance() {
        return INSTANCE;
    }

    public EntityList<Npc> getNpcs() {
        return npcList;
    }

    public EntityList<Player> getPlayers() {
        return playerList;
    }

    /**
     * Gets the tile value as point x, y
     */
    public TileValue getTileValue(int x, int y) {
        if (!withinWorld(x, y)) {
            return outsideWorld;
        }
        TileValue t = tileType[x][y];
        if (t == null) {
            t = new TileValue();
            tileType[x][y] = t;
        }
        return t;
    }

    public boolean registerNpc(Npc npc) {
        return npcList.add(npc);
    }

    public boolean registerPlayer(Player p) {
        return playerList.add(p);
    }

    public boolean unregisterPlayer(Player p) {
        if (p == null) return false;

        p.setLoggedIn(false);
        playerList.remove(p);

        Point location = p.getLocation();
        if (location != null) {
            Region r = Region.getRegion(location);
            r.removePlayer(p);
        }

        return true;
    }

    /**
     * Are the given coords within the world boundaries
     */
    public boolean withinWorld(int x, int y) {
        return x >= 0 && x < MAX_WIDTH && y >= 0 && y < MAX_HEIGHT;
    }
}
