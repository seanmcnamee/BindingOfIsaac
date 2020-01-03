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
    }

    //TODO add wall space accountability
    //TODO fix this adjustment when walls are added
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

    @Override
    public int getPriority() {
        return (int)(-this.location.getY()*10.0);
    }

    //TODO allow for a dual hitbox to check against a dual hitbox
    public boolean contains(Point2D.Double testingLocation, Drawable other) {
        double leftMost = getHitBox().getLeftOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double topMost = getHitBox().getTopOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        double rightMost = getHitBox().getRightOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double bottomMost = getHitBox().getBottomOfHitBox(testingLocation.getY(), sizeInBlocks.getY());

        //TODO add more statements for each corner when checking...
        //System.out.println("Collision checking bounds: " + leftMost + ", " + topMost + " to " + rightMost + ", " + bottomMost);
        return Button.leftHandSideTest(leftMost, topMost, leftMost, bottomMost, other.getRightOfHitBox(), other.location.getY()) && //Left side of this
            Button.leftHandSideTest(leftMost, bottomMost, rightMost, bottomMost, other.location.getX(), other.getTopOfHitBox()) && //Bottom of this
            Button.leftHandSideTest(rightMost, bottomMost, rightMost, topMost, other.getLeftOfHitBox(), other.location.getX()) && //Right side of this
                Button.leftHandSideTest(rightMost, topMost, leftMost, topMost, other.location.getX(), other.getBottomOfHitBox()); //Top side of this
    }

    protected HitBox getHitBox() {
        return hitbox;
    }

    private double getRightOfHitBox() {
        return getHitBox().getRightOfHitBox(location.getX(), sizeInBlocks.getX());
    }

    private double getLeftOfHitBox() {
        return getHitBox().getLeftOfHitBox(location.getX(), sizeInBlocks.getX());
    }

    private double getTopOfHitBox() {
        return getHitBox().getTopOfHitBox(location.getY(), sizeInBlocks.getY());
    }

    private double getBottomOfHitBox() {
        return getHitBox().getBottomOfHitBox(location.getY(), sizeInBlocks.getY());
    }

    protected Point2D.Double getLocation() {
        return location;
    }

    protected Point2D.Double getSizeInBlocks() {
        return sizeInBlocks;
    }

    protected BufferedImage getImage() {
        return image;
    }

}