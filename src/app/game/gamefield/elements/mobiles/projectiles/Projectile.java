package app.game.gamefield.elements.mobiles.projectiles;

import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;
/**
 * Projectile
 */
public class Projectile extends Mobile {
    private int range;
    private double traveled;

    public enum Projectiles {
        Tear;
    }

    public Projectile(GameValues gameValues, Projectiles p, Point2D.Double location) {
        super(gameValues, location);
        setProjectileStats(p);
        setFullHealth();
        this.traveled = 0;
    }

    //TODO make sure the range number matches up to a block distance (units is blocks)
    public void setProjectileStats(Projectiles p) {
        SpriteSheet spriteSheet = new SpriteSheet(gameValues.DEGRADABLES_SPRITE_SHEET);

        switch(p) {
            case Tear:
                this.sizeInBlocks = new Point2D.Double(.5, .5);
                this.maxSpeed = 5;
                this.range = 5;
                this.maxHealth = 1;
                this.image = spriteSheet.shrink(spriteSheet.grabImage(14, 2, 2, 2, gameValues.DEGRADABLES_SPRITE_SHEET_BOX_SIZE));
                break;
            default:
                this.maxSpeed = this.range = 0;
                break;
        }
    }

    //TODO ability to check when near end of range (full a bullet to start dropping)
    public boolean endOfRange() {
        return traveled >= range;
    }

    public void incrementTraveled(double distance) {
        traveled+=distance;
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
    protected Point2D.Double onCollision(Point2D.Double newLocation, Drawable collidingElement, Room room) {
        //TODO write block
        return location;
    }

    
}