package app.game.gamefield.elements.mobiles;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import app.game.gamefield.elements.mobiles.stats.CharacterStats;
import app.supportclasses.GameValues;

/**
 * Character
 */
public class Character extends Mobile {
    private boolean up, down, left, right;

    public Character(GameValues gameValues, CharacterStats.Character c, double xStart, double yStart) {
        super(xStart, yStart);
        this.stats = new CharacterStats(gameValues, c);
    }

    public void tick() {
        accelerate(up, down, left, right);
        updateVelocity();
        move();
        System.out.println("Position: " + x + ", " + y);
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        if (e.getKeyCode() == this.stats.gameValues.moveUp) {
            this.up = true;
        }   else if (e.getKeyCode() == this.stats.gameValues.moveDown) {
            this.down = true;
        }   else if (e.getKeyCode() == this.stats.gameValues.moveLeft) {
            this.left = true;
        }   else if (e.getKeyCode() == this.stats.gameValues.moveRight) {
            this.right = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == this.stats.gameValues.moveUp) {
            this.up = false;
        }   else if (e.getKeyCode() == this.stats.gameValues.moveDown) {
            this.down = false;
        }   else if (e.getKeyCode() == this.stats.gameValues.moveLeft) {
            this.left = false;
        }   else if (e.getKeyCode() == this.stats.gameValues.moveRight) {
            this.right = false;
        }
    }
    
}