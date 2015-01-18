package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.*;
import org.moparscape.elysium.util.StatefulEntityCollection;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.Region;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Observer extends AbstractComponent {

    private static final int MAX_VISIBLE_ITEMS = 254;
    private static final int MAX_VISIBLE_NPCS = 100;
    private static final int MAX_VISIBLE_OBJECTS = 100;
    private static final int MAX_VISIBLE_PLAYERS = 100;
    private final List<Bubble> bubbles = new ArrayList<>();
    private final Map<Integer, Integer> knownPlayerAppearanceIds = new HashMap<>();
    private final Set<Item> nearestViewableItems = new HashSet<>(MAX_VISIBLE_ITEMS);
    private final Set<Npc> nearestViewableNpcs = new HashSet<>(MAX_VISIBLE_NPCS);
    private final Set<GameObject> nearestViewableObjects = new HashSet<>(MAX_VISIBLE_OBJECTS);
    private final Set<Player> nearestViewablePlayers = new HashSet<>(MAX_VISIBLE_PLAYERS);
    private final List<Npc> npcHitUpdates = new ArrayList<>();
    private final List<Player> playerHitUpdates = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final StatefulEntityCollection<Item> watchedItems = new StatefulEntityCollection<>();
    private final StatefulEntityCollection<Npc> watchedNpcs = new StatefulEntityCollection<>();
    private final StatefulEntityCollection<GameObject> watchedObjects = new StatefulEntityCollection<>();
    private final StatefulEntityCollection<Player> watchedPlayers = new StatefulEntityCollection<>();
    private List<Item> allViewableItems;
    private List<Npc> allViewableNpcs;
    private List<GameObject> allViewableObjects;
    private List<Player> allViewablePlayers;
    private Player owner;
    private PlayerSprite sprite;
    private boolean visibleItemLimitBreached = false;
    private boolean visibleNpcLimitBreached = false;
    private boolean visibleObjectLimitBreached = false;
    private boolean visiblePlayerLimitBreached = false;

    public Observer(Player owner, PlayerSprite sprite) {
        this.owner = owner;
        this.sprite = sprite;
    }

    public void addPlayerAppearanceIds(int[] indices, int[] appearanceIds) {
        for (int i = 0; i < indices.length; i++) {
            knownPlayerAppearanceIds.put(indices[i], appearanceIds[i]);
        }
    }

    public void cleanupViewableEntities() {
        allViewableItems.clear();
        allViewableNpcs.clear();
        allViewableObjects.clear();
        allViewablePlayers.clear();

        nearestViewableItems.clear();
        nearestViewableNpcs.clear();
        nearestViewableObjects.clear();
        nearestViewablePlayers.clear();

        visibleItemLimitBreached = false;
        visibleNpcLimitBreached = false;
        visibleObjectLimitBreached = false;
        visiblePlayerLimitBreached = false;
    }

    public void clearDisplayLists() {
        projectiles.clear();
        playerHitUpdates.clear();
        npcHitUpdates.clear();
        bubbles.clear();
    }

    public List<Bubble> getBubblesNeedingDisplayed() {
        return bubbles;
    }

    public Collection<Item> getCachedViewableItems() {
        return visibleItemLimitBreached ?
                Collections.unmodifiableCollection(nearestViewableItems) :
                Collections.unmodifiableCollection(allViewableItems);
    }

    public Collection<Npc> getCachedViewableNpcs() {
        return visibleNpcLimitBreached ?
                Collections.unmodifiableCollection(nearestViewableNpcs) :
                Collections.unmodifiableCollection(allViewableNpcs);
    }

    public Collection<GameObject> getCachedViewableObjects() {
        return visibleObjectLimitBreached ?
                Collections.unmodifiableCollection(nearestViewableObjects) :
                Collections.unmodifiableCollection(allViewableObjects);
    }

    public Collection<Player> getCachedViewablePlayers() {
        return visiblePlayerLimitBreached ?
                Collections.unmodifiableCollection(nearestViewablePlayers) :
                Collections.unmodifiableCollection(allViewablePlayers);
    }

    public List<Npc> getNpcHitUpdates() {
        return npcHitUpdates;
    }

    public List<Player> getPlayerAppearanceUpdates() {
        Set<Player> newEntities = watchedPlayers.getNewEntities();
        Set<Player> knownEntities = watchedPlayers.getKnownEntities();
        List<Player> needingUpdates = new ArrayList<>(newEntities.size() + knownEntities.size());

        needingUpdates.addAll(watchedPlayers.getNewEntities());
        if (sprite.appearanceChanged()) {
            needingUpdates.add(owner);
        }

        for (Player p : watchedPlayers.getKnownEntities()) {
            if (needsAppearanceUpdateFor(p)) {
                needingUpdates.add(p);
            }
        }

        return needingUpdates;
    }

    public List<Player> getPlayerHitUpdates() {
        return playerHitUpdates;
    }

    public List<Projectile> getProjectilesNeedingDisplayed() {
        return projectiles;
    }

    public StatefulEntityCollection<Item> getWatchedItems() {
        return watchedItems;
    }

    public StatefulEntityCollection<Npc> getWatchedNpcs() {
        return watchedNpcs;
    }

    public StatefulEntityCollection<GameObject> getWatchedObjects() {
        return watchedObjects;
    }

    public StatefulEntityCollection<Player> getWatchedPlayers() {
        return watchedPlayers;
    }

    private boolean needsAppearanceUpdateFor(Player target) {
        int targetIndex = target.getIndex();
        PlayerSprite targetSprite = target.getSprite();
        if (knownPlayerAppearanceIds.containsKey(targetIndex)) {
            int knownAppearanceId = knownPlayerAppearanceIds.get(targetIndex);
            if (knownAppearanceId != targetSprite.getAppearanceId()) {
                knownPlayerAppearanceIds.put(targetIndex, targetSprite.getAppearanceId());
                return true;
            }
        } else {
            knownPlayerAppearanceIds.put(targetIndex, targetSprite.getAppearanceId());
            return true;
        }

        return false;
    }

    @Override
    public void resolveDependencies(Map<Class<? extends Component>, Component> components) {
        this.sprite = PlayerSprite.class.cast(components.get(PlayerSprite.class));
    }

    public void revalidateWatchedEntities() {
        revalidateWatchedPlayers();
        revalidateWatchedObjects();
        revalidateWatchedItems();
        revalidateWatchedNpcs();
    }

    private void revalidateWatchedItems() {
        Point loc = owner.getLocation();

        if (!visibleItemLimitBreached) {
            for (Item i : watchedItems.getKnownEntities()) {
                if (!loc.withinRange(i.getLocation(), 16) || i.isRemoved() || !i.isVisibleTo(owner)) {
                    watchedItems.remove(i);
                }
            }
        } else {
            for (Item i : watchedItems.getKnownEntities()) {
                if (!nearestViewableItems.contains(i)) {
                    watchedItems.remove(i);
                }

                if (!loc.withinRange(i.getLocation(), 16) || i.isRemoved() || !i.isVisibleTo(owner)) {
                    watchedItems.remove(i);
                }
            }
        }
    }

    private void revalidateWatchedNpcs() {
        Point loc = owner.getLocation();
        for (Npc n : watchedNpcs.getKnownEntities()) {
            if (!loc.withinRange(n.getLocation(), 16) || n.isRemoved()) {
                watchedNpcs.remove(n);
            }
        }
    }

    private void revalidateWatchedObjects() {
        Point loc = owner.getLocation();
        for (GameObject o : watchedObjects.getKnownEntities()) {
            if (!loc.withinRange(o.getLocation(), 21) || o.isRemoved()) {
                watchedObjects.remove(o);
            }
        }
    }

    private void revalidateWatchedPlayers() {
        Point loc = owner.getLocation();
        for (Player p : watchedPlayers.getKnownEntities()) {
            if (!loc.withinRange(p.getLocation(), 16) || !p.isLoggedIn()) {
                watchedPlayers.remove(p);
                knownPlayerAppearanceIds.remove(p.getIndex());
            }
        }
    }

    public void setOwner(Player player) {
        if (owner != null) {
            throw new IllegalStateException("Observer's player is already set");
        }
        this.owner = player;
    }

    public void updateEntityLists() {
        watchedPlayers.update();
        watchedObjects.update();
        watchedItems.update();
        watchedNpcs.update();
    }

    public void updateViewableEntities() {
        Point loc = owner.getLocation();
        allViewableItems = Region.getViewableItems(loc, 16);
        allViewableNpcs = Region.getViewableNpcs(loc, 16);
        allViewableObjects = Region.getViewableObjects(loc, 21);
        allViewablePlayers = Region.getViewablePlayers(owner, 16);

        EntityComparators.DistanceComparator distCompare = null;
        if (allViewableItems.size() > MAX_VISIBLE_ITEMS) {
            if (distCompare == null) distCompare = new EntityComparators.DistanceComparator(loc);

            allViewableItems.sort(distCompare);
            visibleItemLimitBreached = true;

            int count = 0;
            for (Item i : allViewableItems) {
                if (count == MAX_VISIBLE_ITEMS) break;

                nearestViewableItems.add(i);
                count++;
            }
        }

        if (allViewableNpcs.size() > MAX_VISIBLE_NPCS) {
            if (distCompare == null) distCompare = new EntityComparators.DistanceComparator(loc);

            allViewableNpcs.sort(distCompare);
            visibleNpcLimitBreached = true;
        }

        if (allViewableObjects.size() > MAX_VISIBLE_OBJECTS) {
            if (distCompare == null) distCompare = new EntityComparators.DistanceComparator(loc);

            allViewableObjects.sort(distCompare);
            visibleObjectLimitBreached = true;
        }

        if (allViewablePlayers.size() > MAX_VISIBLE_PLAYERS) {
            if (distCompare == null) distCompare = new EntityComparators.DistanceComparator(loc);

            allViewablePlayers.sort(distCompare);
            visiblePlayerLimitBreached = true;
        }
    }

    public void updateWatchedEntities() {
        updateWatchedPlayers();
        updateWatchedObjects();
        updateWatchedItems();
        updateWatchedNpcs();
    }

    private void updateWatchedItems() {
        if (!visibleItemLimitBreached) {
            for (Item item : allViewableItems) {
                if (!watchedItems.contains(item) && item.isVisibleTo(owner)) {
                    watchedItems.add(item);
                }
            }
        } else {
            for (Item item : nearestViewableItems) {
                if (!watchedItems.contains(item) && item.isVisibleTo(owner)) {
                    watchedItems.add(item);
                }
            }
        }
    }

    private void updateWatchedNpcs() {
        Iterable<Npc> npcs = Region.getViewableNpcs(owner.getLocation(), 16);

        for (Npc npc : npcs) {
            if (!watchedNpcs.contains(npc) || watchedNpcs.isRemoving(npc)) {
                watchedNpcs.add(npc);
            }
        }
    }

    private void updateWatchedObjects() {
        Iterable<GameObject> objects = Region.getViewableObjects(owner.getLocation(), 21);

        for (GameObject go : objects) {
            if (!watchedObjects.contains(go)) {
                watchedObjects.add(go);
            }
        }
    }

    private void updateWatchedPlayers() {
        Iterable<Player> players = Region.getViewablePlayers(owner, 16);

        for (Player player : players) {
            if (!watchedPlayers.contains(player) || watchedPlayers.isRemoving(player)) {
                watchedPlayers.add(player);
            }
        }
    }
}
