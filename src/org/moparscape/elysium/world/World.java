package org.moparscape.elysium.world;

import org.moparscape.elysium.entity.*;
import org.moparscape.elysium.util.EntityList;

import java.security.SecureRandom;
import java.util.Random;

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

    private int getRandomOrdinate(Random r, int basePoint, int maxDistance) {
        boolean negative = r.nextBoolean();
        int offset = r.nextInt() % maxDistance;

        if (negative) offset *= -1;

        return basePoint + offset;
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

    public void seedWithEntities() {
        Point workingLocation = new Point(329, 552);
        SecureRandom random = new SecureRandom();
        random.setSeed(13333333333337L);

        for (int i = 0; i < 50; i++) {
            int x = getRandomOrdinate(random, workingLocation.getX(), 10);
            int y = getRandomOrdinate(random, workingLocation.getY(), 10);

            Point loc = new Point(x, y);
            Item item = new Item(i, 1, loc, null);
            Region.getRegion(loc).addItem(item);
        }

        for (int i = 1; i < 20; i++) {
            int x = getRandomOrdinate(random, workingLocation.getX(), 10);
            int y = getRandomOrdinate(random, workingLocation.getY(), 10);

            Point loc = new Point(x, y);
            Npc npc = new Npc(95);
            npc.setIndex(i);
            npc.setLocation(loc, true);
        }
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
