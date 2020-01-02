package app.game.gamefield.elements.immovables;

import java.awt.geom.Point2D.Double;

import app.game.gamefield.elements.Destroyable;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

/**
 * Immovable
 */
public abstract class Immovable extends Drawable {

    public Immovable(GameValues gameValues, Double location) {
        super(gameValues, location);
    }

}