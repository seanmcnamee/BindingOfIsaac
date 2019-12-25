package app.game.gamefield.elements;

import java.awt.image.BufferedImage;

import app.supportclasses.GameValues;
/**
 * Stats
 */
public abstract class Stats {
    protected BufferedImage image;
    protected GameValues gameValues;

    public Stats(GameValues gameValues) {
        this.gameValues = gameValues;
    }
}