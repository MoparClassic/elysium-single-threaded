package org.moparscape.elysium.task.timed;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public abstract class AbstractTimedTask implements TimedTask {

    private final long recurringTime;
    private long execTime = 0L;

    public AbstractTimedTask(long firstRunTime, long recurringTime) {
        this.execTime = firstRunTime;
        this.recurringTime = recurringTime;
    }

    /**
     * Compares this task to the specified task.
     * This compareTo function works such that tasks which should be
     * executed earlier will be the first tasks to be polled from the
     * priority queue of tasks.
     *
     * @param other The TimedTask to compare this task to
     * @return
     */
    public final int compareTo(TimedTask other) {
        return Long.compare(this.getExecutionTime(), other.getExecutionTime());
    }

    public long getExecutionTime() {
        return execTime;
    }

    /**
     * Sets the next time that this task should be executed.
     * <p>
     * This implementation sets the task to re-execute immediately
     * at the next chance.
     *
     * @param lastExecuted The current time (possibly estimate) that the
     *                     task was last executed
     */
    public final void setNextRunningTime(long lastExecuted) {
        execTime = lastExecuted + recurringTime;
    }

    /**
     * Returns true if this task should be repeated in the future.
     * <p>
     * This implementation returns false by default. If a task should
     * be executed repeatedly then it is up to the implementor of the
     * task in question to override this method such that it returns
     * true when appropriate.
     *
     * @return True if this task should be repeated
     */
    public boolean shouldRepeat() {
        return false;
    }
}
