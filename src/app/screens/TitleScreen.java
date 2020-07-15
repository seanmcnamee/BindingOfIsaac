package app.screens;

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
    private Button btnStart, btnCredits, btnExit, btnStats, btnOptions, btnCollection;
    private GameValues gameValues;
    private DisplayScreen game, credits, stats, options, collection;
    // private Font font;

    public TitleScreen(JFrame frame, GameValues gameValues, Input gameInputs, DisplayScreen game) {
        super(frame);
        background = new BufferedImageLoader(gameValues.MAIN_MENU_FILE);
        SpriteSheet buttons = new SpriteSheet(gameValues.MAIN_MENU_BUTTONS);
        
        btnStart = new Button(buttons.shrink(buttons.grabImage(0, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.START_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.START_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnCredits = new Button(buttons.shrink(buttons.grabImage(1, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.CREDIT_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.CREDIT_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnExit = new Button(buttons.shrink(buttons.grabImage(2, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.EXIT_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.EXIT_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnStats = new Button(buttons.shrink(buttons.grabImage(0, 1, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.STATS_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.STATS_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnOptions = new Button(buttons.shrink(buttons.grabImage(1, 1, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.OPTIONS_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.OPTIONS_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnCollection = new Button(buttons.shrink(buttons.grabImage(2, 1, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.COLLECTION_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.COLLECTION_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
 
        
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

    @Override
    public void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale), null);
        btnStart.render(g);
        btnCredits.render(g);
        btnExit.render(g);
        btnStats.render(g);
        btnOptions.render(g);
        btnCollection.render(g);

        //g.setFont(font);
        //g.drawString("START", mainGUI.getContentPane().getWidth()/2 - 60, (int)(mainGUI.getContentPane().getHeight()*.75));
    }

    public void mouseClicked(MouseEvent e){
        if (btnStart.contains(e.getPoint())) {
            startGame();
        }   else if (btnCredits.contains(e.getPoint())) {
            gotoCredits();
        }   else if (btnStats.contains(e.getPoint())) {
            gotoStats();
        }   else if (btnOptions.contains(e.getPoint())) {
            gotoOptions();
        }   else if (btnCollection.contains(e.getPoint())) {
            gotoCollection();
        }   else if (btnExit.contains(e.getPoint())) {
            exitGame();
        }
        System.out.println("Mouse clicked at: " +e.getPoint());
        
    }

    public void mouseMoved(MouseEvent e) {
        //Set hovering effect for the following buttons...
        //btnStart
        if (!btnStart.isHovering() && btnStart.contains(e.getPoint())) {
            btnStart.setHovering(true);
        }   else if (btnStart.isHovering() && !btnStart.contains(e.getPoint())) {
            btnStart.setHovering(false);
        //btnCredits
        }   else if (!btnCredits.isHovering() && btnCredits.contains(e.getPoint())) {
            btnCredits.setHovering(true);
        }   else if (btnCredits.isHovering() && !btnCredits.contains(e.getPoint())) {
            btnCredits.setHovering(false);
        //btnExit
        }   else if (!btnExit.isHovering() && btnExit.contains(e.getPoint())) {
            btnExit.setHovering(true);
        }   else if (btnExit.isHovering() && !btnExit.contains(e.getPoint())) {
            btnExit.setHovering(false);
        //btnStats
        }   else if (!btnStats.isHovering() && btnStats.contains(e.getPoint())) {
            btnStats.setHovering(true);
        }   else if (btnStats.isHovering() && !btnStats.contains(e.getPoint())) {
            btnStats.setHovering(false);
        //btnOptions
        }   else if (!btnOptions.isHovering() && btnOptions.contains(e.getPoint())) {
            btnOptions.setHovering(true);
        }   else if (btnOptions.isHovering() && !btnOptions.contains(e.getPoint())) {
            btnOptions.setHovering(false);
        //btnCollection
        }   else if (!btnCollection.isHovering() && btnCollection.contains(e.getPoint())) {
            btnCollection.setHovering(true);
        }   else if (btnCollection.isHovering() && !btnCollection.contains(e.getPoint())) {
            btnCollection.setHovering(false);
        }
        
    }

    private void startGame() {
        System.out.println("Starting Game");
        System.out.println("Setting currentScreen to 'game'");
        System.out.println("Game: " + game);
        ((Game)game).initialize();
        gameValues.currentScreen = game;
    }

    private void gotoCredits() {

    }

    private void gotoStats() {

    }

    private void gotoOptions() {

    }

    private void gotoCollection() {

    }

    private void exitGame() {
        gameValues.gameState = GameValues.GameState.QUIT;
        System.exit(0);
    }





    
}