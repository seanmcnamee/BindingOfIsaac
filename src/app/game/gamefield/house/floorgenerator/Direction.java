package app.game.gamefield.house.floorgenerator;

import java.awt.Point;
/**
 * Direction
 */
public class Direction {
    private Point location;
    
    public enum Directions {
        up, down, left, right;
    }

    public Direction(Point startLocation) {
        this.location = startLocation;
    }

    public boolean oneAvailable(MutableBoolean... bools) {
        for (MutableBoolean b : bools) { 
            if (b.getBoolean()) { 
                return true;
            }
        }
        return false;
    }

    /**
     * Booleans are internally mapped to 0, 1, 2, and 3
     */
    public Point getRandomAdjacentFromAvailableAndUpdateBooleans(MutableBoolean up, MutableBoolean right, MutableBoolean down, MutableBoolean left) {
        int total = up.intVal() + right.intVal() + down.intVal() + left.intVal();
        int rawDirection = rndInRange(new Point(0, total-1));
        int properDirection = rawDirection;
        
        System.out.println("RawDir: " + rawDirection);

        boolean[] directions = {up.getBoolean(), right.getBoolean(), down.getBoolean(), left.getBoolean()};
        for (int i = 0; i <= properDirection; i++) {
            System.out.println("i = " + i + ", " + directions[i]);
            if (!directions[i]) {
                properDirection++;
            }
        }
        System.out.println("Fixed Dir: " + properDirection);

        switch(properDirection) {
            case 0:
                up.setBool(false);
                return upLocation();
            case 1:
                right.setBool(false);
                return rightLocation();
            case 2:
                down.setBool(false);
                return downLocation();
            case 3:
                left.setBool(false);
                return leftLocation();
            default:
                throw new NullPointerException("Not returning a Point. Random direction: " + rawDirection + " became: " + properDirection + " and is out of range. Booleans: " + up + "-" + right + "-" + down + "-" + left);
        }
    }

    public Point upLocation() {
        return new Point((int)location.getX(), (int)location.getY()-1);
    }

    public Point downLocation() {
        return new Point((int)location.getX(), (int)location.getY()+1);
    }

    public Point leftLocation() {
        return new Point((int)location.getX()-1, (int)location.getY());
    }

    public Point rightLocation() {
        return new Point((int)location.getX()+1, (int)location.getY());
    }

    public Point getLocation() {
        return location;
    }

    public static int rndInRange(Point range) {
        int min = (int)range.getX();
        int max = (int)range.getY();
        return (int) (Math.random()*(max-min+1)+min);
    }
}