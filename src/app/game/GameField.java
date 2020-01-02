package app.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import app.game.gamefield.rooms.Room;
import app.game.gamefield.rooms.SpawnRoom;
import app.supportclasses.GameValues;
import app.game.gamefield.elements.mobiles.players.Player;
/**
 * GameField
 */
public class GameField {
    
    private GameValues gameValues;
    private Player player;

    //TODO make a map class and stuff
    private Room room;
    

    public GameField(GameValues gameValues, Player player) {
        this.gameValues = gameValues;
        this.player = player;
        room = new SpawnRoom(gameValues, player);
    }

    public void tick() {
        room.tick();
    }

    public void render(Graphics g) {
        //this.gameValues.fieldXStart = gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        //this.gameValues.fieldYStart = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;
        this.gameValues.fieldXSize = gameValues.WIDTH_SCALE_1*(gameValues.gameScale);
        this.gameValues.fieldYSize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*(1-gameValues.GAME_BAR_HEIGHT);

        gameValues.singleSquareX = (gameValues.fieldXSize)/gameValues.XSpaces;
        gameValues.singleSquareY = (gameValues.fieldYSize)/gameValues.YSpaces;

        double excessWidth = gameValues.frameWidth-(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        double excessHeight = gameValues.frameHeight-(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        this.gameValues.fieldXStart = excessWidth/2.0;//gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        this.gameValues.fieldYStart = gameValues.barYSize + excessHeight/2.0;

        room.render(g); //TODO make this through the map class
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}