package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.*;
import org.moparscape.elysium.util.StatefulEntityCollection;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class UpdateProxy {

    private Movement movement;
    private Observer observer;

    public UpdateProxy(Movement movement, Observer observer) {
        this.movement = movement;
        this.observer = observer;
    }

    public void cleanupViewableEntities() {
        observer.cleanupViewableEntities();
    }

    public void clearDisplayLists() {
        observer.clearDisplayLists();
    }

    public List<Bubble> getBubblesNeedingDisplayed() {
        return observer.getBubblesNeedingDisplayed();
    }

    public List<Npc> getNpcHitUpdates() {
        return observer.getNpcHitUpdates();
    }

    public List<Player> getPlayerAppearanceUpdates() {
        return observer.getPlayerAppearanceUpdates();
    }

    public List<Player> getPlayerHitUpdates() {
        return observer.getPlayerHitUpdates();
    }

    public List<Projectile> getProjectilesNeedingDisplayed() {
        return observer.getProjectilesNeedingDisplayed();
    }

    public StatefulEntityCollection<Item> getWatchedItems() {
        return observer.getWatchedItems();
    }

    public StatefulEntityCollection<Npc> getWatchedNpcs() {
        return observer.getWatchedNpcs();
    }

    public StatefulEntityCollection<GameObject> getWatchedObjects() {
        return observer.getWatchedObjects();
    }

    public StatefulEntityCollection<Player> getWatchedPlayers() {
        return observer.getWatchedPlayers();
    }

    public boolean hasMoved() {
        return movement.hasMoved();
    }

    public void resetMoved() {
        movement.resetMoved();
    }

    public void revalidateWatchedEntities() {
        observer.revalidateWatchedEntities();
    }

    public void updateEntityLists() {
        observer.updateEntityLists();
    }

    public void updatePosition() {
        movement.updatePosition();
    }

    public void updateViewableEntities() {
        observer.updateViewableEntities();
    }

    public void updateWatchedEntities() {
        observer.updateWatchedEntities();
    }
}
