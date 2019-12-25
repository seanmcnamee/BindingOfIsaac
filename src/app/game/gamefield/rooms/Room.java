package app.game.gamefield.rooms;

import java.awt.Graphics;
import java.util.ArrayList;

import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.BST;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;

/**
 * Room
 */
public abstract class Room {
    BST elements;
    ArrayList<Mobile> movables;
    GameValues gameValues;

    public Room() {
        elements = new BST();
        movables = new ArrayList<Mobile>();
    }

    public void tick() {
        for (Mobile m: movables) {
            m.tick();
        }
    }

    public void render(Graphics g) {
        BST tempHeap = new BST();
        while(elements.getRoot()!=null) {
            Drawable temp = elements.deQueue();
            temp.render(g);
            tempHeap.add(temp.resetConnections());
        }
        elements = tempHeap;
    }
}