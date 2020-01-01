package app.supportclasses;

import app.game.gamefield.elements.mobiles.Character;
import java.awt.event.KeyEvent;

/**
 * GameValues for the game
 */
public class GameValues {

    public enum GameState {
        NOTSTARTED, RUNNING, WON, LOST, QUIT;
    }

    //Overall Application Values
    public double gameScale = 1;
    public final int WIDTH_SCALE_1 = 790;//960;//300;
    public final int HEIGHT_SCALE_1 = 590;//WIDTH_SCALE_1 / 12 * 9;

    public final double NANO_SECONDS_PER_TICK = 1000000000d / 60d; // (NanoSeconds/1 seconds) * (1 second/nanoseconds in 1 tick)
    public final int ONE_SEC_IN_MILLIS = 1000;
    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;

    public final String NAME = "Binding of Isaac - Sean McNamee";
    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    
    public final String GAME_FONT_FILE = "res/MainScreenFont.ttf";
    public final String GAME_BACKGROUND_FILE = "res/background.png";

    //TitleScreen values
    public final String MAIN_MENU_FILE = "res//emptyMainMenu.jpg";
    public final String MAIN_MENU_BUTTONS = "res/MenuButtonsSpriteSheet.png";
    public final int MENU_BUTTON_SIZE = 170;
    public final float DARKEN_VALUE = .8f;
    public final float LIGHTEN_VALUE = 1.26f;
    public final double START_BUTTON_Y = .8;
    public final double START_BUTTON_X = .5;
    public final double CREDIT_BUTTON_Y = .42;
    public final double CREDIT_BUTTON_X = .1;
    public final double EXIT_BUTTON_Y = .42;
    public final double EXIT_BUTTON_X = .835;
    public final double STATS_BUTTON_Y = .91;
    public final double STATS_BUTTON_X = .31;
    public final double OPTIONS_BUTTON_Y = .935;
    public final double OPTIONS_BUTTON_X = .467;
    public final double COLLECTION_BUTTON_Y = .96;
    public final double COLLECTION_BUTTON_X = .64;

    //Game Values
    public final double GAME_BAR_HEIGHT = .2;
    public final double GAME_BAR_WIDTH = 1;
    public final String ISSAC_FILE = "res//Isaac_Walking.gif";
    public Character.Characters chosenCharacter = Character.Characters.Issac;
    public int moveUpKey = KeyEvent.VK_W;
    public int moveDownKey = KeyEvent.VK_S;
    public int moveLeftKey = KeyEvent.VK_A;
    public int moveRightKey = KeyEvent.VK_D;
    public double fieldXStart = 0;
    public double fieldYStart = 0;
    public double fieldXSize = 0;
    public double fieldYSize = 0;
    public final int XSpaces = 14;
    public final int YSpaces = 8;
    public final double wallSpaceY = .1;


}
