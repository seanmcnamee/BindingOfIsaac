package app.game.gamefield.elements;

import java.awt.image.BufferedImage;

import app.supportclasses.GameValues;
/**
 * Stats
 */
public abstract class Stats {
    protected BufferedImage image;
    public GameValues gameValues;

    public Stats(GameValues gameValues) {
        this.gameValues = gameValues;
    }

    public GameValues getGameValues() {
        return gameValues;
    }
}