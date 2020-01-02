package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
/**
 * Projectile
 */
public abstract class Projectile extends Mobile {
    private int range;
    private double traveled;

    public enum Projectiles {
        Tear;
    }

    public Projectile(GameValues gameValues, Point2D.Double location) {
        super(gameValues, location);
        this.traveled = 0;
    }

    //TODO ability to check when near end of range (full a bullet to start dropping)
    public boolean endOfRange() {
        return traveled >= range;
    }

    public void incrementTraveled(double distance) {
        traveled+=distance;
    }


    //TODO make sure the range number matches up to a block distance (units is blocks)
    public void setProjectileStats(Projectiles p) {
        switch(p) {
            case Tear:
                this.maxSpeed = 5;
                this.range = 5;
                this.image = null;//TODO Add this image
                break;
            default:
                this.maxSpeed = this.range = 0;
                break;
        }
    }

    @Override
    public void tick(Room room) {
        //Maybe for future types, acclerate
        updateVelocity();
        testCollisionAndMove(room);
        incrementTraveled(getTickDistance());
        if (endOfRange()) {
            room.destroyElement(this);
        }
    }

    private double getTickDistance() {
        return Math.sqrt(Math.pow(velocityPercent.getX()*maxSpeed, 2) + Math.pow(velocityPercent.getY()*maxSpeed, 2));
    }

    @Override
    protected void onCollision(Point2D.Double newLocation, Drawable collidingElement, Room room) {
        //TODO write block
    }

    
}