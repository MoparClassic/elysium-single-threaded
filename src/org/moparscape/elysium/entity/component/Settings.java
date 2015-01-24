package org.moparscape.elysium.entity.component;

/**
 * Created by daniel on 17/01/2015.
 */
public final class Settings {

    public static final int AUTO_SCREENSHOTS_INDEX = 5;
    public static final int BLOCK_CHAT_MESSAGES_INDEX = 0;
    public static final int BLOCK_DUEL_REQUESTS_INDEX = 3;
    public static final int BLOCK_PRIVATE_MESSAGES_INDEX = 1;
    public static final int BLOCK_TRADE_REQUESTS_INDEX = 2;
    //    public static final int
    public static final int CAMERA_ANGLE_MODE_INDEX = 0;
    public static final int FIGHTMODE_SELECTOR_INDEX = 6;
    public static final int HIDE_ROOFS_INDEX = 4;
    public static final int MOUSE_BUTTONS_INDEX = 2;
    public static final int SOUND_EFFECTS_INDEX = 3;
    private boolean[] privacySettings = new boolean[4];
    private boolean[] gameSettings = new boolean[7];

    public Settings() {

    }

    public boolean getPrivacySetting(int index) {
        return privacySettings[index];
    }

    public boolean[] getPrivacySettings() {
        return privacySettings;
    }

    public boolean getGameSetting(int index) {
        return gameSettings[index];
    }

    public boolean[] getGameSettings() {
        return gameSettings;
    }

    public void updateCommunicationSetting(int index, boolean flag) {
        privacySettings[index] = flag;
    }

    public void updateCommunicationSettings(boolean blockChatMessages, boolean blockPrivateMessages,
                                            boolean blockTradeRequests, boolean blockDuelRequests) {
        privacySettings[BLOCK_CHAT_MESSAGES_INDEX] = blockChatMessages;
        privacySettings[BLOCK_PRIVATE_MESSAGES_INDEX] = blockPrivateMessages;
        privacySettings[BLOCK_TRADE_REQUESTS_INDEX] = blockTradeRequests;
        privacySettings[BLOCK_DUEL_REQUESTS_INDEX] = blockDuelRequests;
    }

    public void updateGameSetting(int index, boolean flag) {
        gameSettings[index] = flag;
    }
}
