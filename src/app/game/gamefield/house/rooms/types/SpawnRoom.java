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
 */
public class SpawnRoom extends Room {
    
    public SpawnRoom(GameValues gameValues, Drawable player, Point location, Traversable above, Traversable below, Traversable left, Traversable right) {
        super(gameValues, player, Room.Rooms.Spawn, location, above, above, above, above);
    }

    @Override
    protected void createMobiles() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void createImmovables() {
        // TODO Auto-generated method stub
        Drawable r = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(0, 0));
        elements.add(r);

        /*
        for (int i = 0; i < gameValues.FIELD_X_SPACES; i++) {
            if (i%6!=0) {
                Drawable rock = new Degradable(gameValues, Degradables.Rock, new Point2D.Double(i, 2));
                elements.add(rock);
            }
        }*/
    }
}