package app.game.gamefield.elements.immovables.doors;

import java.awt.geom.Point2D.Double;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.house.Room;
import app.supportclasses.GameValues;

/**
 * Door
 */
public class Door extends Drawable {
    boolean open;

    public Door(GameValues gameValues, Room.Rooms door, Double location) {
        super(gameValues, location);
        // TODO Auto-generated constructor stub
    }

    
}