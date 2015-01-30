package org.moparscape.elysium.world;

import org.moparscape.elysium.entity.GameObject;
import org.moparscape.elysium.entity.Item;
import org.moparscape.elysium.entity.Npc;
import org.moparscape.elysium.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Region {

    private static final int REGION_SIZE = 64;

    private static final int LOWER_BOUND = (REGION_SIZE / 2) - 1;

    private static final int HORIZONTAL_REGIONS = (World.MAX_WIDTH / REGION_SIZE) + 1;

    private static final int VERTICAL_REGIONS = (World.MAX_HEIGHT / REGION_SIZE) + 1;

    private static final Region[][] regions = new Region[HORIZONTAL_REGIONS][VERTICAL_REGIONS];
    private final List<Item> items = new ArrayList<>();
    private final List<Npc> npcs = new ArrayList<>();
    private final List<GameObject> objects = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    static {
        for (int x = 0; x < HORIZONTAL_REGIONS; x++) {
            for (int y = 0; y < VERTICAL_REGIONS; y++) {
                regions[x][y] = new Region();
            }
        }
    }

    public static Region getRegion(Point p) {
        return getRegion(p.getX(), p.getY());
    }

    private static Region getRegion(int x, int y) {
        int regionX = x / REGION_SIZE;
        int regionY = y / REGION_SIZE;

        return regions[regionX][regionY];
    }

    public static List<Item> getViewableItems(Point p, int radius) {
        List<Region> regions = getViewableRegions(p);

        int itemCount = 0;
        for (Region r : regions) itemCount += r.getItemCount();
        List<Item> items = new ArrayList<>(itemCount);

        for (Region r : regions) {
            for (Item i : r.getItems()) {
                if (!i.isRemoved() && p.withinRange(i.getLocation(), radius)) {
                    items.add(i);
                }
            }
        }

        return items;
    }

    public static List<Npc> getViewableNpcs(Point p, int radius) {
        List<Region> regions = getViewableRegions(p);

        int npcCount = 0;
        for (Region r : regions) npcCount += r.getNpcCount();
        List<Npc> npcs = new ArrayList<>(npcCount);

        for (Region r : regions) {
            for (Npc n : r.getNpcs()) {
                if (p.withinRange(n.getLocation(), radius)) {
                    npcs.add(n);
                }
            }
        }

        return npcs;
    }

    public static List<GameObject> getViewableObjects(Point p, int radius) {
        List<Region> regions = getViewableRegions(p);

        int objectCount = 0;
        for (Region r : regions) objectCount += r.getObjectCount();
        List<GameObject> objects = new ArrayList<>(objectCount);

        for (Region r : regions) {
            for (GameObject go : r.getObjects()) {
                if (!go.isRemoved() && p.withinRange(go.getLocation(), radius)) {
                    objects.add(go);
                }
            }
        }

        return objects;
    }

    public static List<Player> getViewablePlayers(Player player, int radius) {
        Point loc = player.getLocation();
        List<Region> regions = getViewableRegions(loc);

        int playerCount = 0;
        for (Region r : regions) playerCount += r.getPlayerCount();
        List<Player> players = new ArrayList<>(playerCount);

        for (Region r : regions) {
            for (Player p : r.getPlayers()) {
                if (p != player && p.isLoggedIn() && loc.withinRange(p.getLocation(), radius)) {
                    players.add(p);
                }
            }
        }

        return players;
    }

    private static List<Region> getViewableRegions(int x, int y) {
        List<Region> result = new ArrayList<>(4);
        int regionX = x / REGION_SIZE;
        int regionY = y / REGION_SIZE;
        result.add(regions[regionX][regionY]);

        int relX = x % REGION_SIZE;
        int relY = y % REGION_SIZE;

        if (relX <= LOWER_BOUND) {
            if (relY <= LOWER_BOUND) {
                result.add(regions[regionX - 1][regionY]);
                result.add(regions[regionX - 1][regionY - 1]);
                result.add(regions[regionX][regionY - 1]);
            } else {
                result.add(regions[regionX - 1][regionY]);
                result.add(regions[regionX - 1][regionY + 1]);
                result.add(regions[regionX][regionY + 1]);
            }
        } else {
            if (relY <= LOWER_BOUND) {
                result.add(regions[regionX + 1][regionY]);
                result.add(regions[regionX + 1][regionY - 1]);
                result.add(regions[regionX][regionY - 1]);
            } else {
                result.add(regions[regionX + 1][regionY]);
                result.add(regions[regionX + 1][regionY + 1]);
                result.add(regions[regionX][regionY + 1]);
            }
        }

        return result;
    }

    public static List<Region> getViewableRegions(Point p) {
        return getViewableRegions(p.getX(), p.getY());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addNpc(Npc npc) {
        npcs.add(npc);
    }

    public void addObject(GameObject go) {
        objects.add(go);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Item getItem(int itemId, Point location) {
        for (Item i : items) {
            if (i.getId() == itemId && i.getLocation().equals(location)) {
                return i;
            }
        }

        return null;
    }

    public int getItemCount() {
        return items.size();
    }

    public Iterable<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }

    public Npc getNpc() {
        throw new UnsupportedOperationException();
    }

    public int getNpcCount() {
        return npcs.size();
    }

    public Iterable<Npc> getNpcs() {
        return Collections.unmodifiableCollection(npcs);
    }

    public GameObject getObject() {
        throw new UnsupportedOperationException();
    }

    public int getObjectCount() {
        return objects.size();
    }

    public Iterable<GameObject> getObjects() {
        return Collections.unmodifiableCollection(objects);
    }

    public Player getPlayer() {
        throw new UnsupportedOperationException();
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Iterable<Player> getPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeNpc(Npc npc) {
        npcs.remove(npc);
    }

    public void removeObject(GameObject go) {
        objects.remove(go);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(2000);
        sb.append("Players:\n");
        for (Player p : players) {
            sb.append("\t").append(p).append("\n");
        }

        sb.append("\nItems:\n");
        for (Item i : items) {
            sb.append("\t").append(i).append("\n");
        }

        return sb.toString();
    }
}
