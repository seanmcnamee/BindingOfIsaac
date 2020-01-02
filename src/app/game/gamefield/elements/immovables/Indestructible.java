package app.game.gamefield.elements.immovables;

import java.awt.geom.Point2D;

import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;

/**
 * Indestructible
 */
public class Indestructible extends Drawable {

    public enum Indestructibles {
        Door;
    }

    public Indestructible(GameValues gameValues, Indestructibles object, Point2D.Double location) {
        super(gameValues, location);
        // TODO Auto-generated constructor stub
    }

    
}