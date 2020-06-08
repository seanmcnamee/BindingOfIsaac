package app.game.gamefield.elements.immovables.walls;

import java.awt.geom.Point2D.Double;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.elements.rendering.HitBox;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

/**
 * Wall
 */
public class Wall extends Drawable {

    public enum WallType {
        Top, Bottom, Left, Right;
    }

    public Wall(GameValues gameValues, WallType wallType) {
        super(gameValues, Wall.loadWallLocation(gameValues, wallType), gameValues.TOP_BOTTOM_WALL_Z);
        loadWallPictureAndSize(wallType);
        this.hitbox = new HitBox();
    }

    private void loadWallPictureAndSize(WallType wall) {
        SpriteSheet ss = new SpriteSheet(gameValues.GAME_WALL_SPRITESHEET);
        final double longX = gameValues.FIELD_X_SPACES+2*gameValues.WALL_THICKNESS;
        final double longY = gameValues.FIELD_Y_SPACES+3*gameValues.WALL_THICKNESS;

        switch(wall) {
            case Top:
                this.image = ss.shrink(ss.grabImage(1, 0, 1, 1, gameValues.WALL_SPRITESHEET_SIZE));
                this.sizeInBlocks = new Double(longX, gameValues.WALL_THICKNESS);
                break;
            case Bottom:
                this.image = ss.flipTopBottom(ss.shrink(ss.grabImage(1, 0, 1, 1, gameValues.WALL_SPRITESHEET_SIZE)));
                this.sizeInBlocks = new Double(longX, gameValues.WALL_THICKNESS);
                break;
            case Left:
                this.image = ss.flipLeftRight(ss.shrink(ss.grabImage(0, 0, 1, 1, gameValues.WALL_SPRITESHEET_SIZE)));
                this.sizeInBlocks = new Double(gameValues.WALL_THICKNESS, longY);
                this.zValue = gameValues.SIDE_WALL_Z;
                break;
            case Right:
            default:
                this.image = ss.shrink(ss.grabImage(0, 0, 1, 1, gameValues.WALL_SPRITESHEET_SIZE));
                this.sizeInBlocks = new Double(gameValues.WALL_THICKNESS, longY);
                this.zValue = gameValues.SIDE_WALL_Z;
                
                break;
        }
    }

    private static Double loadWallLocation(GameValues gameValues, WallType wall) {
        final double half_block = .5;
        switch(wall) {
            case Top:
                return new Double(gameValues.FIELD_X_SPACES/2.0 - half_block, -(half_block+gameValues.WALL_THICKNESS/2.0));
            case Bottom:
                return new Double(gameValues.FIELD_X_SPACES/2.0 - half_block, half_block+gameValues.FIELD_Y_SPACES-gameValues.WALL_THICKNESS/2.0);
            case Left:
                return new Double(-(half_block+gameValues.WALL_THICKNESS/2.0), gameValues.FIELD_Y_SPACES/2.0 - half_block);
            case Right:
            default:
                return new Double(half_block+gameValues.FIELD_X_SPACES-gameValues.WALL_THICKNESS/2.0, gameValues.FIELD_Y_SPACES/2.0 - half_block);
        }
    }
/*
    @Override
    public int getPriority() {
        return this.drawPriority;
    }*/
}