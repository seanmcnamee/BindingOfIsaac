package app.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.rooms.Room;
import app.game.gamefield.rooms.SpawnRoom;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.game.gamefield.elements.mobiles.Character;
/**
 * GameField
 */
public class GameField {
    
    private GameValues gameValues;
    private Character player;
    //TODO make a map class and stuff
    private Room room;
    

    public GameField(GameValues gameValues) {
        this.gameValues = gameValues;
        player = new Character(gameValues, gameValues.chosenCharacter, 0, 0);
        room = new SpawnRoom(gameValues, player);
    }

    public void tick() {
        room.tick();
    }

    public void render(Graphics g) {
        this.gameValues.fieldXStart = 0;
        this.gameValues.fieldYStart = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;
        this.gameValues.fieldXSize = gameValues.WIDTH_SCALE_1*(gameValues.gameScale);
        this.gameValues.fieldYSize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*(1-gameValues.GAME_BAR_HEIGHT);
        room.render(g); //TODO make this through the map class
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
}