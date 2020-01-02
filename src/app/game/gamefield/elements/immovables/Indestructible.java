package app.game.gamefield.elements.immovables;

import java.awt.geom.Point2D.Double;

import app.supportclasses.GameValues;

/**
 * Indestructible
 */
public class Indestructible extends Immovable {

    public enum Indestructibles {
        Door;
    }

    public Indestructible(GameValues gameValues, Objects object, Double location) {
        super(gameValues,  location);
        // TODO Auto-generated constructor stub
    }

    
}