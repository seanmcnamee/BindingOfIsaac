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
    public final int SCALE = 4;
    /**
     * The numerator the the number of nanoseconds in 1 second
     * The denomiator is the Ticks Per Second
     */
    public final double nanoSecondsPerTick = 1000000000d / 60d;
    public final int oneSecondInMillis = 1000;
    public final String NAME = "Binding of Isaac - Sean McNamee";

    public final String mainMenuFile = "bin//emptyMainMenu.jpg";
    public final String gameFontFile = "bin/MainScreenFont.ttf";
    public final String gameBackground = "bin/background.png";

    public final String mainMenuButtons = "bin/MenuButtonsSpriteSheet.png";
    public final int menuButtonSize = 160;
    public final float darkenValue = .8f;
    public final float lightenValue = 1.26f;


    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;
    //public final int WIDTH = 300;
    //public final int HEIGHT = WIDTH / 12 * 9;

}
