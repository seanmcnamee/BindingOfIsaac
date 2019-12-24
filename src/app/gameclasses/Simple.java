package app.gameclasses;

import app.gameclasses.renderingclasses.Drawable;
/**
 * Simple
 */
public class Simple extends Drawable{

    private int x, y;

    public Simple(int x, int y){
        super();
        this.x = x;
        this.y = y;
    }

    public void render() {
        System.out.println(y);
    }

    @Override
    public int getPriority() {
        return this.y;
    }

    @Override
    public Simple setPriority(int... y) {
        System.out.println("Setting priority");
        this.y = y[0];
        return this;
    }

}