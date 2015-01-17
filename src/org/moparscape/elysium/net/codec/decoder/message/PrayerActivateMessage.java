package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PrayerActivateMessage extends AbstractMessage {

    private final int prayerIndex;

    public PrayerActivateMessage(int prayerIndex) {
        this.prayerIndex = prayerIndex;
    }

    public int getPrayerIndex() {
        return prayerIndex;
    }
}
