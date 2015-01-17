package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class MenuActionMessage extends AbstractMessage {

    private int option;

    public MenuActionMessage(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }
}
