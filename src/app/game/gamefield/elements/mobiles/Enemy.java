package app.game.gamefield.elements.mobiles;

import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
/**
 * Enemy
 */
public abstract class Enemy extends Living {

    public enum Enemies {
        Fly;
    }

    public Enemy(GameValues gameValues, Point2D.Double location) {
        super(gameValues, location);
        
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