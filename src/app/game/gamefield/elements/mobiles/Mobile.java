package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.mobiles.stats.MobileStats;
import app.game.gamefield.elements.rendering.Drawable;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Entity
 */
public abstract class Mobile extends Drawable{
    protected MobileStats stats;

    protected double x, y;
    protected Point2D.Double velocityPercent;
    protected Point accelerationPercent;

    public Mobile(double x, double y) {
        super();
        this.x = x;
        this.y = y;
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
        updateVelocity();
    }

    protected void updateVelocity() {
        final double acceleration = 6.0/60.0;//How long should it take to accelerate to full? 1/2 a second... so it should double in a second
        final double friction = 2.0/60.0;
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

        if (Math.sqrt((Math.pow(tempXPercent, 2) + Math.pow(tempYPercent, 2))) <= 1) {
            velocityPercent.x = tempXPercent;
            velocityPercent.y = tempYPercent;
        }   else {
            //Lower both?
            velocityPercent.x *=.99;
            velocityPercent.y *=.99;
        }

        System.out.println("Velocity... x: " + velocityPercent.getX() + ", y: " + velocityPercent.getY());
    }

    protected void move() {
        double tempX, tempY;
        tempX = x;
        tempY = y;

        tempX += velocityPercent.x*stats.getMaxSpeed()/60;
        tempY += velocityPercent.y*stats.getMaxSpeed()/60;

        //TODO add collision check
        //(possibly a method that everything that extends has to make? -abstract method?)

        x = tempX;
        y = tempY;
        
    }

    public void render(Graphics g) {
        //TODO add wall space accountability
        double wallPixels = stats.gameValues.fieldYSize*.05;
        double singleSquareX = (stats.gameValues.fieldXSize-2*wallPixels)/stats.gameValues.XSpaces;
        double singleSquareY = (stats.gameValues.fieldYSize-2*wallPixels)/stats.gameValues.YSpaces;
        g.fillRect((int)(stats.gameValues.fieldXStart+this.x*singleSquareX+wallPixels), (int)(stats.gameValues.fieldYStart+this.y*singleSquareY+wallPixels), (int)(singleSquareX), (int)(singleSquareY));
    }

    public abstract void tick();

    @Override
    public int getPriority() {
        return (int)(this.y*10.0);
    }
}