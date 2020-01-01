package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Entity
 */
public abstract class Mobile extends Drawable{
    protected int maxSpeed;
    protected Point2D.Double velocityPercent;
    protected Point accelerationPercent;

    public Mobile(GameValues gameValues, double x, double y) {
        this(gameValues, new Point2D.Double(x, y));
    }

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
        accelerationPercent.x = (int)Math.signum(xAcc);//(xAcc==0)? 0:(xAcc/Math.abs(xAcc));
        accelerationPercent.y = (int)Math.signum(yAcc);//(yAcc==0)? 0:(yAcc/Math.abs(yAcc));
        System.out.println("Accelerated... x: " + accelerationPercent.x + ", y: " + accelerationPercent.y);
    }

    protected void updateVelocity() {
        final double acceleration = 6.0/60.0;//How long should it take to accelerate to full? 1/2 a second... so it should double in a second
        final double friction = 2.0/60.0;
        final double changeWhenFull = .1;
        //final double staticFriction = .01;

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

        //TODO when adding a second direction, let that one's power become equal to the current

        //Allow for friction to stop motion...
        /*
        if (accelerationPercent.getX()==0 && Math.signum(tempXPercent)!=Math.signum(velocityPercent.getX())) {
            tempXPercent = 0;
        }
        if (accelerationPercent.getY()==0 && Math.signum(tempYPercent)!=Math.signum(velocityPercent.getY())) {
            tempYPercent = 0;
        }
        */

        if (Math.sqrt((Math.pow(tempXPercent, 2) + Math.pow(tempYPercent, 2))) <= 1+changeWhenFull) {
            velocityPercent.x = tempXPercent;
            velocityPercent.y = tempYPercent;
        }   else {
            //Lower both?
            velocityPercent.x *=(1-changeWhenFull);
            velocityPercent.y *=(1-changeWhenFull);
        }

        System.out.println("Velocity... x: " + velocityPercent.getX() + ", y: " + velocityPercent.getY());
    }

    protected void move() {
        Point2D.Double tempLocation = new Point2D.Double(location.getX(), location.getY());

        double maxSpeedPerTick = maxSpeed/60.0;

        tempLocation.x += velocityPercent.x*maxSpeedPerTick;
        tempLocation.y += velocityPercent.y*maxSpeedPerTick;

        //TODO add collision check
        //(possibly a method that everything that extends has to make? -abstract method?)
        if (!isColliding(tempLocation)) {
            location = tempLocation;
        }
    }

    //Needs to know what to check that its colliding with. Should either be in the above move method or when things are being rendered.
    protected abstract boolean isColliding(Point2D.Double possibleLocation);


    public int getMaxSpeed() {
        return maxSpeed;
    }
    //TODO possibly add mass for more realizstic collisions
    
    public abstract void tick();

    @Override
    public int getPriority() {
        return (int)(this.location.getY()*10.0);
    }
}