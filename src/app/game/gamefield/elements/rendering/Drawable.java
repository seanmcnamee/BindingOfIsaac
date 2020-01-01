package app.game.gamefield.elements.rendering;

import app.supportclasses.GameValues;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
/**
 * BTNode
 */
public abstract class Drawable extends Node{
    protected Point2D.Double location;
    protected BufferedImage image;
    protected GameValues gameValues;

    public Drawable(GameValues gameValues, Point2D.Double location) {
        super();
        this.gameValues = gameValues;
        this.location = location;
    }

    public void render(Graphics g) {
        //TODO add wall space accountability
        double wallPixels = gameValues.fieldYSize*.05;
        double singleSquareX = (gameValues.fieldXSize-2*wallPixels)/gameValues.XSpaces;
        double singleSquareY = (gameValues.fieldYSize-2*wallPixels)/gameValues.YSpaces;
        g.fillRect((int)(gameValues.fieldXStart+this.location.getX()*singleSquareX+wallPixels), (int)(gameValues.fieldYStart+this.location.getY()*singleSquareY+wallPixels), (int)(singleSquareX), (int)(singleSquareY));
    }

    public static void renderAll(BST elements, Graphics g) {
        BST tempHeap = new BST();
        while(elements.getRoot()!=null) {
            Drawable temp = (Drawable)elements.deQueue();
            temp.render(g);
            tempHeap.add(temp.resetConnections());
        }
        elements = tempHeap;
    }
}