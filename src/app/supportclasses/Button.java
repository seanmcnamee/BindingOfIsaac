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
        image = shrink(i);
    }

    public Button(BufferedImage i, int x, int y, GameValues gameValues) {
        this(i, new Point(x, y), gameValues);
    }

    public void render(Graphics g) {
        double leftMost = pictureCenterLocation.getX()-image.getWidth()/2.0;
        double topMost = pictureCenterLocation.getY()-image.getHeight()/2.0;

        g.drawImage(image, (int)(leftMost*gameValues.SCALE), (int)(topMost*gameValues.SCALE), image.getWidth()*gameValues.SCALE, image.getHeight()*gameValues.SCALE, null);
    }

    /**
     * Creates and returns the smallest image containing all non-transparents pixels
     * @param image
     */
    private BufferedImage shrink(BufferedImage image) {
        int leftMost = -1;
        int rightMost = -1;
        int topMost = -1;
        int bottomMost = -1;

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
    
        //Go through array and find corners of smaller inside picture
        for (int y = 0; y < imgHeight; y++) {
          for (int x = 0; x < imgWidth; x++) {

            int pixel = image.getRGB(x, y);
            if (((pixel >> 24) & 0xff) != 0) {
                if (leftMost == -1 || leftMost > x) {
                    leftMost = x;
                }
                if (rightMost < x) {
                    rightMost = x;
                }

                if (topMost == -1 || leftMost > y) {
                    topMost = y;
                }
                if (bottomMost < y) {
                    bottomMost = y;
                }
            }
          }
        }


        //Create new, smaller BufferedImage
        int width = rightMost-leftMost;
        int height = bottomMost-topMost;
        BufferedImage smallerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                smallerImage.setRGB(x, y, image.getRGB(x+leftMost, y+topMost));
            }
        }

        return smallerImage;
    }

    /**
     * 
     * @param other : The point to be checked
     * @return true only if 'other' is within the rectangle created by the corners of the picture
     * Uses the left Hand Side test (would work for any convex polygon)
     */
    public boolean contains(Point other) {
        
        double leftMost = pictureCenterLocation.getX()-image.getWidth()/2.0;
        double topMost = pictureCenterLocation.getY()-image.getHeight()/2.0;
        double rightMost = pictureCenterLocation.getX()+image.getWidth()/2.0;
        double bottomMost = pictureCenterLocation.getY()+image.getHeight()/2.0;

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