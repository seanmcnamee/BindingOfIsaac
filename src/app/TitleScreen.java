package app;

import java.awt.Graphics;
import javax.swing.JFrame;


import app.supportclasses.BufferedImageLoader;
import app.supportclasses.Button;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;
import app.supportclasses.Input;
import app.supportclasses.SpriteSheet;



import java.awt.event.MouseEvent;

/**
 * MainScreen of the App
 */
public class TitleScreen extends DisplayScreen {

    private final BufferedImageLoader background;
    private Button btnStart, btnCredits, btnExit;
    private GameValues gameValues;
    private DisplayScreen game;
    // private Font font;

    public TitleScreen(JFrame frame, GameValues gameValues, Input gameInputs, DisplayScreen game) {
        super(frame);
        background = new BufferedImageLoader(gameValues.MAIN_MENU_FILE);
        SpriteSheet buttons = new SpriteSheet(gameValues.MAIN_MENU_BUTTONS);
        // TODO fix this position and finish up the others
        
        btnStart = new Button(buttons.shrink(buttons.grabImage(0, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.START_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.START_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnCredits = new Button(buttons.shrink(buttons.grabImage(1, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.CREDIT_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.CREDIT_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnExit = new Button(buttons.shrink(buttons.grabImage(2, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.EXIT_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.EXIT_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        
        
        this.gameValues = gameValues;
        this.game = game;

        //font = setFont(gameValues);
    }

    /*
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
    */

    //TODO setup transparent background buttons for this menu
    @Override
    public void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale), null);
        btnStart.render(g);
        btnCredits.render(g);
        btnExit.render(g);
        //g.setFont(font);
        //g.drawString("START", mainGUI.getContentPane().getWidth()/2 - 60, (int)(mainGUI.getContentPane().getHeight()*.75));
    }

    public void mouseClicked(MouseEvent e){
        if (btnStart.contains(e.getPoint())) {
            System.out.println("Starting Game");
            System.out.println("Setting currentScreen to 'game'");
            System.out.println("Game: " + game);
            gameValues.currentScreen = game;
        }
        System.out.println("Mouse clicked at: " +e.getPoint());
        
    }

    public void mouseMoved(MouseEvent e) {
        if (!btnStart.isHovering() && btnStart.contains(e.getPoint())) {
            btnStart.setHovering(true);
        }   else if (btnStart.isHovering() && !btnStart.contains(e.getPoint())) {
            btnStart.setHovering(false);
        } else if (!btnCredits.isHovering() && btnCredits.contains(e.getPoint())) {
            btnCredits.setHovering(true);
        }   else if (btnCredits.isHovering() && !btnCredits.contains(e.getPoint())) {
            btnCredits.setHovering(false);
        } else if (!btnExit.isHovering() && btnExit.contains(e.getPoint())) {
            btnExit.setHovering(true);
        }   else if (btnExit.isHovering() && !btnExit.contains(e.getPoint())) {
            btnExit.setHovering(false);
        }
        
    }
    
}