package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PrayerDeactivateMessage extends AbstractMessage {

    private final int prayerIndex;

    public PrayerDeactivateMessage(int prayerIndex) {
        this.prayerIndex = prayerIndex;
    }

    public int getPrayerIndex() {
        return prayerIndex;
    }
}
