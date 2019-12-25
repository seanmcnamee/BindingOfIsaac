package app.game.gamefield.elements.mobiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import app.game.gamefield.elements.mobiles.stats.CharacterStats;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;

/**
 * Character
 */
public class Character extends Mobile {

    public Character(GameValues gameValues, CharacterStats.Character c, double xStart, double yStart) {
        super(xStart, yStart);
        this.stats = new CharacterStats(gameValues, c);
    }

    public void tick() {
        move();
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub

    }

    
}