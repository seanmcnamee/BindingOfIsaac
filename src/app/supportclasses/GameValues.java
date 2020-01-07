package app.supportclasses;

import app.game.gamefield.elements.mobiles.players.Player;
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
    public double frameWidth = 790;
    public double frameHeight = 590;
    public final int WIDTH_SCALE_1 = 790;//960;//300;
    public final int HEIGHT_SCALE_1 = 590;//WIDTH_SCALE_1 / 12 * 9;
    
    public final double goalTicksPerSecond = 60.0;
    public final double NANO_SECONDS_PER_TICK = 1000000000d / goalTicksPerSecond; // (NanoSeconds/1 seconds) * (1 second/nanoseconds in 1 tick)
    public final int ONE_SEC_IN_MILLIS = 1000;
    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;

    public final String NAME = "Binding of Isaac - Sean McNamee";
    public final String GAME_FONT_FILE = "res//MainScreenFont.ttf";
    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    //TitleScreen values
    public final String MAIN_MENU_FILE = "res//emptyMainMenu.jpg";
    public final String MAIN_MENU_BUTTONS = "res//MenuButtonsSpriteSheet.png";
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

    /////Game Values
    //GameField
    public final String GAME_BACKGROUND_FILE = "res//background.png";
    public final String STARTING_BACKGROUND_FILE = "res//tutorialbackground.png";
    public final String GAME_WALL_SPRITESHEET = "res//Walls.png";
    public final int WALL_SPRITESHEET_SIZE = 700;
    public final int FIELD_X_SPACES = 13;
    public final int FIELD_Y_SPACES = 7;
    public final double WALL_THICKNESS = 1;
    public double fieldXStart = 0;//For entire field display
    public double fieldYStart = 0;
    public double fieldXSize = 0;
    public double fieldYSize = 0;
    public double fieldXZero = 0;//For in game location representation //TODO allow change fopr screen scrolling (large room)
    public double fieldYZero = 0;
    public double singleSquareX = 0;
    public double singleSquareY = 0;
    
    //GameBar
    public final String GENERAL_GAMEBAR_SPRITE_SHEET = "res//GeneralGameBarShpriteSheet.png";
    public final int GENERAL_GAMEBAR_PICTURE_SIZE = 64;
    public final double GAME_BAR_HEIGHT = .15;
    public final double GAME_BAR_WIDTH = 1;
    public double barXStart = 0;
    public double barYStart = 0;
    public double barXSize = 0;
    public double barYSize = 0;
    
    //Player
    public final String ISSAC_FILE = "res//isaac_spritesheet.png";
    public final int PLAYER_SHEET_BOX_SIZE = 64;
    public final double HEAD_LEG_PERCENT_OVERLAPPING = .17;
    private final double tempHeadYPercent = .65;
    private final double tempLegsYPercent = 1-tempHeadYPercent;
    public final double HEAD_Y_SIZE_PERCENT = tempHeadYPercent + HEAD_LEG_PERCENT_OVERLAPPING/2.0;
    public final double LEGS_Y_SIZE_PERCENT = tempLegsYPercent + HEAD_LEG_PERCENT_OVERLAPPING/2.0;
    public final double HEAD_X_SIZE_PERCENT = 1;
    public final double LEGS_X_SIZE_PERCENT = .6;

    public final double HEAD_Y_OFFSET_PERCENT = -(1-HEAD_Y_SIZE_PERCENT)/2.0;
    public final double LEGS_Y_OFFSET_PERCENT = (1-LEGS_Y_SIZE_PERCENT)/2.0;
    public final double HEAD_X_OFFSET_PERCENT = 0;
    public final double LEGS_X_OFFSET_PERCENT = 0;

    public Player.Characters chosenCharacter = Player.Characters.Isaac;
    public int moveUpKey = KeyEvent.VK_W;
    public int moveDownKey = KeyEvent.VK_S;
    public int moveLeftKey = KeyEvent.VK_A;
    public int moveRightKey = KeyEvent.VK_D;

    //Others
    public final String SPRITE_SHEET = "res//MainSpriteSheet.png";
    public final int SPRITE_SHEET_BOX_SIZE = 20;

    public final double DEGRADABLE_Y_HITBOX = .5;

    //Animation
    public final int TICKS_PER_PICTURE_STEP = 3;

    //General Movement
    public double friction = 6.0; //In blocks per second
    public final double iceAccelerationChange = 1.0/6.0;
    public final double sludgeAccelerationChange = 1.0/3.0;
    
    


}
