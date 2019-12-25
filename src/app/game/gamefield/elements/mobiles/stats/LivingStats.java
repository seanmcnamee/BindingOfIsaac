package app.game.gamefield.elements.mobiles.stats;

import app.supportclasses.GameValues;

/**
 * LivingStats
 */
public abstract class LivingStats extends MobileStats {
    protected int maxHealth, health; // A full heart is 2. A half heart is 1

    public LivingStats(GameValues gameValues) {
        super(gameValues);
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