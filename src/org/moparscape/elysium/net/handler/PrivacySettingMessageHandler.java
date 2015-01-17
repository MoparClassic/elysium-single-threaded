package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Settings;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.PrivacySettingMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class PrivacySettingMessageHandler extends MessageHandler<PrivacySettingMessage> {
    @Override
    public boolean handle(Session session, Player player, PrivacySettingMessage message) {
        Settings settings = player.getSettings();

        settings.updateCommunicationSettings(
                message.getBlockChatMessages(),
                message.getBlockPrivateMessages(),
                message.getBlockTradeRequests(),
                message.getBlockDuelRequests());

        return true;
    }
}
