package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import app.DisplayScreen;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * MainScreen of the App
 */
public class TitleScreen extends DisplayScreen {

    private final BufferedImageLoader background;
    private Font font;

    public TitleScreen(JFrame frame, GameValues gameValues) {
        super(frame);
        background = new BufferedImageLoader(gameValues.mainMenuFile);

        font = setFont(gameValues);
    }

    private Font setFont(GameValues gameValues) {
        Font returningFont = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(gameValues.gameFontFile));
            Font temp = Font.createFont(Font.TRUETYPE_FONT, myStream);
            returningFont = temp.deriveFont(Font.PLAIN, 50);          
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return returningFont;
    }

    //TODO setup transparent background buttons for this menu
    @Override
    void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, mainGUI.getContentPane().getWidth(), mainGUI.getContentPane().getHeight(), null);
        //g.setFont(font);
        //g.drawString("START", mainGUI.getContentPane().getWidth()/2 - 60, (int)(mainGUI.getContentPane().getHeight()*.75));
    }
    
}