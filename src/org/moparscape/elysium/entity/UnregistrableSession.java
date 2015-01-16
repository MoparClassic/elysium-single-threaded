package org.moparscape.elysium.entity;

import org.moparscape.elysium.Server;
import org.moparscape.elysium.net.Session;

/**
 * Created by daniel on 16/01/2015.
 */
public final class UnregistrableSession implements Heartbeat {

    private static final Server server = Server.getInstance();
    private final Session session;
    private final long unregisterTime;

    public UnregistrableSession(Session session, boolean applyPenalty) {
        if (session == null) throw new IllegalArgumentException("session");

        this.session = session;

        if (applyPenalty) {
            this.unregisterTime = server.getHighResolutionTimestamp() + 30000;
        } else {
            this.unregisterTime = server.getHighResolutionTimestamp();
        }
    }

    @Override
    public long getScheduledPulseTime() {
        return unregisterTime;
    }

    @Override
    public void setScheduledPulseTime(long time) {
        // Do nothing - this task will not be rescheduled.
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void pulse() {
        // Do nothing - this is handled in the Server class.
    }

    public Session getSession() {
        return session;
    }
}
