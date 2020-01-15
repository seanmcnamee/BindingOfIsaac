package app.game.gamefield.house.floorgenerator;

/**
 * MutableBoolean
 */
public class MutableBoolean {
    private boolean b;

    public MutableBoolean(boolean b) {
        this.b = b;
    }

    public boolean getBoolean() {
        return b;
    }

    public void setBool(boolean b) {
        this.b = b;
    }

    public int intVal() {
        return b? 1:0;
    }
    
}