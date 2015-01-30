package org.moparscape.elysium.entity;

import org.moparscape.elysium.def.DoorDef;
import org.moparscape.elysium.def.GameObjectDef;
import org.moparscape.elysium.def.GameObjectLoc;
import org.moparscape.elysium.world.Point;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class GameObject implements Locatable {

    private final GameObjectLoc loc;
    private int direction;
    private int id;
    private Point location;
    private int type;

    public GameObject(GameObjectLoc loc) {
        this.loc = loc;
        this.location = new Point(loc.getX(), loc.getY());
        this.direction = loc.getDirection();
        this.type = loc.getType();
        this.id = loc.getId();
    }

    public int getDirection() {
        return direction;
    }

    public DoorDef getDoorDef() {
        return DefinitionHandler.getDoorDef(id);
    }

    public GameObjectDef getGameObjectDef() {
        return DefinitionHandler.getGameObjectDef(id);
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    public boolean isRemoved() {
        return false;
    }
}
