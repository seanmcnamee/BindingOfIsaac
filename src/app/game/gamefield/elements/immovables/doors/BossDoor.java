package app.game.gamefield.elements.immovables.doors;

import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
import java.util.NoSuchElementException;

/**
 * Has to worry about a weird sizing/hitbox for the open door with shadow
 */
public class BossDoor extends Door {

    public BossDoor(GameValues gameValues, Room room, DoorPosition position) {
        super(gameValues, calculateLocation(position, gameValues, gameValues.DOOR_BOSS_OPEN_DEPTH_OFFSET), room, position);
    }

    @Override
    protected void setDoorSizeAndHitBox() {
        System.out.println("Setting size/hitbox for " + this.position);
        double blockDepth, hitBoxDepth, hitBoxDepthOffset;
        if (this.doorState == DoorState.Open) {
            blockDepth = gameValues.DOOR_BOSS_OPEN_DEPTH;
            hitBoxDepth = gameValues.DOOR_BOSS_OPEN_HITBOX_DEPTH;
            hitBoxDepthOffset = gameValues.DOOR_BOSS_OPEN_HITBOX_DEPTH_OFFSET;
        }   else {
            //Closed (but not locked)
            blockDepth = gameValues.DOOR_BOSS_CLOSED_DEPTH;
            hitBoxDepth = gameValues.DOOR_HITBOX_DEPTH;
            hitBoxDepthOffset = gameValues.DOOR_HITBOX_DEPTH_OFFSET;
        }

        switch(this.position) {
            case Top:
                this.sizeInBlocks = new Point2D.Double(gameValues.DOOR_WIDTH, blockDepth);
                this.hitbox = new HitBox(gameValues.DOOR_HITBOX_WIDTH, hitBoxDepth, 0, -hitBoxDepthOffset);
                break;
            case Right:
                this.sizeInBlocks = new Point2D.Double(blockDepth, gameValues.DOOR_WIDTH);
                this.hitbox = new HitBox(hitBoxDepth, gameValues.DOOR_HITBOX_WIDTH, hitBoxDepthOffset, 0);
                break;
            case Below:
                this.sizeInBlocks = new Point2D.Double(gameValues.DOOR_WIDTH, blockDepth);
                this.hitbox = new HitBox(gameValues.DOOR_HITBOX_WIDTH, hitBoxDepth, 0, hitBoxDepthOffset);
                break;
            case Left:
                this.sizeInBlocks = new Point2D.Double(blockDepth, gameValues.DOOR_WIDTH);
                this.hitbox = new HitBox(hitBoxDepth, gameValues.DOOR_HITBOX_WIDTH, -hitBoxDepthOffset, 0);
                break;
            default:
                throw new NoSuchElementException("Unknown enum '" + position + "' given.");
        }
    }

    @Override
    public void open() {
        super.open();
        this.location = calculateLocation(position, gameValues, gameValues.DOOR_BOSS_OPEN_DEPTH_OFFSET);
        setDoorSizeAndHitBox();
    }

    @Override
    public void unlock() {
        super.unlock();
        if (this.doorState == DoorState.Closed) {
            this.location = calculateLocation(position, gameValues, gameValues.DOOR_DEPTH_OFFSET);
            setDoorSizeAndHitBox();
        }
    }
    
}