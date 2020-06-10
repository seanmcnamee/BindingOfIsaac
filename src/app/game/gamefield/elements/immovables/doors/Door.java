package app.game.gamefield.elements.immovables.doors;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.types.ArcadeRoom;
import app.game.gamefield.house.rooms.types.BossRoom;
import app.game.gamefield.house.rooms.types.RegularRoom;
import app.game.gamefield.house.rooms.types.SecretRoom;
import app.game.gamefield.house.rooms.types.ShopRoom;
import app.game.gamefield.house.rooms.types.SpawnRoom;
import app.game.gamefield.house.rooms.types.TreasureRoom;
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

    public Door(GameValues gameValues, Room room, DoorPosition position) {
        super(gameValues, calculationLocation(position, gameValues), gameValues.DOOR_Z);
        this.position = position;
        doorState = DoorState.Open;
        setDoorSizeAndHitBox();
        setDoorImages(room);
    }

    private static Point2D.Double calculationLocation(DoorPosition position, GameValues gameValues) {
        System.out.println("Door position: " + position);
        double x, y;
        double halfBlock = .5;

        if (position==DoorPosition.Top||position==DoorPosition.Below) {
            x = gameValues.FIELD_X_SPACES/2.0;
        }   else {
            if (position==DoorPosition.Right) {
                x = gameValues.FIELD_X_SPACES + gameValues.WALL_THICKNESS/2.0 - (gameValues.WALL_THICKNESS-gameValues.DOOR_DEPTH)/2.0;
            } else {
                x = -gameValues.WALL_THICKNESS/2.0 + (gameValues.WALL_THICKNESS-gameValues.DOOR_DEPTH)/2.0;
            }
        }

        x-= halfBlock;

        if (position==DoorPosition.Right||position==DoorPosition.Left) {
            y = gameValues.FIELD_Y_SPACES/2.0;
        }   else {
            if (position==DoorPosition.Below) {
                y = gameValues.FIELD_Y_SPACES + gameValues.WALL_THICKNESS/2.0 - (gameValues.WALL_THICKNESS-gameValues.DOOR_DEPTH)/2.0;
            } else {
                y = -gameValues.WALL_THICKNESS/2.0 + (gameValues.WALL_THICKNESS-gameValues.DOOR_DEPTH)/2.0;
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
                this.sizeInBlocks = new Point2D.Double(gameValues.DOOR_WIDTH, gameValues.DOOR_DEPTH);
                this.hitbox = new HitBox(gameValues.DOOR_HITBOX_WIDTH, gameValues.DOOR_HITBOX_DEPTH, 0, 0);
                break;
            case Right:
                this.sizeInBlocks = new Point2D.Double(gameValues.DOOR_DEPTH, gameValues.DOOR_WIDTH);
                this.hitbox = new HitBox(gameValues.DOOR_HITBOX_DEPTH, gameValues.DOOR_HITBOX_WIDTH, 0, 0);
                break;
            case Below:
                this.sizeInBlocks = new Point2D.Double(gameValues.DOOR_WIDTH, gameValues.DOOR_DEPTH);
                this.hitbox = new HitBox(gameValues.DOOR_HITBOX_WIDTH, gameValues.DOOR_HITBOX_DEPTH, 0, 0);
                break;
            case Left:
                this.sizeInBlocks = new Point2D.Double(gameValues.DOOR_DEPTH, gameValues.DOOR_WIDTH);
                this.hitbox = new HitBox(gameValues.DOOR_HITBOX_DEPTH, gameValues.DOOR_HITBOX_WIDTH, 0, 0);
                break;
            default:
                throw new NoSuchElementException("Unknown enum '" + position + "' given.");
        }
    }

    private void setDoorImages(Room room) {
        images = room.createDoorImage(new SpriteSheet(gameValues.DOOR_SPRITE_SHEET), this.position);
        //images.setCurrentImageIndex(1);

        /*
        switch(roomClassType) {
            case SpawnRoom.class.toString():
            case RegularRoom.class.toString():
                images = new InterchangeableImage(2, new HitBox());
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case ShopRoom.class.toString():
                images = new InterchangeableImage(3, new HitBox());
                images.setImage(2, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case SecretRoom.class.toString():
                images = new InterchangeableImage(1, new HitBox());
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 1, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case TreasureRoom.class.toString():
                images = new InterchangeableImage(3, new HitBox());
                images.setImage(2, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case BossRoom.class.toString():
                images = new InterchangeableImage(2, new HitBox());
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 3, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 3, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            case ArcadeRoom.class.toString():
                images = new InterchangeableImage(3, new HitBox());
                images.setImage(2, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(0, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                images.setImage(1, flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE))));
                break;
            default:
                throw new NoSuchElementException("Class not found!");
        }
        */
    }

    public static BufferedImage flipImagesOnPosition(BufferedImage imageToTransform, Door.DoorPosition position) {
        System.out.println("Flipping door at " + position);
        switch(position) {
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
/*
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE/2;
    }*/

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