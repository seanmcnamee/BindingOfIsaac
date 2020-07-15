package app.screens;

import java.awt.Graphics;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JFrame;
import java.awt.Font;

import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

/**
 * OptionsScreen
 */
public class OptionsScreen extends DisplayScreen {

    private GameValues gameValues;

    public OptionsScreen(JFrame frame, GameValues gameValues) {
        super(frame);
        this.gameValues = gameValues;
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub

    }
    
}