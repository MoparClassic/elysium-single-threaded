package org.moparscape.elysium.world;

import org.moparscape.elysium.def.NPCLoc;
import org.moparscape.elysium.entity.*;
import org.moparscape.elysium.io.WorldLoader;
import org.moparscape.elysium.util.EntityList;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
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
    private final TileValue outsideWorld = new TileValue();
    private final EntityList<Player> playerList = new EntityList<>(MAX_PLAYERS);
    private final List<Shop> shops = new ArrayList<>();
    static {
        INSTANCE = new World();
        try {
            WorldLoader loader = new WorldLoader();
            loader.loadWorld(INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("WorldLoader failed.");
        }
    }
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

    /**
     * Updates the map to include a new door
     */
    public void registerDoor(GameObject o) {
        if (o.getDoorDef().getDoorType() != 1) {
            return;
        }
        int dir = o.getDirection();
        Point location = o.getLocation();
        int x = location.getX();
        int y = location.getY();
        if (dir == 0) {
            getTileValue(x, y).objectValue |= 1;
            getTileValue(x, y - 1).objectValue |= 4;
        } else if (dir == 1) {
            getTileValue(x, y).objectValue |= 2;
            getTileValue(x - 1, y).objectValue |= 8;
        } else if (dir == 2) {
            getTileValue(x, y).objectValue |= 0x10;
        } else if (dir == 3) {
            getTileValue(x, y).objectValue |= 0x20;
        }
    }

    public void registerGameObject(GameObject o) {
        Point location = o.getLocation();
        if (location != null) {
            Region r = Region.getRegion(location);
            r.addObject(o);
        }

        switch (o.getType()) {
            case 0:
                registerObject(o);
                break;
            case 1:
                registerDoor(o);
                break;
        }
    }

    public boolean registerItem(Item item) {
        Point location = item.getLocation();
        if (location != null) {
            Region r = Region.getRegion(location);
            r.addItem(item);
        }
        return true;
    }

    public boolean registerNpc(Npc n) {
        NPCLoc npc = n.getLoc();
        if (npc.startX < npc.minX || npc.startX > npc.maxX || npc.startY < npc.minY || npc.startY > npc.maxY || (getTileValue(npc.startX, npc.startY).mapValue & 64) != 0) {
            System.out.println("Fucked Npc: <id>" + npc.id + "</id><startX>" + npc.startX + "</startX><startY>" + npc.startY + "</startY>");
        }
        return npcList.add(n);
    }

    /**
     * Updates the map to include a new object
     */
    public void registerObject(GameObject o) {
        if (o.getGameObjectDef().getType() != 1 && o.getGameObjectDef().getType() != 2) {
            return;
        }
        int dir = o.getDirection();
        Point location = o.getLocation();
        int baseX = location.getX();
        int baseY = location.getY();
        int width, height;
        if (dir == 0 || dir == 4) {
            width = o.getGameObjectDef().getWidth();
            height = o.getGameObjectDef().getHeight();
        } else {
            height = o.getGameObjectDef().getWidth();
            width = o.getGameObjectDef().getHeight();
        }
        for (int x = baseX; x < baseX + width; x++) {
            for (int y = baseY; y < baseY + height; y++) {
                if (o.getGameObjectDef().getType() == 1) {
                    getTileValue(x, y).objectValue |= 0x40;
                } else if (dir == 0) {
                    getTileValue(x, y).objectValue |= 2;
                    getTileValue(x - 1, y).objectValue |= 8;
                } else if (dir == 2) {
                    getTileValue(x, y).objectValue |= 4;
                    getTileValue(x, y + 1).objectValue |= 1;
                } else if (dir == 4) {
                    getTileValue(x, y).objectValue |= 8;
                    getTileValue(x + 1, y).objectValue |= 2;
                } else if (dir == 6) {
                    getTileValue(x, y).objectValue |= 1;
                    getTileValue(x, y - 1).objectValue |= 4;
                }
            }
        }
    }

    public boolean registerPlayer(Player p) {
        return playerList.add(p);
    }

    public void registerShop(final Shop shop) {
        shop.setEquilibrium();
        shops.add(shop);
    }

    public void seedWithEntities() {
        Point workingLocation = new Point(329, 552);
        SecureRandom random = new SecureRandom();
        random.setSeed(13333333333337L);

        for (int i = 0; i < 500; i++) {
            int x = getRandomOrdinate(random, workingLocation.getX(), 10);
            int y = getRandomOrdinate(random, workingLocation.getY(), 10);

            Point loc = new Point(x, y);
            Item item = new Item(i, 1, loc, null);
            Region.getRegion(loc).addItem(item);
        }
//
//        for (int i = 1; i < 20; i++) {
//            int x = getRandomOrdinate(random, workingLocation.getX(), 10);
//            int y = getRandomOrdinate(random, workingLocation.getY(), 10);
//
//            Point loc = new Point(x, y);
//            Npc npc = new Npc(95);
//            npc.setIndex(i);
//            npc.setLocation(loc, true);
//        }
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
