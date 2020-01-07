package app.game.gamefield.rooms;

import java.awt.Graphics;
import java.util.ArrayList;

import app.game.gamefield.elements.immovables.walls.Wall;
import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.BST;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

/**
 * Room
 */
public abstract class Room {
    protected BST elements; // Drawables
    protected ArrayList<Mobile> movables;

    //TODO add actual objects as the walls for a room, not just a background...
    private BufferedImage background; // Background
    private BufferedImage roomIcon;
    private boolean explored;
    GameValues gameValues;

    public enum Rooms {
        Spawn, Regular, Arcade, Shop, Treasure, Boss, Secret;
    }

    public Room(GameValues gameValues, Drawable player, Rooms roomType) {
        this.gameValues = gameValues;
        this.elements = new BST();
        this.movables = new ArrayList<Mobile>();
        this.explored = false;
        setPictures(roomType);
        createWalls();

        this.elements.add(player);
        this.movables.add((Mobile)player);
    }

    private void setPictures(Rooms room) {
        SpriteSheet spriteSheet = new SpriteSheet(gameValues.SPRITE_SHEET);
        this.roomIcon = null;
        this.roomIcon = spriteSheet.shrink(spriteSheet.grabImage(1, 1, 1, 1, gameValues.SPRITE_SHEET_BOX_SIZE));

        
        //TODO go through these backgrounds and icons
        switch(room) {
            case Spawn:
                this.background = new BufferedImageLoader(gameValues.STARTING_BACKGROUND_FILE).getImage();
                break;
            case Regular:

            case Arcade:

            case Shop:

            case Treasure:

            case Boss:

            case Secret:

            default:
                this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
                break;
        }
    }

    public void tick() {
        for (Mobile m: movables) {
            m.tick(this);
        }
    }

    public void render(Graphics g) {
        g.drawImage(background, (int)gameValues.fieldXStart, (int)gameValues.fieldYStart, (int)gameValues.fieldXSize, (int)gameValues.fieldYSize, null);
        renderElements(g);
    }

    private void renderElements(Graphics g) {
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

    private void createWalls() {
        Drawable wallTop, wallBottom, wallLeft, wallRight;
        wallTop = new Wall(gameValues, Wall.WallType.Top);
        wallBottom = new Wall(gameValues, Wall.WallType.Bottom);
        wallLeft = new Wall(gameValues, Wall.WallType.Left);
        wallRight = new Wall(gameValues, Wall.WallType.Right);

        this.elements.add(wallTop);
        this.elements.add(wallBottom);
        this.elements.add(wallLeft);
        this.elements.add(wallRight);

    }

}