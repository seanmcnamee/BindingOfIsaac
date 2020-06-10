package app.game.gamefield.house.rooms.types;

import java.awt.Point;

import app.game.gamefield.elements.immovables.doors.Door;
import app.game.gamefield.elements.immovables.doors.Door.DoorPosition;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

public class ArcadeRoom extends Room {

    public ArcadeRoom(GameValues gameValues, Drawable player, Point location) {
        super(gameValues, player, location);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void createMobiles() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void createImmovables() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setPictures(SpriteSheet icons) {
        this.roomIcon = icons.shrink(icons.grabImage(1, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
        this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
    }

    @Override
    public InterchangeableImage createDoorImage(SpriteSheet doorSprites, DoorPosition position) {
        InterchangeableImage images = new InterchangeableImage(3, new HitBox());
        
        images.setImage(2, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(0, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        images.setImage(0, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        images.setImage(1, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 4, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        
        return images;
    }
    
}