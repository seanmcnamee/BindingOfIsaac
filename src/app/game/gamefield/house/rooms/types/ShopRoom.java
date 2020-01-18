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
 * ShopRoom
 * always locked (key)
 * Only 1 per floor (chapters 1 2 and 3 only)
 * secret room can be adjacent
 */
public class ShopRoom extends Room {
    
    public ShopRoom(GameValues gameValues, Drawable player, Point location) {
        super(gameValues, player, Room.Rooms.Shop, location);
    }

    @Override
    protected void createMobiles() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void createImmovables() {
        // TODO Auto-generated method stub
        Drawable r = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(0, 4));
        elements.add(r);

        Drawable s = new Degradable(this.gameValues, Degradables.Rock, new Point2D.Double(0, 5));
        elements.add(s);
    }
}