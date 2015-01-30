package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class AttackStyleMessage extends AbstractMessage {

    private final int attackStyle;

    public AttackStyleMessage(int attackStyle) {
        this.attackStyle = attackStyle;
    }

    public int getAttackStyle() {
        return attackStyle;
    }
}
