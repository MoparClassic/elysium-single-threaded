package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class DuelOptionsMessage extends AbstractMessage {

    private final boolean noMagic;
    private final boolean noPrayer;
    private final boolean noRetreating;
    private final boolean noWeapons;

    public DuelOptionsMessage(boolean noRetreating, boolean noMagic,
                              boolean noPrayer, boolean noWeapons) {
        this.noRetreating = noRetreating;
        this.noMagic = noMagic;
        this.noPrayer = noPrayer;
        this.noWeapons = noWeapons;
    }

    public boolean areWeaponsDisabled() {
        return noWeapons;
    }

    public boolean isMagicDisabled() {
        return noMagic;
    }

    public boolean isPrayerDisabled() {
        return noPrayer;
    }

    public boolean isRetreatingDisabled() {
        return noRetreating;
    }
}
