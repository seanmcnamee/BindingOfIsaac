package app.game.gamefield.house.rooms;

import java.awt.image.BufferedImage;
import java.awt.Point;
/**
 * Traversable
 */
public abstract class Traversable {
    protected BufferedImage roomIcon;
    private boolean explored;
    public Traversable above, below, left, right;
    private Point location;

    public Traversable(Point location, Traversable above, Traversable below, Traversable left, Traversable right) {
        this.location = location;
        this.above = above;
        this.below = below;
        this.left = left;
        this.right = right;
        this.explored = false;
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
        return new Point((int)(location.getX()), (int)(location.getY()-1));
    }

    public Point getLocationBelow() {
        return new Point((int)(location.getX()), (int)(location.getY()+1));
    }

    public Point getLocationLeft() {
        return new Point((int)(location.getX()-1), (int)(location.getY()));
    }

    public Point getLocationRight() {
        return new Point((int)(location.getX()+1), (int)(location.getY()));
    }
    
}