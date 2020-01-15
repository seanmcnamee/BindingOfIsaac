package app.game.gamefield.house.rooms;

import java.awt.image.BufferedImage;
import java.io.InvalidClassException;
import java.awt.Point;

/**
 * Traversable
 */
public abstract class Traversable {
    protected BufferedImage roomIcon;

    public enum Status {
        UNEXPLORED, SEEN, EXPLORED;
    }
    private Status roomStatus;
    private Traversable above, below, left, right;
    private boolean surroundingLocked = false;
    private Point location;

    public Traversable(Point location) {
        this.location = location;
        this.roomStatus = Status.UNEXPLORED;
        this.surroundingLocked = false;
    }

    /*
    public Traversable(Point location, Traversable above, Traversable below, Traversable left, Traversable right) {
        this.location = location;
        this.roomStatus = false;
        setSurroundingAndLock(above, below, left, right);
        this.surroundingLocked = true;
    }*/

    protected abstract void createDoors();

    public boolean isSeen() {
        return this.roomStatus == Status.SEEN;
    }

    public boolean isExplored() {
        return this.roomStatus == Status.EXPLORED;
    }

    public void setExplored() {
        this.roomStatus = Status.EXPLORED;
        System.out.println("Setting surrounding rooms as seen");
        setSurroundingSeen();
    }

    private void setSurroundingSeen() {
        if (above!=null) {
            System.out.println("\tAbove");
            above.setSeen();
        }
        if (right!=null) {
            System.out.println("\tRight");
            right.setSeen();
        }
        if (below!=null) {
            System.out.println("\tBelow");
            below.setSeen();
        }
        if (left!=null) {
            System.out.println("\tLeft");
            left.setSeen();
        }
    }

    private void setSeen() {
        System.out.println("Setting as seen");
        if (this.roomStatus==Status.UNEXPLORED) {
            System.out.println("No, Really!");
            this.roomStatus = Status.SEEN;
        }
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
            createDoors();
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