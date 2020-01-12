package app.game.gamefield.house.rooms.types;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.Traversable;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * SpawnRoom
 */
public class SpawnRoom extends Room {
    private BufferedImage directions;
    private boolean directionsNeeded;
    
    public SpawnRoom(GameValues gameValues, Drawable player, boolean directionsAreNeeded, Point location) {
        super(gameValues, player, Room.Rooms.Spawn, location);
        this.directionsNeeded = directionsAreNeeded;
        if (this.directionsNeeded) {
            directions = new BufferedImageLoader(gameValues.STARTING_DIRECTIONS_FILE).getImage();
        }
    }

    @Override
    protected void createMobiles() {
        //No enemies
    }

    @Override
    protected void createImmovables() {
        //No objects in room
    }

    @Override
    protected void drawBackground(Graphics g) {
        super.drawBackground(g);
        if (directionsNeeded) {
            g.drawImage(this.directions, (int)(gameValues.fieldXStart+(gameValues.fieldXSize*(1-gameValues.DIRECTIONS_WIDTH)/2)), (int)(gameValues.fieldYStart+(gameValues.fieldYSize*(1-gameValues.DIRECTIONS_HEIGHT)/2)), (int)(gameValues.fieldXSize*gameValues.DIRECTIONS_WIDTH), (int)(gameValues.fieldYSize*gameValues.DIRECTIONS_HEIGHT), null);
        }
   }
}