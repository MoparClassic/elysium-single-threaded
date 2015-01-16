package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.ChatMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Communication extends AbstractComponent {

    public static final int MAX_FRIENDS = 100;

    public static final int MAX_IGNORES = 50;
    /**
     * This should be fine as a standard linked list as the only time it is
     * modified is when a player explicitly adds another player to the list.
     * This means that only one thread (the thread handling the incoming
     * packet from the player) will modify this list at a time.
     */
    private final List<Long> friends = new ArrayList<>();
    /**
     * This should be fine as a standard linked list as the only time it is
     * modified is when a player explicitly adds another player to the list.
     * This means that only one thread (the thread handling the incoming
     * packet from the player) will modify this list at a time.
     */
    private final List<Long> ignores = new ArrayList<>();
    private final Queue<ChatMessage> messages = new LinkedList<>();
    private final List<ChatMessage> messagesToDisplay = new ArrayList<>();
    private final List<ChatMessage> npcMessagesToDisplay = new ArrayList<>();

    public void addChatMessage(ChatMessage message) {
        messages.add(message);
    }

    public boolean addFriend(long usernameHash) {
        if (!friends.contains(usernameHash) && friends.size() < MAX_FRIENDS) {
            return friends.add(usernameHash);
        }

        return false;
    }

    public boolean addIgnore(long usernameHash) {
        if (!ignores.contains(usernameHash) && ignores.size() < MAX_IGNORES) {
            return ignores.add(usernameHash);
        }

        return false;
    }

    public void clearChatMessagesNeedingDisplayed() {
        messagesToDisplay.clear();
    }

    public void clearNpcMessagesNeedingDisplayed() {
        npcMessagesToDisplay.clear();
    }

    public List<ChatMessage> getChatMessagesNeedingDisplayed() {
        return messagesToDisplay;
    }

    public List<Long> getFriendList() {
        return friends;
    }

    public List<Long> getIgnoreList() {
        return ignores;
    }

    public ChatMessage getNextChatMessage() {
        return messages.poll();
    }

    public List<ChatMessage> getNpcMessagesNeedingDisplayed() {
        return npcMessagesToDisplay;
    }

    public void informOfChatMessage(ChatMessage message) {
        messagesToDisplay.add(message); // Use offer, it doesn't block
    }

    public void informOfNpcChatMessage(ChatMessage message) {
        npcMessagesToDisplay.add(message);
    }

    public boolean isFriendsWith(long usernameHash) {
        return friends.contains(usernameHash);
    }

    public boolean isIgnoring(long usernameHash) {
        return ignores.contains(usernameHash);
    }

    public void removeFriend(long usernameHash) {
        friends.remove(usernameHash);
    }

    public void removeIgnore(long usernameHash) {
        ignores.remove(usernameHash);
    }
}
