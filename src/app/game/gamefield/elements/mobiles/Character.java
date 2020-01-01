package app.game.gamefield.elements.mobiles;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;

/**
 * Character
 */
public class Character extends Living {
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private int money, bombs, keys;

    public enum Characters {
        Issac;
    }

    public Character(GameValues gameValues, Characters c, double xStart, double yStart) {
        super(gameValues, xStart, yStart);
        money = bombs = keys = 0;
        setCharacterStats(c);
    }

    private void setCharacterStats(Characters character) {
        switch (character) {
        case Issac:
            this.maxSpeed = 6;
            this.maxHealth = 6;
            this.image = new BufferedImageLoader(gameValues.ISSAC_FILE).getImage();
            break;
        default:
            break;
        }
        setFullHealth();
    }

    public void tick() {
        accelerate(moveUp, moveDown, moveLeft, moveRight);
        updateVelocity();
        move();
        System.out.println("Position: " + location.getX() + ", " + location.getY());
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

    @Override
    protected boolean isColliding(Double possibleLocation) {
        // TODO Auto-generated method stub
        return false;
    }
    
}