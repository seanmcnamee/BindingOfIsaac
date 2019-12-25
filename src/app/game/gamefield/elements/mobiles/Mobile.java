package app.game.gamefield.elements.mobiles;

import app.game.gamefield.elements.mobiles.stats.MobileStats;
import app.game.gamefield.elements.rendering.Drawable;

import java.awt.geom.Point2D;
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
    }

    public void accelerate(boolean up, boolean down, boolean left, boolean right) {
        accelerate(up?-1:0, down?1:0, left?-1:0, right?1:0);
    }

    public void accelerate(int up, int down, int left, int right) {
        accelerate(up+down, left+right);
    }

    public void accelerate(int xAcc, int yAcc) {
        accelerationPercent.x = (int)Math.signum(xAcc);
        accelerationPercent.y = (int)Math.signum(yAcc);
    }

    protected void updateVelocity() {
        final double acceleration = .35;
        final double friction = .1;

        double tempXPercent, tempYPercent;
        tempXPercent = velocityPercent.x;
        tempYPercent = velocityPercent.y;

        tempXPercent -= Math.signum(tempXPercent)*friction + acceleration*accelerationPercent.x;
        tempYPercent -= Math.signum(tempYPercent)*friction + acceleration*accelerationPercent.y;

        if (Math.sqrt((Math.pow(tempXPercent, 2) + Math.pow(tempYPercent, 2))) <= 100) {
            velocityPercent.x = tempXPercent;
            velocityPercent.y = tempYPercent;
        }
    }

    protected void move() {
        double tempX, tempY;
        tempX = x;
        tempY = y;

        tempX += velocityPercent.x*stats.getMaxSpeed();
        tempY += velocityPercent.y*stats.getMaxSpeed();

        //TODO add collision check
        //(possibly a method that everything that extends has to make? -abstract method?)

        x = tempX;
        y = tempY;
    }

    public abstract void tick();

    @Override
    public int getPriority() {
        return (int)(this.y*10.0);
    }
}