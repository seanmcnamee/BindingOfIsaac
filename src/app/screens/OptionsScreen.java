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

    public OptionsScreen(JFrame frame) {
        super(frame);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub

    }

    private Font setFont(GameValues gameValues) {
        Font returningFont = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(gameValues.GAME_FONT_FILE));
            Font temp = Font.createFont(Font.TRUETYPE_FONT, myStream);
            returningFont = temp.deriveFont(Font.PLAIN, 50);          
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return returningFont;
    }
    
}