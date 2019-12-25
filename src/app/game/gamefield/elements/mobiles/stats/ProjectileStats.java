package app.game.gamefield.elements.mobiles.stats;

import app.supportclasses.GameValues;
/**
 * ProjectileStats
 */
public class ProjectileStats extends MobileStats {
    private int range;
    private double traveled;

    public enum Projectile {
        Tear;
    }

    //TODO ability to check when near end of range
    public boolean endOfRange() {
        return traveled >= range;
    }

    public void incrementTraveled(double distance) {
        traveled+=distance;
    }


    //TODO make sure the range number matches up to a block distance (units is blocks)
    public ProjectileStats(GameValues gameValues, Projectile p) {
        super(gameValues);
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
        this.traveled = 0;
    }

    
}