package app.game.gamefield.elements.mobiles;

import app.supportclasses.GameValues;

/**
 * Projectile
 */
public abstract class Projectile extends Mobile {
    private int range;
    private double traveled;

    public enum Projectiles {
        Tear;
    }

    public Projectile(GameValues gameValues, double x, double y) {
        super(gameValues, x, y);
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

    
}