package app.game.gamefield.elements.mobiles;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;

import app.game.gamefield.elements.rendering.Drawable;
import app.game.gamefield.rooms.Room;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;

/**
 * Character
 */
public class Player extends Mobile {
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int money, bombs, keys;

    public enum Characters {
        Issac;
    }

    public Player(GameValues gameValues, Characters c, Point2D.Double location) {
        super(gameValues, location);
        money = bombs = keys = 0;
        sizeInBlocks = new Point2D.Double(1, 1);
        setCharacterStats(c);
        setFullHealth();
    }

    private void setCharacterStats(Characters character) {
        switch (character) {
        case Issac:
            this.accelerationRate = 12.0;
            this.maxSpeed = 6.0;
            this.maxHealth = 6;
            this.image = new BufferedImageLoader(gameValues.ISSAC_FILE).getImage();
            break;
        default:
            break;
        }
    }

    @Override
    public void tick(Room r) {
        accelerate(moveUp, moveDown, moveLeft, moveRight);
        updateVelocity();
        testCollisionAndMove(r);
        //System.out.println("Position: " + location.getX() + ", " + location.getY());
    }

    @Override
    protected void onCollision(Double newLocation, Drawable collidingElement, Room room) {
        // TODO Auto-generated method stub

    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        if (e.getKeyCode() == gameValues.moveUpKey) {
            this.moveUp = true;
        } else if (e.getKeyCode() == gameValues.moveDownKey) {
            this.moveDown = true;
        } else if (e.getKeyCode() == gameValues.moveLeftKey) {
            this.moveLeft = true;
        } else if (e.getKeyCode() == gameValues.moveRightKey) {
            this.moveRight = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == gameValues.moveUpKey) {
            this.moveUp = false;
        } else if (e.getKeyCode() == gameValues.moveDownKey) {
            this.moveDown = false;
        } else if (e.getKeyCode() == gameValues.moveLeftKey) {
            this.moveLeft = false;
        } else if (e.getKeyCode() == gameValues.moveRightKey) {
            this.moveRight = false;
        }
    }
}