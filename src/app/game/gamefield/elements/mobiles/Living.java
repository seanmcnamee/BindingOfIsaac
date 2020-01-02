package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.Destroyable;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;

/**
 * Living
 */
public abstract class Living extends Mobile implements Destroyable{
    protected int maxHealth, health; // A full heart is 2. A half heart is 1

    public Living(GameValues gameValues, Point2D.Double location) {
        super(gameValues, location);
    }

    public boolean damage(int damage) {
        health -= damage;
        return isDead();
    }

    public boolean isDead() {
        return health <= 0;
    }

    protected void setFullHealth() {
        this.health = this.maxHealth;
    }
    
}