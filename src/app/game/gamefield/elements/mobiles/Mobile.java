package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.Destructible;
import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.house.Floor;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * Mobile
 * Provides background calculation methods/variables for movement
 * Deals with collisions in the 'onCollision' method
 */
public abstract class Mobile extends Destructible{
    protected double maxSpeed;
    protected double accelerationRate; //In blocks per second 
    protected Point2D.Double velocityPercent;
    protected Point accelerationPercent; //can only have values of -1, 0, and 1 (toggles the accelerationRate)
    //protected boolean colliding = false;

    /*
    public Mobile(GameValues gameValues, double x, double y) {
        this(gameValues, new Point2D.Double(x, y));
    }*/

    public Mobile(GameValues gameValues, Point2D.Double location) {
        super(gameValues, location, gameValues.MOBILE_Z);
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

    /**
     * 
     */
    protected void updateVelocity() {
        //Converts from blocks/second to blocks/tick
        final double acceleration = accelerationRate/gameValues.goalTicksPerSecond;
        final double friction = gameValues.friction/gameValues.goalTicksPerSecond;
        final double changeWhenFull = .1;

        double tempXPercent, tempYPercent;
        tempXPercent = velocityPercent.x;
        tempYPercent = velocityPercent.y;

        //Add friction and acceleration to the current velocity
        tempXPercent -= Math.signum(tempXPercent)*friction + acceleration*accelerationPercent.x;
        tempYPercent -= Math.signum(tempYPercent)*friction + acceleration*accelerationPercent.y;

        //Considers static and kinetic friction to be equal
        if (Math.abs(tempXPercent) <= friction) {
            tempXPercent = 0;
        }

        if (Math.abs(tempYPercent) <= friction) {
            tempYPercent = 0;
        }

        //When the resultant of x and y are too large, lower them both 
        //this will result in the initial direction always being slightly more than the other
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

    //Calls the Room's collisionCheck for an element, and sends it to the onCollision method
    //To deal with how the item should move
    protected void testCollisionAndMove(Floor floor) {
        Point2D.Double tempLocation = new Point2D.Double(location.getX(), location.getY());

        double maxSpeedPerTick = maxSpeed/gameValues.goalTicksPerSecond;

        tempLocation.x += velocityPercent.x*maxSpeedPerTick;
        tempLocation.y += velocityPercent.y*maxSpeedPerTick;

        Drawable collidingElement = floor.getCurrentRoom().collisionCheck(this, tempLocation);
        if (collidingElement!=null) {
            location = onCollision(tempLocation, collidingElement, floor);
        }   else {
            location = tempLocation;
        }
    }

    //TODO possibly add mass for more realizstic collisions
    //TODO add collision recoil (bounceback) if mass is added
    protected abstract Point2D.Double onCollision(Point2D.Double newLocation, Drawable collidingElement, Floor floor);

    public double getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    /**
     * A moving object should only be drawn behind something when the bottom of it's picture is completely above the top of the other.
     * Adding half the height of the mobile object achieves this (location based on center)
     */
    public int getPriority() {
        int typicalPriority = super.getPriority();
        return typicalPriority + this.zValue - (int)(.5*this.getHitBoxSizeInBlocks().getY()*10.0);
    }

    public abstract void tick(Floor f);
}