package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class ObjectSecondaryActionMessage extends AbstractMessage {

    private final int actionX;
    private final int actionY;

    public ObjectSecondaryActionMessage(int actionX, int actionY) {
        this.actionX = actionX;
        this.actionY = actionY;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }
}
