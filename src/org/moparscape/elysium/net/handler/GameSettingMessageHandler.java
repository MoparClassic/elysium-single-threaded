package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Settings;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.GameSettingMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class GameSettingMessageHandler extends MessageHandler<GameSettingMessage> {
    @Override
    public boolean handle(Session session, Player player, GameSettingMessage message) {
        Settings settings = player.getSettings();
        settings.updateGameSetting(message.getSettingIndex(), message.getFlag());
        return true;
    }
}
