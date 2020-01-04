package app.game.gamefield.elements.rendering;

import app.supportclasses.Button;
import app.supportclasses.GameValues;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

/**
 * BTNode
 * 
 *
 */
public class Drawable extends Node {
    protected Point2D.Double location;
    protected BufferedImage image;
    protected Point2D.Double sizeInBlocks;
    protected HitBox hitbox;
    protected GameValues gameValues;

    public Drawable(GameValues gameValues, Point2D.Double location) {
        super();
        this.gameValues = gameValues;
        this.location = location;
    }

    public void render(Graphics g) {
        g.drawImage(getImage(), (int)(findPixelXLocation()), (int)(findPixelYLocation()), (int)findPixelXSize(), (int)findPixelYSize(), null);
        g.drawRect((int)findHitBoxPixelXLocation(), (int)findHitBoxPixelYLocation(), (int)findHitBoxPixelXSize(), (int)findHitBoxPixelYSize());
    }

    private double findPixelXLocation() {
        return gameValues.fieldXZero+((this.getLocation().getX()-this.getSizeInBlocks().getX()/2.0)*gameValues.singleSquareX);
    }

    private double findPixelYLocation() {
        return gameValues.fieldYZero+((this.getLocation().getY()-this.getSizeInBlocks().getY()/2.0)*gameValues.singleSquareY);
    }

    private double findPixelXSize() {
        return gameValues.singleSquareX*this.getSizeInBlocks().getX();
    }

    private double findPixelYSize() {
        return gameValues.singleSquareY*this.getSizeInBlocks().getY();
    }

    private double findHitBoxPixelXLocation() {
        return gameValues.fieldXZero+((this.getHitBoxLocation().getX()-this.getHitBoxSizeInBlocks().getX()/2.0)*gameValues.singleSquareX);
    }

    private double findHitBoxPixelYLocation() {
        return gameValues.fieldYZero+((this.getHitBoxLocation().getY()-this.getHitBoxSizeInBlocks().getY()/2.0)*gameValues.singleSquareY);
    }

    private double findHitBoxPixelXSize() {
        return gameValues.singleSquareX*this.getHitBoxSizeInBlocks().getX();
    }

    private double findHitBoxPixelYSize() {
        return gameValues.singleSquareY*this.getHitBoxSizeInBlocks().getY();
    }

    @Override
    public int getPriority() {
        return (int)(-this.location.getY()*10.0);
    }

    //TODO allow for a dual hitbox to check against a dual hitbox
    public boolean contains(Point2D.Double testingLocation, Drawable other) {
        /*
        double leftMost = getHitBox().getLeftOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double topMost = getHitBox().getTopOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        double rightMost = getHitBox().getRightOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double bottomMost = getHitBox().getBottomOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        */

        double otherLeftMost = other.getHitBox().getLeftOfHitBox(other.location.getX(), other.sizeInBlocks.getX());
        double otherTopMost = other.getHitBox().getTopOfHitBox(other.location.getY(), other.sizeInBlocks.getY());
        double otherRightMost = other.getHitBox().getRightOfHitBox(other.location.getX(), other.sizeInBlocks.getX());
        double otherBottomMost = other.getHitBox().getBottomOfHitBox(other.location.getY(), other.sizeInBlocks.getY());

        double leftMost = getHitBox().getLeftOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double topMost = getHitBox().getTopOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        double rightMost = getHitBox().getRightOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double bottomMost = getHitBox().getBottomOfHitBox(testingLocation.getY(), sizeInBlocks.getY());

        Point2D.Double center = getHitBox().getCenterOfHitBox(testingLocation, sizeInBlocks);

        //TODO add more statements for each corner when checking...
        //System.out.println("Collision checking bounds: " + leftMost + ", " + topMost + " to " + rightMost + ", " + bottomMost);

        return Button.leftHandSideTest(otherLeftMost, otherTopMost, otherLeftMost, otherBottomMost, rightMost, center.getY()) && //Left side of other
            Button.leftHandSideTest(otherLeftMost, otherBottomMost, otherRightMost, otherBottomMost, center.getX(), topMost) && //Bottom of other
            Button.leftHandSideTest(otherRightMost, otherBottomMost, otherRightMost, otherTopMost, leftMost, center.getY()) && //Right side of other
                Button.leftHandSideTest(otherRightMost, otherTopMost, otherLeftMost, otherTopMost, center.getX(), bottomMost); //Top side of other


        /*
        return Button.leftHandSideTest(leftMost, topMost, leftMost, bottomMost, other.location.getX(), other.location.getY()) && //Left side of this
            Button.leftHandSideTest(leftMost, bottomMost, rightMost, bottomMost, other.location.getX(), other.location.getY()) && //Bottom of this
            Button.leftHandSideTest(rightMost, bottomMost, rightMost, topMost, other.location.getX(), other.location.getX()) && //Right side of this
                Button.leftHandSideTest(rightMost, topMost, leftMost, topMost, other.location.getX(), other.location.getY()); //Top side of this
        */
    }

    protected HitBox getHitBox() {
        return hitbox;
    }

    protected Point2D.Double getLocation() {
        return location;
    }

    protected Point2D.Double getSizeInBlocks() {
        return sizeInBlocks;
    }

    protected Point2D.Double getHitBoxLocation() {
        return getHitBox().getCenterOfHitBox(location, sizeInBlocks);
    }

    protected Point2D.Double getHitBoxSizeInBlocks() {
        return getHitBox().getHitBoxSize(sizeInBlocks);
    }

    protected BufferedImage getImage() {
        return image;
    }

}