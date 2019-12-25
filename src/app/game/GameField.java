package app.game;

import java.awt.Graphics;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
/**
 * GameField
 */
public class GameField {
    private final BufferedImageLoader defaultBackground;
    GameValues gameValues;
    

    public GameField(GameValues gameValues) {
        this.defaultBackground = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE);
        this.gameValues = gameValues;
    }

    public void render(Graphics g) {
        //Everything else should be calculated for placement based on these
        double yStart = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;
        double xStart = 0;
        double xSize = gameValues.WIDTH_SCALE_1*(gameValues.gameScale);
        double ySize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*(1-gameValues.GAME_BAR_HEIGHT);
        

        g.drawImage(defaultBackground.getImage(), (int)xStart, (int)yStart, (int)xSize, (int)ySize, null);
    }
}