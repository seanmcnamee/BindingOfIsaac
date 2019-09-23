package app.supportclasses;

import java.awt.image.BufferedImage;

import java.awt.Point;
import java.awt.Graphics;

import java.awt.image.RescaleOp;

/**
 * Button
 */
public class Button{
    /**
     *
     */
    private BufferedImage image;
    private boolean isHovering;
    private Point buttonLocation, topLeftPos, bottomRightPos;
    private GameValues gameValues;

    public Button(BufferedImage i, int x, int y, GameValues gameValues) {
        image = i;
        buttonLocation = new Point(x, y);
        isHovering = false;
        this.gameValues = gameValues;
        calibrateCorners(i);
    }

    public Button(BufferedImage i, Point p, GameValues gameValues) {
        this(i, (int)p.getX(), (int)p.getY(), gameValues);
    }

    public void render(Graphics g) {
        //g.drawImage(image, pos.getX(), pos.getY(), null);
        g.drawImage(image, (int)buttonLocation.getX(), (int)buttonLocation.getY(), null);
    }

    /**
     * Figures out the top left and bottom right most pixels of the image, and uses those for .contains calculations
     */
    private void calibrateCorners(BufferedImage image) {
        int leftMost = -1;
        int rightMost = -1;
        int topMost = -1;
        int bottomMost = -1;

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
    
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

        this.topLeftPos = new Point((int)buttonLocation.getX() + leftMost, (int)buttonLocation.getY() + topMost);
        this.bottomRightPos = new Point((int)buttonLocation.getX() + rightMost, (int)buttonLocation.getY() + bottomMost);
        System.out.println(this.topLeftPos + " - " + this.bottomRightPos);
      }


    /**
     * 
     * @param other : The point to be checked
     * @return true only if 'other' is within the rectangle created by the corners of the picture
     * Uses the left Hand Side test (would work for any convex polygon)
     */
    public boolean contains(Point other) {
        return leftHandSideTest(topLeftPos.getX(), topLeftPos.getY(), bottomRightPos.getX(), topLeftPos.getY(), other.getX(), other.getY()) &&
            leftHandSideTest(bottomRightPos.getX(), topLeftPos.getY(), bottomRightPos.getX(), bottomRightPos.getY(), other.getX(), other.getY()) &&
            leftHandSideTest(bottomRightPos.getX(), bottomRightPos.getY(), topLeftPos.getX(), bottomRightPos.getY(), other.getX(), other.getY()) &&
            leftHandSideTest(topLeftPos.getX(), bottomRightPos.getY(), topLeftPos.getX(), topLeftPos.getY(), other.getX(), other.getY());
        
    }

    public boolean leftHandSideTest(double x1, double y1, double x2, double y2, double xTEST, double yTEST) {
        double D = xTEST*(y1-y2) + x1*(y2-yTEST) + x2*(yTEST-y1);

        if (D >= 0) {
            return true;
        }   else {
            return false;
        }
    }

    public void setHovering(boolean b) {
        if (b != isHovering) {
            if (b) {
                //Make image darker
                RescaleOp op = new RescaleOp(gameValues.darkenValue, 0, null);
                image = op.filter(image, null);
            }   else {
                //Make image brighter
                RescaleOp op = new RescaleOp(gameValues.lightenValue, 0, null);
                image = op.filter(image, null);
            }
            isHovering = b;
        }
        
    }

    public boolean isHovering() {
        return isHovering;
    }
}