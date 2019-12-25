package app.game.gamefield.elements.mobiles.stats;

import app.supportclasses.GameValues;
/**
 * EnemyStats
 */
public class EnemyStats extends LivingStats {
    public enum Enemy {
        Fly;
    }

    public EnemyStats(GameValues gameValues, Enemy e) {
        super(gameValues);
        switch(e) {
            case Fly:
                this.maxSpeed = 1;
                this.maxHealth = 2;
                this.image = null;//TODO Add this image
                break;
            default:
                break;
        }

        setFullHealth();
    }
}