package app.game.gamefield.elements.mobiles;

import app.supportclasses.GameValues;

/**
 * Enemy
 */
public abstract class Enemy extends Living {

    public enum Enemies {
        Fly;
    }

    public Enemy(GameValues gameValues, double x, double y) {
        super(gameValues, x, y);
        
    }

    protected void setEnemyStats(Enemies e) {
        switch(e) {
            case Fly:
                this.maxSpeed = 1;
                this.maxHealth = 2;
                this.image = null;//TODO Add this image
                break;
            default:
                break;
        }
        //TODO move this elsewhere if an enemy shouldn't spawn in with full health
        setFullHealth();
    }
}