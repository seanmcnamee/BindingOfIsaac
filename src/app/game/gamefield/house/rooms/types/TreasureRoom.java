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
 * Locked in every except Basement1
 * TreasureRoom: Never border more than one regular/spawn room
 * Can have an adjacent secret room
 */
public class TreasureRoom extends Room {
    
    public TreasureRoom(GameValues gameValues, Drawable player, Point location) {
        super(gameValues, player, Room.Rooms.Treasure, location);
    }

    @Override
    protected void createMobiles() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void createImmovables() {
        // TODO Auto-generated method stub
        Drawable r = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(3, 1));
        elements.add(r);

        Drawable s = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(9, 1));
        elements.add(s);

        Drawable a = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(3, 4));
        elements.add(a);

        Drawable w = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(9, 4));
        elements.add(w);
    }
}