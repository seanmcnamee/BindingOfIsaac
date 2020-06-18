package app.game.gamefield.house.rooms.types;

import app.game.gamefield.elements.immovables.Degradable;
import app.game.gamefield.elements.immovables.Degradable.Degradables;
import app.game.gamefield.elements.immovables.doors.Door;
import app.game.gamefield.elements.immovables.doors.Door.DoorPosition;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.drawableSupport.HitBox;
import app.game.gamefield.elements.rendering.drawableSupport.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * SpawnRoom adjacent to 3 or 4 rooms entrances to/from never have rocks/gaps in
 * the way
 */
public class SecretRoom extends Room {

    public SecretRoom(GameValues gameValues, Drawable player, Point location) {
        super(gameValues, player, location);
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

    @Override
    protected void setPictures(SpriteSheet icons) {
        this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
        this.roomIcon = icons.shrink(icons.grabImage(0, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE)); // TODO
                                                                                                          // Auto-generated
                                                                                                          // method stub
    }

    @Override
    public InterchangeableImage createDoorImage(SpriteSheet doorSprites, DoorPosition position) {
        InterchangeableImage images = new InterchangeableImage(1, new HitBox());
        
        images.setImage(0, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 1, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        
        return images;
    }

}