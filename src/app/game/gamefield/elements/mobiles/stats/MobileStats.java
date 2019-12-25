package app.game.gamefield.elements.mobiles.stats;

import app.game.gamefield.elements.Stats;
import app.supportclasses.GameValues;

/**
 * Every Entity's Stats (all entities can move)
 */
public abstract class MobileStats extends Stats{
    protected int maxSpeed;

    public MobileStats(GameValues gameValues) {
        super(gameValues);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }
    //TODO possibly add mass for more realizstic collisions
}