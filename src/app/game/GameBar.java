package app.game;

import java.awt.Graphics;
import java.awt.Color;

import app.supportclasses.GameValues;

/**
 * GameBar
 */
public class GameBar {
    private GameValues gameValues;
    private GameField gameField;

    public GameBar(GameValues gameValues, GameField gameField) {
        this.gameValues = gameValues;
        this.gameField = gameField;
    }

    public void render(Graphics g) {
        //Use these values to figure out everyting else
        this.gameValues.barXSize = gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_WIDTH;
        this.gameValues.barYSize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;

        double excessWidth = gameValues.frameWidth-(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        double excessHeight = gameValues.frameHeight-(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        this.gameValues.barXStart = excessWidth/2.0;//gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        this.gameValues.barYStart = excessHeight/2.0;
        
        
        g.setColor(Color.gray);
        g.fillRect((int)gameValues.barXStart, (int)gameValues.barYStart, (int)gameValues.barXSize, (int)gameValues.barYSize);
    }
}