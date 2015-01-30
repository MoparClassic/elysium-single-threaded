package org.moparscape.elysium.net.handler;

import org.moparscape.elysium.entity.Path;
import org.moparscape.elysium.entity.Player;
import org.moparscape.elysium.entity.component.Movement;
import org.moparscape.elysium.net.Session;
import org.moparscape.elysium.net.codec.decoder.message.WalkTargetMessage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class WalkTargetMessageHandler extends MessageHandler<WalkTargetMessage> {

    @Override
    public boolean handle(Session session, Player player, WalkTargetMessage message) {
//        Point loc = player.getLocation();
//        System.out.printf("WalkTargetMessage - Player pos: (%d, %d). Start pos: (%d, %d). Steps = %d\n",
//                loc.getX(), loc.getY(), message.getStartX(), message.getStartY(), message.getSteps());
//        for (int i = 0; i < message.getXOffsets().length; i++) {
//            System.out.printf("\tOffset: (%d, %d)\n",
//                    message.getXOffsets()[i], message.getYOffsets()[i]);
//        }
//        System.out.println();

        Movement movement = player.getMovement();
        Path path = new Path(message);
        movement.setPath(path);

        return true;
    }
}
