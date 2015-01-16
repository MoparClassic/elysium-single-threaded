package org.moparscape.elysium.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.net.codec.Message;
import org.moparscape.elysium.net.handler.HandlerLookupService;
import org.moparscape.elysium.net.handler.MessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class Session {

    private final Channel channel;
    private boolean allowedToDisconnect = true;
    private List<Message> messages = new ArrayList<>();
    private Player player;
    private boolean removing = false;

    public Session(Channel channel) {
        this.channel = channel;
    }

    public ChannelFuture close() {
        return channel.close();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    public boolean isAllowedToDisconnect() {
        return allowedToDisconnect;
    }

    public void setAllowedToDisconnect(boolean allowed) {
        this.allowedToDisconnect = allowed;
    }

    public boolean isRemoving() {
        return removing;
    }

    public void setRemoving(boolean removing) {
        this.removing = removing;
    }

    public <T extends Message> void messageReceived(T message) {
        System.out.println(message);
        synchronized (messages) {
            messages.add(message);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean pulse() {
        if (isRemoving()) {
            return false;
        }

        List<Message> messagesToProcess = null;

        synchronized (messages) {
            messagesToProcess = messages;

            List<Message> replacementMessages = new ArrayList<>(messages.size());
            messages = replacementMessages;
        }

        for (Message message : messagesToProcess) {
            MessageHandler<Message> handler = (MessageHandler<Message>) HandlerLookupService.getHandler(message.getClass());
            if (handler != null) {
                try {
                    handler.handle(this, getPlayer(), message);

                    if (!handler.shouldContinuePacketProcessing()) return true;
                } catch (Exception e) {
                    System.out.printf("Player Index: %d - Failure during handling of %s\n",
                            getPlayer() == null ? -1 : getPlayer().getIndex(),
                            message.getClass().toString());
                    e.printStackTrace();
                }
            } else {
                // TODO: Log non-existent handler.
            }
        }

        return true;
    }

    public ChannelFuture write(Object o) {
        return channel.writeAndFlush(o);
    }
}
