package app.game;

import java.awt.Graphics;
import java.awt.Color;

import app.supportclasses.GameValues;

/**
 * GameBar
 */
public class GameBar {
    GameValues gameValues;
    GameField gameField;

    public GameBar(GameValues gameValues, GameField gameField) {
        this.gameValues = gameValues;
        this.gameField = gameField;
    }

    public void render(Graphics g) {
        //Use these values to figure out everyting else
        double xStart = gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        double yStart = 0;
        double xSize = gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_WIDTH;
        double ySize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;
        
        g.setColor(Color.gray);
        g.fillRect((int)xStart, (int)yStart, (int)xSize, (int)ySize);
    }
}