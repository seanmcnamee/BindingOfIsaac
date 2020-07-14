package app.supportclasses;

import app.game.gamefield.elements.mobiles.players.Player;
import java.awt.Point;
import java.awt.Color;

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

    public boolean debugMode = true;
    public boolean generationDebugMode = true;

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
    public final String STARTING_DIRECTIONS_FILE = "res//directions.png";
    public final double DIRECTIONS_WIDTH = .8;
    public final double DIRECTIONS_HEIGHT = .35;
    public final double DIRECTIONS_X_PERCENT_LOCATION = .5;
    public final double DIRECTIONS_Y_PERCENT_LOCATION = .35;
    public final String GAME_WALL_SPRITESHEET = "res//Walls.png";
    public final int WALL_SPRITESHEET_SIZE = 700;
    public final String DEGRADABLES_SPRITE_SHEET = "res//DegradablesSpriteSheet.png";
    public final int DEGRADABLES_SPRITE_SHEET_BOX_SIZE = 41;
    public final String DOOR_SPRITE_SHEET = "res//doorSpriteSheet.png";
    public final int DOOR_SPRITE_SHEET_BOX_SIZE = 185;
    public final Point MAPSIZE = new Point(7, 5);

    public final int FIELD_X_SPACES = 13;
    public final int FIELD_Y_SPACES = 7;
    public final double WALL_THICKNESS = 1;
    public double fieldXStart = 0;//For entire field display
    public double fieldYStart = 0;
    public double fieldXSize = 0;
    public double fieldYSize = 0;
    public double fieldXZero = 0;//For in game location representation //TODO allow change for screen scrolling (large room)
    public double fieldYZero = 0;
    public double singleSquareX = 0;
    public double singleSquareY = 0;

    //Pixel heights
    private final int DOOR_HEIGHT_REGULAR = 86;
    private final int DOOR_HEIGHT_BOSS_CLOSED = 126;
    private final int DOOR_HEIGHT_BOSS_OPEN = 185;
    private final int DOOR_HEIGHT_BOSS_ABOVE = DOOR_HEIGHT_BOSS_CLOSED-DOOR_HEIGHT_REGULAR;
    private final int DOOR_HEIGHT_BOSS_BELOW = DOOR_HEIGHT_BOSS_OPEN-DOOR_HEIGHT_BOSS_CLOSED;
    private final int DOOR_HEIGHT_TREASURE = 91;
    private final int DOOR_HEIGHT_SECRET = 121;
    private final int DOOR_HEIGHT_ARCADE = 108;

    //Default blocksize and hitbox size for doors
    public final double DOOR_DEPTH = .75;
    public final double DOOR_WIDTH = 2;
    public final double DOOR_HITBOX_DEPTH = 1;
    public final double DOOR_HITBOX_WIDTH = .1;
    public final double DOOR_DEPTH_OFFSET = 0;
    public final double DOOR_HITBOX_DEPTH_OFFSET = 0;

    private final double DOOR_BLOCKSIZE_RATIO = DOOR_DEPTH/DOOR_HEIGHT_REGULAR;

    public final double DOOR_BOSS_OPEN_DEPTH = DOOR_HEIGHT_BOSS_OPEN*DOOR_BLOCKSIZE_RATIO;
    public final double DOOR_BOSS_CLOSED_DEPTH = DOOR_HEIGHT_BOSS_CLOSED*DOOR_BLOCKSIZE_RATIO;
    public final double DOOR_BOSS_TREASURE = DOOR_HEIGHT_TREASURE*DOOR_BLOCKSIZE_RATIO;
    public final double DOOR_BOSS_SECRET = DOOR_HEIGHT_SECRET*DOOR_BLOCKSIZE_RATIO;
    public final double DOOR_BOSS_ARCADE = DOOR_HEIGHT_ARCADE*DOOR_BLOCKSIZE_RATIO;

    public final double DOOR_BOSS_OPEN_DEPTH_OFFSET = (DOOR_HEIGHT_BOSS_BELOW-DOOR_HEIGHT_BOSS_ABOVE)/2.0*DOOR_BLOCKSIZE_RATIO; //Half of what is extra
    public final double DOOR_BOSS_OPEN_HITBOX_DEPTH = DOOR_HITBOX_DEPTH*(DOOR_DEPTH/DOOR_BOSS_OPEN_DEPTH);
    public final double DOOR_BOSS_OPEN_HITBOX_DEPTH_OFFSET = DOOR_BOSS_OPEN_DEPTH_OFFSET/DOOR_BOSS_OPEN_DEPTH;

    //Z values
    public final int TOP_BOTTOM_WALL_Z = -50;
    public final int SIDE_WALL_Z = -100;
    public final int DOOR_Z = -10;
    public final int DESTRUCTIBLE_Z = 0;
    public final int MOBILE_Z = 5;
    public final int FLYING_Z = 100;

    //Shadow Diameter Size
    public final double SHADOW_X = 1;
    public final double SHADOW_Y = .3;
    public final double SHADOW_Y_UNDER = 2.0/3.0;
    public final Color SHADOW_COLOR = new Color(0, 0, 0, 40); //Black transparent

    
    //GameBar
    public final String GENERAL_GAMEBAR_SPRITE_SHEET = "res//GeneralGameBarShpriteSheet.png";
    public final int GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE = 64;
    public final String ICON_SPRITE_SHEET = "res//RoomIconSpriteSheet.png";
    public final int ICON_SPRITE_SHEET_BOX_SIZE = 69;
    public final double GAME_BAR_HEIGHT = .15;
    public final double GAME_BAR_WIDTH = 1;
    public double barXStart = 0;
    public double barYStart = 0;
    public double barXSize = 0;
    public double barYSize = 0;
    public final double MINIMAP_X_SIZE = .4;
    public final double MINIMAP_Y_SIZE = 1;
    

    
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


    public final double DEGRADABLE_Y_HITBOX = .5;

    //Animation
    public final int TICKS_PER_PICTURE_STEP = 3;

    //General Movement
    public double friction = 6.0; //In blocks per second
    public final double iceAccelerationChange = 1.0/6.0;
    public final double sludgeAccelerationChange = 1.0/3.0;
    
    


}
