package org.moparscape.elysium.entity.component;

import org.moparscape.elysium.entity.ChatMessage;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Communication extends AbstractComponent {

    public static final int MAX_FRIENDS = 100;

    public static final int MAX_IGNORES = 50;
    private final Set<Long> friends = new HashSet<>();
    private final Set<Long> ignores = new HashSet<>();
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

    public Set<Long> getFriendList() {
        return friends;
    }

    public Set<Long> getIgnoreList() {
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
