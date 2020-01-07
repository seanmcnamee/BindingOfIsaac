package app.game.gamefield.elements.immovables.doors;

import java.awt.geom.Point2D.Double;

import app.game.gamefield.elements.immovables.Indestructible;
import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;

/**
 * Door
 */
public class Door extends Indestructible {
    boolean open;

    public Door(GameValues gameValues, Room.Rooms door, Double location) {
        super(gameValues, location);
        // TODO Auto-generated constructor stub
    }

    
}