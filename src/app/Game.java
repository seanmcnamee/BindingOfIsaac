package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

/**
 * Main playing screen of the App
 */
public class Game extends DisplayScreen{

    private final BufferedImageLoader background;

    public Game(JFrame frame, GameValues gameValues) {
        super(frame);
        background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE);
    }


    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, mainGUI.getContentPane().getWidth(), mainGUI.getContentPane().getHeight(), null);
    }

    public void mouseMoved(MouseEvent e) {
    }
    
}