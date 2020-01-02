package app.game.gamefield.elements.rendering;

import app.supportclasses.Button;
import app.supportclasses.GameValues;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
/**
 * BTNode
 */
public class Drawable extends Node{
    protected Point2D.Double location;
    protected BufferedImage image;
    protected Point2D.Double sizeInBlocks;
    protected GameValues gameValues;

    public Drawable(GameValues gameValues, Point2D.Double location) {
        super();
        this.gameValues = gameValues;
        this.location = location;
    }

    public void render(Graphics g) {
        //TODO add wall space accountability
        //double wallPixels = gameValues.fieldYSize*.05;
        
        //TODO fix this adjustment when walls are added
        double xZero = gameValues.fieldXStart+(gameValues.singleSquareX*.5);
        double yZero = gameValues.fieldYStart+(gameValues.singleSquareY*.5);

        double pixelXLocation = xZero+(this.location.getX()*gameValues.singleSquareX);
        double pixelYLocation = yZero+(this.location.getY()*gameValues.singleSquareY);

        double pixelXSize = gameValues.singleSquareX*sizeInBlocks.getX();
        double pixelYSize = gameValues.singleSquareY*sizeInBlocks.getY();
        //gameValues.fieldYStart+this.location.getY()*singleSquareY+wallPixels
        //double leftSide = gameValues.fieldXStart+(this.location.getX()*singleSquareX)+wallPixels;

        g.drawImage(image, (int)(pixelXLocation), (int)(pixelYLocation), (int)pixelXSize, (int)pixelYSize, null);
    }

    @Override
    public int getPriority() {
        return (int)(-this.location.getY()*10.0);
    }

    public boolean contains(Point2D.Double testingLocation, Drawable other) {
        
        double leftMost = gameValues.gameScale*(testingLocation.getX()-sizeInBlocks.getX()/2.0);
        double topMost = gameValues.gameScale*(testingLocation.getY()-sizeInBlocks.getY()/2.0);
        double rightMost = gameValues.gameScale*(testingLocation.getX()+sizeInBlocks.getX()/2.0);
        double bottomMost = gameValues.gameScale*(testingLocation.getY()+sizeInBlocks.getY()/2.0);

        //System.out.println("Collision checking bounds: " + leftMost + ", " + topMost + " to " + rightMost + ", " + bottomMost);
        return Button.leftHandSideTest(leftMost, topMost, leftMost, bottomMost, other.location.getX(), other.location.getY()) &&
            Button.leftHandSideTest(leftMost, bottomMost, rightMost, bottomMost, other.location.getX(), other.location.getY()) &&
            Button.leftHandSideTest(rightMost, bottomMost, rightMost, topMost, other.location.getX(), other.location.getY()) &&
                Button.leftHandSideTest(rightMost, topMost, leftMost, topMost, other.location.getX(), other.location.getY());
        
    }
}