package app.game.gamefield.elements.rendering;

import app.supportclasses.Button;
import app.supportclasses.GameValues;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
/**
 * BTNode
 */
public abstract class Drawable extends Node{
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
        double wallPixels = gameValues.fieldYSize*.05;
        double singleSquareX = (gameValues.fieldXSize-2*wallPixels)/gameValues.XSpaces;
        double singleSquareY = (gameValues.fieldYSize-2*wallPixels)/gameValues.YSpaces;
        
        double xZero = wallPixels+gameValues.fieldXStart+singleSquareX/2.0;
        double yZero = wallPixels+gameValues.fieldYStart+singleSquareY/2.0;

        double pixelXLocation = xZero+(this.location.getX()*singleSquareX);
        double pixelYLocation = yZero+(this.location.getY()*singleSquareY);

        double pixelXSize = singleSquareX*sizeInBlocks.getX();
        double pixelYSize = singleSquareY*sizeInBlocks.getY();
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