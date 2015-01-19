package org.moparscape.elysium;

import org.moparscape.elysium.def.PrayerDef;
import org.moparscape.elysium.entity.Heartbeat;
import org.moparscape.elysium.entity.Indexable;
import org.moparscape.elysium.util.PersistenceManager;
import org.moparscape.elysium.world.Point;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public class Derp {

    public static void main(String[] args) throws Exception {
        Point p1 = new Point(10, 10);
        Point p2 = new Point(14, 6);

        System.out.println(Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2)));
        System.out.println(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));

        PrayerDef[] prayers = (PrayerDef[]) PersistenceManager.load("defs/PrayerDef.xml.gz");
        prayers.toString();
        if (true) return;

//        final int TEST_ITERATIONS = 20;
//        ArrayList<Long> timeTaken = new ArrayList<>(TEST_ITERATIONS);
//
//        for (int j = 0; j < 50; j++) {
//            long start = time();
//            EntityList<TestIndexable> elist = new EntityList<>(5000000);
//
//            for (int i = 0; i < 5000000; i++) {
//                elist.add(new TestIndexable());
//            }
//
//            Object cur = null;
//            for (int i = 400000; i < 500000; i++) {
//                elist.remove(i);
//            }
//
//            for (int i = 0; i < 75000; i++) {
//                elist.add(new TestIndexable());
//            }
//
//            //for (int i = 0; i < 10; i++) elist.add(new TestIndexable());
//
//            for (TestIndexable ti : elist) {
//                cur = ti; //System.out.println(ti);
//            }
//            System.out.println(cur);
//            long end = time();
//            long elapsed = end - start;
//            timeTaken.add(elapsed);
//        }
//
//        System.out.println();
//        long average = 0;
//        for (Long l : timeTaken) {
//            average += l;
//        }
//        average /= TEST_ITERATIONS;
//        System.out.printf("Average = %d\n", average);

//        long start = time();
//        String s = null;
//        int progress = 0;
//        for (int i = 0; i < 1000; i++) {
//            UUID id = UUID.randomUUID();
//            System.out.println(id);
//            progress++;
//        }
//        //System.out.printf("%d %s\n", progress, s);
//        long end = time();
//        System.out.println(end - start);


//        EntityComparators.HeartbeatComparator a = new EntityComparators.HeartbeatComparator();
//        PriorityQueue<Heartbeat> pq = new PriorityQueue<>(new EntityComparators.HeartbeatComparator());
//
//        pq.add(new HeartbeatImpl(1));
//        pq.add(new HeartbeatImpl(1000));
//        pq.add(new HeartbeatImpl(20));
//        pq.add(new HeartbeatImpl(15));
//        pq.add(new HeartbeatImpl(10));
//        pq.add(new HeartbeatImpl(0));
//
//        while (pq.peek() != null) System.out.println(pq.poll().getScheduledPulseTime());
    }

    public static void printSomething(Something s) {
        System.out.printf("%s %d\n", s.name, s.index);
    }

    private static long time() {
        //return System.nanoTime() / 1000000;
        return System.currentTimeMillis();
    }

    private static class HeartbeatImpl implements Heartbeat {

        private long pulseTime;

        public HeartbeatImpl(long time) {
            this.pulseTime = time;
        }

        @Override
        public long getScheduledPulseTime() {
            return pulseTime;
        }

        @Override
        public void setScheduledPulseTime(long time) {
            this.pulseTime = time;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public void pulse() {

        }
    }

    private static class Something implements Comparable<Something> {
        private int index;
        private String name;

        public Something(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public int compareTo(Something other) {
            return Integer.compare(this.index, other.index);
        }

        @Override
        public int hashCode() {
            return index;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Something)) return false;

            return ((Something) o).index == this.index;
        }
    }

    private static class TestIndexable implements Indexable {

        private int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String toString() {
            return String.valueOf(index);
        }
    }
}
