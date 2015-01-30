package org.moparscape.elysium.entity;

import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.Region;

/**
 * Created by daniel on 18/01/2015.
 */
public interface Moveable extends Locatable {

    void setLocation(Point location);

    void setLocation(Point location, boolean teleported);

    void updateRegion(Region oldRegion, Region newRegion);
}
