package app;

import java.awt.Graphics;
import javax.swing.JFrame;
import app.DisplayScreen;

import java.awt.event.MouseEvent;

/**
 * MainScreen of the App
 */
public class TitleScreen extends DisplayScreen {

    private final BufferedImageLoader background;
    private Button btnStart;
    private GameValues gameValues;
    private DisplayScreen game;
    // private Font font;

    public TitleScreen(JFrame frame, GameValues gameValues, Input gameInputs, DisplayScreen game) {
        super(frame);
        background = new BufferedImageLoader(gameValues.mainMenuFile);
        SpriteSheet buttons = new SpriteSheet(gameValues.mainMenuButtons);
        // TODO fix this position and finish up the others
        
        btnStart = new Button(buttons.grabImage(0, 0, 1, 1, gameValues.menuButtonSize), 200, 200, gameValues);
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
    void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, mainGUI.getContentPane().getWidth(), mainGUI.getContentPane().getHeight(), null);
        btnStart.render(g);
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
        
    }

    public void mouseMoved(MouseEvent e) {
        if (!btnStart.isHovering() && btnStart.contains(e.getPoint())) {
            btnStart.setHovering(true);
        }   else if (btnStart.isHovering() && !btnStart.contains(e.getPoint())) {
            btnStart.setHovering(false);
        }
    }
    
}