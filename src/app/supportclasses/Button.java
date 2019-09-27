package app.supportclasses;

import java.awt.image.BufferedImage;

import java.awt.Point;
import java.awt.Graphics;

import java.awt.image.RescaleOp;

/**
 * Button
 */
public class Button {
    /**
     *
     */
    private BufferedImage image;
    private boolean isHovering;
    private Point pictureCenterLocation;
    private GameValues gameValues;

    /**
     * This point will be the CENTER of the button (so other things don't have to figure it out)
     */
    public Button(BufferedImage i, Point p, GameValues gameValues) {
        isHovering = false;
        this.gameValues = gameValues;
        pictureCenterLocation = p;
        image = i;
    }

    public Button(BufferedImage i, int x, int y, GameValues gameValues) {
        this(i, new Point(x, y), gameValues);
    }

    public void render(Graphics g) {
        double leftMost = pictureCenterLocation.getX()-image.getWidth()/2.0;
        double topMost = pictureCenterLocation.getY()-image.getHeight()/2.0;

        g.drawImage(image, (int)(leftMost*gameValues.gameScale), (int)(topMost*gameValues.gameScale), (int)(image.getWidth()*gameValues.gameScale), (int)(image.getHeight()*gameValues.gameScale), null);
    }

    /**
     * 
     * @param other : The point to be checked
     * @return true only if 'other' is within the rectangle created by the corners of the picture
     * Uses the left Hand Side test (would work for any convex polygon)
     */
    public boolean contains(Point other) {
        
        double leftMost = gameValues.gameScale*(pictureCenterLocation.getX()-image.getWidth()/2.0);
        double topMost = gameValues.gameScale*(pictureCenterLocation.getY()-image.getHeight()/2.0);
        double rightMost = gameValues.gameScale*(pictureCenterLocation.getX()+image.getWidth()/2.0);
        double bottomMost = gameValues.gameScale*(pictureCenterLocation.getY()+image.getHeight()/2.0);

        //System.out.println("Collision checking bounds: " + leftMost + ", " + topMost + " to " + rightMost + ", " + bottomMost);
        return leftHandSideTest(leftMost, topMost, leftMost, bottomMost, other.getX(), other.getY()) &&
            leftHandSideTest(leftMost, bottomMost, rightMost, bottomMost, other.getX(), other.getY()) &&
            leftHandSideTest(rightMost, bottomMost, rightMost, topMost, other.getX(), other.getY()) &&
            leftHandSideTest(rightMost, topMost, leftMost, topMost, other.getX(), other.getY());
        
    }

    public boolean leftHandSideTest(double x1, double y1, double x2, double y2, double xTEST, double yTEST) {
        double D = xTEST*(y1-y2) + x1*(y2-yTEST) + x2*(yTEST-y1);
        //System.out.println("Collision checking side: " + x1 + ", " + y1 + " to " + x2 + ", " + y2 + " for: " + xTEST + ", " + yTEST);
        if (D <= 0) {
            //System.out.println("On the correct side");
            return true;
        }   else {
            //System.out.println("Wrong side!");
            return false;
        }
    }

    public void setHovering(boolean b) {
        if (b != isHovering) {
            if (b) {
                //Make image darker
                RescaleOp op = new RescaleOp(gameValues.DARKEN_VALUE, 0, null);
                image = op.filter(image, null);
            }   else {
                //Make image brighter
                RescaleOp op = new RescaleOp(gameValues.LIGHTEN_VALUE, 0, null);
                image = op.filter(image, null);
            }
            isHovering = b;
        }
        
    }

    public boolean isHovering() {
        return isHovering;
    }
}