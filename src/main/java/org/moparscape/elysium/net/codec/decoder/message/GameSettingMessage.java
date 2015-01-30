package org.moparscape.elysium.net.codec.decoder.message;

import org.moparscape.elysium.net.codec.AbstractMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class GameSettingMessage extends AbstractMessage {

    private final boolean flag;
    private final int settingIndex;

    public GameSettingMessage(int settingIndex, boolean flag) {
        this.settingIndex = settingIndex;
        this.flag = flag;
    }

    public boolean getFlag() {
        return flag;
    }

    public int getSettingIndex() {
        return settingIndex;
    }
}
