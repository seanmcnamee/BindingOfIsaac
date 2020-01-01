package app.game.gamefield.elements.mobiles;

import app.supportclasses.GameValues;

/**
 * Living
 */
public abstract class Living extends Mobile {
    protected int maxHealth, health; // A full heart is 2. A half heart is 1

    public Living(GameValues gameValues, double x, double y) {
        super(gameValues, x, y);
    }

    public boolean damage(int damage) {
        health -= damage;
        return isDead();
    }

    public boolean isDead() {
        return health<= 0;
    }

    protected void setFullHealth() {
        this.health = this.maxHealth;
    }
    
}