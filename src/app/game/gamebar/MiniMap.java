package app.game.gamebar;

import app.game.gamefield.rooms.Floor;

import java.awt.Graphics;
import java.awt.Color;
/**
 * MiniMap
 */
public class MiniMap {
    Floor floor;
    
    public MiniMap(Floor f) {
        this.floor = f;
    }

    public void setNewFloor(Floor newFloor) {
        this.floor = newFloor;
    }

    public void render(Graphics g) {
        Color emptyRoom = new Color(100, 100, 100);
        //TODO Add minimap class that is used in here
    }
}