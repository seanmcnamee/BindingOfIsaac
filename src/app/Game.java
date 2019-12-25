package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;

import app.game.GameBar;
import app.game.GameField;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

/**
 * Main playing screen of the App
 */
public class Game extends DisplayScreen{
    
    GameValues gameValues;
    GameField gameField;
    GameBar gameBar;

    public Game(JFrame frame, GameValues gameValues) {
        super(frame);
        this.gameValues = gameValues;
        gameField = new GameField(gameValues);
        gameBar = new GameBar(gameValues, gameField);
    }


    public void tick() {

    }

    public void render(Graphics g) {
        gameField.render(g);
        gameBar.render(g);
    }

    public void mouseMoved(MouseEvent e) {
    }
    
}