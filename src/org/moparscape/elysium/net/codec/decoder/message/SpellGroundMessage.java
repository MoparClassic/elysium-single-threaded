package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class SpellGroundMessage extends AbstractMessage {

    private final int actionVariable;
    private final int actionX;
    private final int actionY;

    public SpellGroundMessage(int actionVariable, int actionX, int actionY) {
        this.actionVariable = actionVariable;
        this.actionX = actionX;
        this.actionY = actionY;
    }

    public int getActionVariable() {
        return actionVariable;
    }

    public int getActionX() {
        return actionX;
    }

    public int getActionY() {
        return actionY;
    }
}
