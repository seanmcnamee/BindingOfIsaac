package app.game.gamefield.rooms;

import java.awt.Graphics;
import java.util.ArrayList;

import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.BST;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

/**
 * Room
 */
public abstract class Room {
    private BST elements; // Drawables
    private ArrayList<Mobile> movables;

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
            m.tick(this);
        }
    }

    public void render(Graphics g) {
        g.drawImage(defaultBackground, (int)gameValues.fieldXStart, (int)gameValues.fieldYStart, (int)gameValues.fieldXSize, (int)gameValues.fieldYSize, null);

        BST tempHeap = new BST();
        while(elements.getRoot()!=null) {
            Drawable temp = (Drawable)elements.deQueue();
            temp.render(g);
            tempHeap.add(temp.resetConnections());
        }
        elements = tempHeap;
    }

    public Drawable checkCollisions(Drawable main, Point2D.Double testLocation) {
        Drawable foundItem = null;
        BST tempHeap = new BST();
        while(elements.getRoot()!=null) {
            Drawable temp = (Drawable)elements.deQueue();
            if (main != temp && main.contains(testLocation, temp)) {
                foundItem = temp;
            }
            tempHeap.add(temp.resetConnections());
        }
        elements = tempHeap;
        return foundItem;
    }

    public Drawable recursiveCollisionCheck(Drawable main, Point2D.Double testLocation, Drawable currentNode) {
        if (currentNode == null) {
            return null;
        }   else if (main != currentNode && main.contains(testLocation, currentNode)) {
            return currentNode;
        }   else {
            Drawable left = recursiveCollisionCheck(main, testLocation, (Drawable)currentNode.getLeft());
            Drawable right = recursiveCollisionCheck(main, testLocation, (Drawable)currentNode.getRight());
            if (left!=null) {
                return left;
            }   else {
                return right;
            }
        }
    }

    public void destroyElement(Drawable element) {
        if (element.getClass().equals(Mobile.class)) {
            movables.remove(element);
        }
        elements.bubbleDown(element);
    }

}