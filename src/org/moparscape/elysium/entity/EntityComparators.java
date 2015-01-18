package org.moparscape.elysium.entity;

import org.moparscape.elysium.world.Point;

import java.util.Comparator;

/**
 * Created by daniel on 15/01/2015.
 */
public final class EntityComparators {

    public static class DistanceComparator implements Comparator<Locatable> {

        private final Point origin;

        public DistanceComparator(Point origin) {
            this.origin = origin;
        }

        public int compare(Locatable a, Locatable b) {
            Point aLoc = a.getLocation();
            Point bLoc = b.getLocation();

            int distA = ((int) Math.pow((aLoc.getX() - origin.getX()), 2)) + ((int) Math.pow((aLoc.getY() - origin.getY()), 2));
            int distB = ((int) Math.pow((bLoc.getX() - origin.getX()), 2)) + ((int) Math.pow((bLoc.getY() - origin.getY()), 2));

            return distA - distB;
        }
    }

    public static class HeartbeatComparator implements Comparator<Heartbeat> {

        public int compare(Heartbeat a, Heartbeat b) {
            return Long.compare(a.getScheduledPulseTime(), b.getScheduledPulseTime());
        }
    }
}
