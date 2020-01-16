package app.game.gamefield.elements.immovables.doors;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;
import java.util.NoSuchElementException;
import java.awt.image.BufferedImage;

/**
 * Door
 */
public class Door extends Drawable {
    private InterchangeableImage images;
    private DoorState doorState;
    private DoorPosition position;

    public enum DoorState {
        Locked, Closed, Open;
    }

    public enum DoorPosition {
        Top, Right, Below, Left;
    }

    public Door(GameValues gameValues, Room.Rooms door, DoorPosition position) {
        super(gameValues, calculationLocation(position, gameValues));
        this.position = position;
        doorState = DoorState.Open;
        setDoorSizeAndHitBox();
        setDoorImages(door);
    }

    private static Point2D.Double calculationLocation(DoorPosition position, GameValues gameValues) {
        System.out.println("Door position: " + position);
        double x, y;
        double halfBlock = .5;

        if (position==DoorPosition.Top||position==DoorPosition.Below) {
            x = gameValues.FIELD_X_SPACES/2.0;
        }   else {
            if (position==DoorPosition.Right) {
                x = gameValues.FIELD_X_SPACES + gameValues.WALL_THICKNESS/2.0 - gameValues.DOOR_OFFSET;
            } else {
                x = -gameValues.WALL_THICKNESS/2.0 + gameValues.DOOR_OFFSET;
            }
        }

        x-= halfBlock;

        if (position==DoorPosition.Right||position==DoorPosition.Left) {
            y = gameValues.FIELD_Y_SPACES/2.0;
        }   else {
            if (position==DoorPosition.Below) {
                y = gameValues.FIELD_Y_SPACES + gameValues.WALL_THICKNESS/2.0 - gameValues.DOOR_OFFSET;
            } else {
                y = -gameValues.WALL_THICKNESS/2.0 + gameValues.DOOR_OFFSET;
            }
        }

        y-=halfBlock;

        System.out.println("\tCreating at " + x + ", " + y);
        return new Point2D.Double(x, y);
    }

    private void setDoorSizeAndHitBox() {
        System.out.println("Setting size/hitbox for " + this.position);
        switch(this.position) {
            case Top:
                this.sizeInBlocks = new Point2D.Double(2, 1);
                this.hitbox = new HitBox(.7, 1, 0, 0);
                break;
            case Right:
                this.sizeInBlocks = new Point2D.Double(1, 2);
                this.hitbox = new HitBox(1, .7, 0, 0);
                break;
            case Below:
                this.sizeInBlocks = new Point2D.Double(2, 1);
                this.hitbox = new HitBox(.7, 1, 0, 0);
                break;
            case Left:
                this.sizeInBlocks = new Point2D.Double(1, 2);
                this.hitbox = new HitBox(1, .7, 0, 0);
                break;
            default:
                throw new NoSuchElementException("Unknown enum '" + position + "' given.");
        }
    }

    private void setDoorImages(Room.Rooms doorType) {
        SpriteSheet doorSprites = new SpriteSheet(gameValues.DOOR_SPRITE_SHEET);

        switch(doorType) {
            case Spawn:
            case Regular:
                images = new InterchangeableImage(2, new HitBox());
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case Shop:
                images = new InterchangeableImage(3, new HitBox());
                images.setImage(2, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case Secret:
                images = new InterchangeableImage(1, new HitBox());
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 1, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case Treasure:
                images = new InterchangeableImage(3, new HitBox());
                images.setImage(2, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case Boss:
                images = new InterchangeableImage(2, new HitBox());
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 3, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 3, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case Arcade:
                images = new InterchangeableImage(3, new HitBox());
                images.setImage(2, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            default:
                throw new NoSuchElementException("Enum: " + doorType + " not yet added!");
        }
        images.setCurrentImageIndex(0);
    }

    private BufferedImage flipImagesOnPosition(BufferedImage imageToTransform) {
        System.out.println("Flipping door at " + this.position);
        switch(this.position) {
            case Top:
                return imageToTransform;
            case Right:
                return SpriteSheet.rotateClock90(imageToTransform);
            case Below:
                return SpriteSheet.flipTopBottom(imageToTransform);
            case Left:
                return SpriteSheet.rotateCounter90(imageToTransform);
            default:
                throw new NoSuchElementException("Unknown enum '" + position + "' given.");
        }
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE/2;
    }

    @Override
    protected BufferedImage getImage() {
        return images.getCurrentImage();
    }
    
    public boolean isLocked() {
        return doorState==DoorState.Locked;
    }

    public boolean isOpen() {
        return doorState==DoorState.Open;
    }

    //TODO MIGHT have to recheck all enenmies dead when this happens
    public void unlock() {
        this.doorState = DoorState.Closed;
        this.images.setCurrentImageIndex(0);
    }

    public void open() {
        this.doorState = DoorState.Open;
        this.images.setCurrentImageIndex(1);
    }

    public Room getRoomFromDoorPosition(Room startingRoom) {
        switch(position) {
            case Top:
                return (Room)(startingRoom.getAbove());
            case Right:
                return (Room)(startingRoom.getRight());
            case Below:
                return (Room)(startingRoom.getBelow());
            case Left:
                return (Room)(startingRoom.getLeft());
            default:
                throw new NoSuchElementException("Unknown enum '" + position + "' given.");
        }
    }

    public boolean isTop() {
        return position==DoorPosition.Top;
    }

    public boolean isRight() {
        return position==DoorPosition.Right;
    }

    public boolean isBelow() {
        return position==DoorPosition.Below;
    }

    public boolean isLeft() {
        return position==DoorPosition.Left;
    }
    
}