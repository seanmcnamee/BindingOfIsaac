package app.game.gamefield.house.rooms.types;

import app.game.gamefield.elements.immovables.Degradable;
import app.game.gamefield.elements.immovables.Degradable.Degradables;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.Traversable;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * SpawnRoom
 * adjacent to 3 or 4 rooms
 * entrances to/from never have rocks/gaps in the way
 */
public class SecretRoom extends Room {
    
    public SecretRoom(GameValues gameValues, Drawable player, Point location) {
        super(gameValues, player, Room.Rooms.Secret, location);
    }

    @Override
    protected void createMobiles() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void createImmovables() {
        // TODO Auto-generated method stub
        Drawable r = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(5, 1));
        elements.add(r);
    }
}