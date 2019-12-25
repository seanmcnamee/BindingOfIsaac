package app.game;

import java.awt.Graphics;

import app.game.gamefield.elements.rendering.Drawable;

/**
 * Simple
 */
public class Simple extends Drawable {

    private int x, y;

    public Simple(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public int getPriority() {
        return this.y;
    }

    public Simple setPriority(int... y) {
        System.out.println("Setting priority");
        this.y = y[0];
        return this;
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        System.out.println(y);
    }

}