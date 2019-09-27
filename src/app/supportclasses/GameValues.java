package app.supportclasses;

/**
 * GameValues for the game
 */
public class GameValues {

    public enum GameState {
        NOTSTARTED, RUNNING, WON, LOST;
    }

    /**
     * TODO Figure out exact usage of SCALE
     */
    public double gameScale = 1;
    public final int WIDTH_SCALE_1 = 960;//300;
    public final int HEIGHT_SCALE_1 = 540;//WIDTH_SCALE_1 / 12 * 9;
    
    /**
     * The numerator the the number of nanoseconds in 1 second
     * The denomiator is the Ticks Per Second
     */
    public final double NANO_SECONDS_PER_TICK = 1000000000d / 60d;
    public final int ONE_SEC_IN_MILLIS = 1000;
    public final String NAME = "Binding of Isaac - Sean McNamee";

    public final String MAIN_MENU_FILE = "bin//emptyMainMenu.jpg";
    public final String GAME_FONT_FILE = "bin/MainScreenFont.ttf";
    public final String GAME_BACKGROUND_FILE = "bin/background.png";

    public final String MAIN_MENU_BUTTONS = "bin/MenuButtonsSpriteSheet.png";
    public final int MENU_BUTTON_SIZE = 170;
    public final float DARKEN_VALUE = .8f;
    public final float LIGHTEN_VALUE = 1.26f;
    public final double START_BUTTON_Y = .8;
    public final double START_BUTTON_X = .5;
    public final double CREDIT_BUTTON_Y = .4;
    public final double CREDIT_BUTTON_X = .1;
    public final double EXIT_BUTTON_Y = .4;
    public final double EXIT_BUTTON_X = .82;


    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;
    

}
