package app.game.gamefield.elements;

import java.awt.geom.Point2D;
import java.awt.Graphics;

import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;

/**
 * Destroyable
 * Represents anything that has health
 */
public abstract class Destructible extends Drawable {
    protected int maxHealth, health; // A full heart is 2. A half heart is 1

    public Destructible(GameValues gameValues, Point2D.Double location, int zValue) {
        super(gameValues, location, zValue, true);
    }

    public boolean damage(int damage) {
        health -= damage;
        if (isDead()) {
            health = 0;
            return true;
        }   else {
            return false;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void setFullHealth() {
        health = maxHealth;
    }

    public int getCurrentHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void addHealth(int healthToAdd) {
        if (healthToAdd+this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }   else {
            this.health += healthToAdd;
        }
    }
}