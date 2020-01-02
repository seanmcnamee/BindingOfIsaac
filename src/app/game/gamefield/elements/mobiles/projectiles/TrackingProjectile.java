package app.game.gamefield.elements.mobiles.projectiles;

import java.awt.geom.Point2D;

import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;

/**
 * TrackingProjectile
 */
public class TrackingProjectile extends Projectile {

    public TrackingProjectile(GameValues gameValues, Projectiles p, Point2D.Double location) {
        super(gameValues, p, location);
    }

    @Override
    public void tick(Room room) {
        //Figure out which way to accelerate to get towards the player and
        //accelerate(xAcc, yAcc);
        super.tick(room);
    }
}