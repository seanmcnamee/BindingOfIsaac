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
        SpriteSheet icons = new SpriteSheet(gameValues.ICON_SPRITE_SHEET);
        this.roomIcon = null;
        

        
        //TODO go through these backgrounds
        switch(room) {
            case Spawn:
                this.background = new BufferedImageLoader(gameValues.STARTING_BACKGROUND_FILE).getImage();
                this.roomIcon = icons.shrink(icons.grabImage(2, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
                break;
            case Regular:
                this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
                this.roomIcon = icons.shrink(icons.grabImage(1, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
                break;
            //case Arcade: //TODO add arcade room as well
                //this.roomIcon = spriteSheet.shrink(spriteSheet.grabImage(1, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
            case Shop:
                this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
                this.roomIcon = icons.shrink(icons.grabImage(1, 0, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
                break;
            case Treasure:
                this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
                this.roomIcon = icons.shrink(icons.grabImage(0, 0, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
                break;
            case Boss:
                this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();    
                this.roomIcon = icons.shrink(icons.grabImage(2, 0, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
                break;
            case Secret:
                this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();    
                this.roomIcon = icons.shrink(icons.grabImage(0, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
                break;
            default:
            this.background = new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage();
                this.roomIcon = icons.shrink(icons.grabImage(1, 1, 1, 1, gameValues.ICON_SPRITE_SHEET_BOX_SIZE));
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

    /*
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
    */

    /**
     * Recursively actually works better than removing and creating a new Priority Queue
     */
    public Drawable recursiveCollisionCheck(Drawable main, Point2D.Double testLocation) {
        return recursiveCollisionCheck(main, testLocation, (Drawable)elements.getRoot());
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