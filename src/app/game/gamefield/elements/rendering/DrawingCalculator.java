package app.game.gamefield.elements.rendering;

import app.supportclasses.GameValues;

import java.awt.geom.Point2D;

/**
 * DrawingCalculator
 */
public class DrawingCalculator {

    public int findPixelLocation(double location, double size, double zeroLocation, double singleBlockSize) {
        return (int)( zeroLocation+((location-(size/2.0))*singleBlockSize) );
    }

    public int findPixelSize(double size, double singleBlockSize) {
        return (int)( size*singleBlockSize );
    }

    public Point2D.Double findSingleBlockSize(Point2D.Double sizeOfArea, Point2D.Double locationsInMap) {
        double xBlockSize = sizeOfArea.getX()/locationsInMap.getX();
        double YBlockSize = sizeOfArea.getY()/locationsInMap.getY();

        return new Point2D.Double(xBlockSize, YBlockSize);
    }
}