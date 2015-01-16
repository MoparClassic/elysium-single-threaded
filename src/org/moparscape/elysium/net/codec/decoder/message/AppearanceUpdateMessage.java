package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.entity.Appearance;
import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class AppearanceUpdateMessage extends AbstractMessage {

    private final Appearance appearance;

    public AppearanceUpdateMessage(Appearance appearance) {
        this.appearance = appearance;
    }

    public Appearance getAppearance() {
        return appearance;
    }
}
