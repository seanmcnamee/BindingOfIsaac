package app.game.gamefield.elements.immovables.doors;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;

/**
 * Door
 */
public class Door extends Drawable {
    private InterchangeableImage images;
    private DoorState doorState;

    public enum DoorState {
        Locked, Closed, Open;
    }

    public enum DoorPosition {
        Top, Right, Bottom, Left;
    }

    public Door(GameValues gameValues, Room.Rooms door, DoorPosition position) {
        super(gameValues, calculationLocation(position, gameValues));
        setDoorImages(door, position);
    }

    private static Point2D.Double calculationLocation(DoorPosition position, GameValues gameValues) {
        double x, y;

        if (position==DoorPosition.Top||position==DoorPosition.Bottom) {
            x = gameValues.fieldXSize/2.0;
        }   else {
            if (position==DoorPosition.Right) {
                x = gameValues.fieldXSize + gameValues.WALL_THICKNESS/2.0;
            } else {
                x = -gameValues.WALL_THICKNESS/2.0;
            }
        }

        if (position==DoorPosition.Right||position==DoorPosition.Left) {
            y = gameValues.fieldYSize/2.0;
        }   else {
            if (position==DoorPosition.Bottom) {
                y = gameValues.fieldYSize + gameValues.WALL_THICKNESS/2.0;
            } else {
                y = -gameValues.WALL_THICKNESS/2.0;
            }
        }

        return new Point2D.Double(x, y);
    }

    private void setDoorImages(Room.Rooms doorType, DoorPosition position) {

        SpriteSheet doorSprites = new SpriteSheet(gameValues.DOOR_SPRITE_SHEET);
        switch(doorType) {
            case Regular:

            case Arcade:
            case Treasure:
            case Boss:
            case Secret:
            default:

        }
    }

    public void unlock() {

    }
    
}