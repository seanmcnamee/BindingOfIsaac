package app.game.gamefield.rooms;

import java.awt.Graphics;
import java.util.ArrayList;

import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.BST;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;
import java.awt.image.BufferedImage;

/**
 * Room
 */
public abstract class Room {
    BST elements; // Drawables
    ArrayList<Mobile> movables;

    private final BufferedImage defaultBackground; // Background
    GameValues gameValues;

    public Room(GameValues gameValues, Drawable player, BufferedImage image) {
        this.gameValues = gameValues;
        this.elements = new BST();
        this.movables = new ArrayList<Mobile>();
        this.elements.add(player);
        this.movables.add((Mobile)player);
        this.defaultBackground = image;
    }

    public void tick() {
        for (Mobile m: movables) {
            m.tick();
        }
    }

    public void render(Graphics g) {
        
        g.drawImage(defaultBackground, (int)gameValues.fieldXStart, (int)gameValues.fieldYStart, (int)gameValues.fieldXSize, (int)gameValues.fieldYSize, null);

        /*
        BST tempHeap = new BST();
        while(elements.getRoot()!=null) {
            Drawable temp = (Drawable)elements.deQueue();
            temp.render(g); //TODO add the size of the gameField as parameters
            tempHeap.add(temp.resetConnections());
        }
        elements = tempHeap;
        */
        Drawable.renderAll(elements, g);
    }
}