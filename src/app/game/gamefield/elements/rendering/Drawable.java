package app.game.gamefield.elements.rendering;

import app.game.gamefield.elements.rendering.drawableSupport.DrawingCalculator;
import app.game.gamefield.elements.rendering.drawableSupport.HitBox;
import app.game.gamefield.elements.rendering.structure.Node;
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
    protected int zValue;
    protected BufferedImage image;
    protected Point2D.Double sizeInBlocks;
    protected HitBox hitbox;
    protected GameValues gameValues;
    protected DrawingCalculator calculator;
    protected boolean drawShadow;

    public Drawable(GameValues gameValues, Point2D.Double location, int zValue, boolean drawShadow) {
        super();
        this.gameValues = gameValues;
        this.location = location;
        this.zValue = zValue;
        this.drawShadow = drawShadow;
        this.calculator = new DrawingCalculator();
    }

    public void render(Graphics g) {
        if (shouldDrawShadow()) {
            g.setColor(gameValues.SHADOW_COLOR);
            //Shadows are drawn at the bottom of the image
            double yLocation = getLocation().getY()+((1-gameValues.SHADOW_Y*gameValues.SHADOW_Y_UNDER)*getSizeInBlocks().getY());
            g.fillOval(calculator.findPixelLocation(getLocation().getX(), getSizeInBlocks().getX(), gameValues.fieldXZero, gameValues.singleSquareX), 
                        calculator.findPixelLocation(yLocation, getSizeInBlocks().getY(), gameValues.fieldYZero, gameValues.singleSquareY), 
                        calculator.findPixelSize(getSizeInBlocks().getX()*gameValues.SHADOW_X, gameValues.singleSquareX), 
                        calculator.findPixelSize(getSizeInBlocks().getY()*gameValues.SHADOW_Y, gameValues.singleSquareY));
        }

        g.drawImage(getImage(), calculator.findPixelLocation(getLocation().getX(), getSizeInBlocks().getX(), gameValues.fieldXZero, gameValues.singleSquareX), 
                                calculator.findPixelLocation(getLocation().getY(), getSizeInBlocks().getY(), gameValues.fieldYZero, gameValues.singleSquareY), 
                                calculator.findPixelSize(getSizeInBlocks().getX(), gameValues.singleSquareX), 
                                calculator.findPixelSize(getSizeInBlocks().getY(), gameValues.singleSquareY), 
                                null);
        
        if (gameValues.debugMode) {
            g.drawRect(calculator.findPixelLocation(getHitBoxLocation().getX(), getHitBoxSizeInBlocks().getX(), gameValues.fieldXZero, gameValues.singleSquareX), 
                        calculator.findPixelLocation(getHitBoxLocation().getY(), getHitBoxSizeInBlocks().getY(), gameValues.fieldYZero, gameValues.singleSquareY), 
                        calculator.findPixelSize(getHitBoxSizeInBlocks().getX(), gameValues.singleSquareX), 
                        calculator.findPixelSize(getHitBoxSizeInBlocks().getY(), gameValues.singleSquareY));
        }

    }

    protected boolean shouldDrawShadow() {
        return this.drawShadow;
    }

    @Override
    public int getPriority() {
        return -(int)(this.location.getY()*10.0 + this.zValue);
    }

    //TODO allow for a dual hitbox to check against a dual hitbox
    public boolean contains(Point2D.Double testingLocation, Drawable other) {
        /*
        double leftMost = getHitBox().getLeftOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double topMost = getHitBox().getTopOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        double rightMost = getHitBox().getRightOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double bottomMost = getHitBox().getBottomOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        */

        // System.out.println(".Contains for " + this + " vs " + other);
        // System.out.println(other.location);
        // System.out.println(other.sizeInBlocks);

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

    protected void printGenerationDebug(String toPrint) {
        if (gameValues.generationDebugMode) {
            System.out.println(toPrint);
        }
    }

    protected void printDebug(String toPrint) {
        if (gameValues.debugMode) {
            System.out.println(toPrint);
        }
    }

}