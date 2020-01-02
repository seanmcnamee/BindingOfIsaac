package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.rendering.BST;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.rooms.Room;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * Entity
 */
public abstract class Mobile extends Drawable{
    protected double maxSpeed;
    protected double accelerationRate; //In blocks per second
    protected Point2D.Double velocityPercent;
    protected Point accelerationPercent;

    /*
    public Mobile(GameValues gameValues, double x, double y) {
        this(gameValues, new Point2D.Double(x, y));
    }*/

    public Mobile(GameValues gameValues, Point2D.Double location) {
        super(gameValues, location);
        velocityPercent = new Point2D.Double();
        accelerationPercent = new Point();
    }

    public void accelerate(boolean up, boolean down, boolean left, boolean right) {
        accelerate(up?1:0, down?-1:0, left?1:0, right?-1:0);
    }

    public void accelerate(int up, int down, int left, int right) {
        accelerate(up+down, left+right);
    }

    public void accelerate(int yAcc, int xAcc) {
        accelerationPercent.x = (int)Math.signum(xAcc);
        accelerationPercent.y = (int)Math.signum(yAcc);
        //System.out.println("Accelerated... x: " + accelerationPercent.x + ", y: " + accelerationPercent.y);
    }

    protected void updateVelocity() {
        final double acceleration = accelerationRate/gameValues.goalTicksPerSecond;
        final double friction = gameValues.friction/gameValues.goalTicksPerSecond;
        final double changeWhenFull = .1;

        double tempXPercent, tempYPercent;
        tempXPercent = velocityPercent.x;
        tempYPercent = velocityPercent.y;

        tempXPercent -= Math.signum(tempXPercent)*friction + acceleration*accelerationPercent.x;
        tempYPercent -= Math.signum(tempYPercent)*friction + acceleration*accelerationPercent.y;

        if (Math.abs(tempXPercent) <= friction) {
            tempXPercent = 0;
        }

        if (Math.abs(tempYPercent) <= friction) {
            tempYPercent = 0;
        }

        if (Math.sqrt((Math.pow(tempXPercent, 2) + Math.pow(tempYPercent, 2))) <= 1+changeWhenFull) {
            velocityPercent.x = tempXPercent;
            velocityPercent.y = tempYPercent;
        }   else {
            //Lower both?
            velocityPercent.x *=(1-changeWhenFull);
            velocityPercent.y *=(1-changeWhenFull);
        }

        //System.out.println("Velocity... x: " + velocityPercent.getX() + ", y: " + velocityPercent.getY());
    }

    protected void testCollisionAndMove(Room room) {
        Point2D.Double tempLocation = new Point2D.Double(location.getX(), location.getY());

        double maxSpeedPerTick = maxSpeed/60.0;

        tempLocation.x += velocityPercent.x*maxSpeedPerTick;
        tempLocation.y += velocityPercent.y*maxSpeedPerTick;

        //TODO test recursive version
        Drawable collidingElement = room.checkCollisions(this, tempLocation);
        if (collidingElement!=null) {
            onCollision(tempLocation, collidingElement, room);
        }

        location = tempLocation;
    }

    //TODO possibly add mass for more realizstic collisions
    //TODO add collision recoil (bounceback) if mass is added
    protected abstract void onCollision(Point2D.Double newLocation, Drawable collidingElement, Room r);

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public abstract void tick(Room r);
}