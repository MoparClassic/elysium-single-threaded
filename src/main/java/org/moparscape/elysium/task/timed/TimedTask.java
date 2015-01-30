package org.moparscape.elysium.task.timed;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public interface TimedTask extends Runnable, Comparable<TimedTask> {

    long getExecutionTime();

    void setExecutionTime(long executionTime);

    void setDelay(long delay);

    boolean shouldRepeat();

    void updateExecutionTime();
}
