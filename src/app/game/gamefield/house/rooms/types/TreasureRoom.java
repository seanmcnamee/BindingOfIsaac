package app.game.gamefield.house.rooms.types;

import app.game.gamefield.elements.immovables.Degradable;
import app.game.gamefield.elements.immovables.Degradable.Degradables;
import app.game.gamefield.elements.immovables.doors.Door;
import app.game.gamefield.elements.immovables.doors.Door.DoorPosition;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * Locked in every except Basement1 TreasureRoom: Never border more than one
 * regular/spawn room Can have an adjacent secret room
 */
public class TreasureRoom extends Room {

    public TreasureRoom(GameValues gameValues, Drawable player, Point location) {
        super(gameValues, player, location);
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

    @Override
    protected void setPictures(SpriteSheet icons) {
        this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
        this.roomIcon = icons.shrink(icons.grabImage(0, 0, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
    }

    @Override
    public InterchangeableImage createDoorImage(SpriteSheet doorSprites, DoorPosition position) {
        InterchangeableImage images = new InterchangeableImage(3, new HitBox());

        images.setImage(0, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        images.setImage(0, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        images.setImage(1, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 2, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        
        images.setCurrentImageIndex(1);
        
        return images;
    }

}