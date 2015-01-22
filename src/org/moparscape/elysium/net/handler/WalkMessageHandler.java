package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Path;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.PlayerState;
import org.moparscape.elysium.entity.component.Movement;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.WalkMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class WalkMessageHandler extends MessageHandler<WalkMessage> {

    @Override
    public boolean handle(Session session, Player player, WalkMessage message) {
//        Point loc = player.getLocation();
//        System.out.printf("WalkMessage - Player pos: (%d, %d). Start pos: (%d, %d). Steps = %d\n",
//                loc.getX(), loc.getY(), message.getStartX(), message.getStartY(), message.getSteps());
//        for (int i = 0; i < message.getXOffsets().length; i++) {
//            System.out.printf("\tOffset: (%d, %d)\n",
//                    message.getXOffsets()[i], message.getYOffsets()[i]);
//        }
//        System.out.println();

        Movement movement = player.getMovement();
        Path path = new Path(message);
        movement.setPath(path);

        player.setState(PlayerState.WALKING);

        return true;
    }
}
