package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;

import app.gameclasses.GameBar;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

/**
 * Main playing screen of the App
 */
public class Game extends DisplayScreen{

    private final BufferedImageLoader background;
    GameValues gameValues;
    GameBar gameBar = new GameBar();

    public Game(JFrame frame, GameValues gameValues) {
        super(frame);
        this.gameValues = gameValues;
        background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE);
    }


    public void tick() {

    }

    public void render(Graphics g) {
        double xSize = gameValues.WIDTH_SCALE_1*(gameValues.gameScale);
        double ySize = gameValues.HEIGHT_SCALE_1*(gameValues.gameScale*(1-gameValues.GAME_BAR_HEIGHT));
        double yStart = (gameValues.HEIGHT_SCALE_1*gameValues.gameScale)-ySize;

        g.drawImage(background.getImage(), 0, (int)yStart, (int)xSize, (int)ySize, null);
    }

    public void mouseMoved(MouseEvent e) {
    }
    
}