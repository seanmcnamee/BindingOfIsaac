package app.game.gamefield.house.rooms.types;

import app.game.gamefield.elements.immovables.doors.Door;
import app.game.gamefield.elements.immovables.doors.Door.DoorPosition;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.game.gamefield.elements.rendering.InterchangeableImage;
import app.game.gamefield.house.rooms.Room;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * SpawnRoom
 */
public class SpawnRoom extends Room {
    private BufferedImage directions;
    private boolean directionsNeeded;

    public SpawnRoom(GameValues gameValues, Drawable player, boolean directionsAreNeeded, Point location) {
        super(gameValues, player, location);
        this.directionsNeeded = directionsAreNeeded;
        if (this.directionsNeeded) {
            directions = new BufferedImageLoader(gameValues.STARTING_DIRECTIONS_FILE).getImage();
        }
    }

    @Override
    protected void createMobiles() {
        // No enemies
    }

    @Override
    protected void createImmovables() {
        // No objects in room
    }

    @Override
    protected void setPictures(SpriteSheet icons) {
        this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
        this.roomIcon = icons.shrink(icons.grabImage(1, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
    }

    @Override
    protected void drawBackground(Graphics g) {
        super.drawBackground(g);
        if (directionsNeeded) {
            int xSize = (int) (gameValues.fieldXSize * gameValues.DIRECTIONS_WIDTH);
            int ySize = (int) (gameValues.fieldYSize * gameValues.DIRECTIONS_HEIGHT);

            double xOffsetPercent = (gameValues.DIRECTIONS_X_PERCENT_LOCATION - ((gameValues.DIRECTIONS_WIDTH) / 2.0));
            double yOffsetPercent = (gameValues.DIRECTIONS_Y_PERCENT_LOCATION - ((gameValues.DIRECTIONS_HEIGHT) / 2.0));
            int xPos = (int) (gameValues.fieldXStart + (gameValues.fieldXSize * xOffsetPercent));
            int yPos = (int) (gameValues.fieldYStart + (gameValues.fieldYSize * yOffsetPercent));

            g.drawImage(this.directions, xPos, yPos, xSize, ySize, null);
        }
    }

    @Override
    public InterchangeableImage createDoorImage(SpriteSheet doorSprites, DoorPosition position) {
        InterchangeableImage images = new InterchangeableImage(2, new HitBox());
        images.setImage(0, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(1, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        images.setImage(1, Door.flipImagesOnPosition(doorSprites.shrink(doorSprites.grabImage(2, 0, 1, 1, gameValues.DOOR_SPRITE_SHEET_BOX_SIZE)), position));
        
        images.setCurrentImageIndex(1);
        
        return images;
    }

}