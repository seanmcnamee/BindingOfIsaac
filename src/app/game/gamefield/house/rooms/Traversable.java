package app.game.gamefield.house.rooms;

import java.awt.image.BufferedImage;
import java.io.InvalidClassException;
import java.awt.Point;

/**
 * Traversable
 */
public class Traversable {
    protected BufferedImage roomIcon;
    private boolean explored;
    private Traversable above, below, left, right;
    private boolean surroundingLocked = false;
    private Point location;

    public Traversable(Point location) {
        this.location = location;
        this.explored = false;
        this.surroundingLocked = false;
    }

    public Traversable(Point location, Traversable above, Traversable below, Traversable left, Traversable right) {
        this.location = location;
        this.explored = false;
        setSurroundingAndLock(above, below, left, right);
        this.surroundingLocked = true;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored() {
        explored = true;
    }

    public Point getLocation() {
        return location;
    }

    public Point getLocationAbove() {
        return new Point((int) (location.getX()), (int) (location.getY() - 1));
    }

    public Point getLocationBelow() {
        return new Point((int) (location.getX()), (int) (location.getY() + 1));
    }

    public Point getLocationLeft() {
        return new Point((int) (location.getX() - 1), (int) (location.getY()));
    }

    public Point getLocationRight() {
        return new Point((int) (location.getX() + 1), (int) (location.getY()));
    }

    public BufferedImage getIcon() {
        return roomIcon;
    }

    public Traversable getAbove() {
        return above;
    }

    public Traversable getBelow() {
        return below;
    }

    public Traversable getLeft() {
        return left;
    }

    public Traversable getRight() {
        return right;
    }

    public void setSurroundingAndLock(Traversable above, Traversable below, Traversable left, Traversable right) {
        if (!surroundingLocked) {
            this.above = above;
            this.below = below;
            this.left = left;
            this.right = right;
            surroundingLocked = true;
        }   else {
            try {
                throw new InvalidClassException("Can't change surrounding rooms once they are locked!");
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isLocked() {
        return surroundingLocked;
    }

}