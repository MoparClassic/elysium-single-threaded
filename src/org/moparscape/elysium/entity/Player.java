package org.moparscape.elysium.entity;

import org.moparscape.elysium.entity.component.*;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.Region;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Player extends MobileEntity implements Moveable {

    private final Bank bank = new Bank(this);
    private final Combat combat = new Combat();
    private final Communication communication = new Communication();
    private final Credentials credentials = new Credentials();
    private final Inventory inventory = new Inventory(this);
    private final Session session;
    private final Settings settings = new Settings();
    private final Skills skills = new Skills();
    private final PlayerSprite sprite = new PlayerSprite(this);
    private final Movement movement = new Movement(this, sprite);
    private final Observer observer = new Observer(this, sprite);
    private final UpdateProxy updateProxy = new UpdateProxy(communication, credentials, movement, observer, skills, sprite);
    private int actionCount = 0;
    private boolean loggedIn = false;
    private Region region = null;

    public Player(Session session) {
        this.session = session;
        this.setLocation(new Point(329, 552));
    }

    public int getActionCount() {
        return actionCount;
    }

    public Bank getBank() {
        return bank;
    }

    public Combat getCombat() {
        return combat;
    }

    public Communication getCommunication() {
        return communication;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Point getLocation() {
        return movement.getLocation();
    }

    @Override
    public void setLocation(Point location) {
        movement.setLocation(location);
    }

    @Override
    public void setLocation(Point location, boolean teleported) {
        movement.setLocation(location, teleported);
    }

    @Override
    public void updateRegion(Region oldRegion, Region newRegion) {
        if (oldRegion != newRegion) {
            if (oldRegion != null) {
                oldRegion.removePlayer(this);
            }

            newRegion.addPlayer(this);
        }
    }

    public Movement getMovement() {
        return movement;
    }

    public Observer getObserver() {
        return observer;
    }

    public Session getSession() {
        return session;
    }

    public Settings getSettings() {
        return settings;
    }

    public Skills getSkills() {
        return skills;
    }

    public PlayerSprite getSprite() {
        return sprite;
    }

    public UpdateProxy getUpdateProxy() {
        return updateProxy;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    public int incrementActionCount() {
        return ++actionCount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return credentials + " (" + getLocation().getX() + ", " + getLocation().getY() + ")";
    }
}
