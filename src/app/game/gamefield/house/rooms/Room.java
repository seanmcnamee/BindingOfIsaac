package app.game.gamefield.house.rooms;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import app.game.gamefield.elements.immovables.doors.Door;
import app.game.gamefield.elements.immovables.doors.Door.DoorPosition;
import app.game.gamefield.elements.immovables.walls.Wall;
import app.game.gamefield.elements.mobiles.Mobile;
import app.game.gamefield.elements.rendering.BST;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * Room
 */
public abstract class Room extends Traversable{
    
    protected BST elements; // Drawables
    protected ArrayList<Mobile> movables; //Things that move and must be checked for collision
    private BufferedImage background; // Background picture
    protected GameValues gameValues;
    private Rooms roomType;

    public enum Rooms { //TODO change these into class types???
        Spawn, Regular, Shop, Treasure, Arcade, Boss, Secret;
    }

    public Room(GameValues gameValues, Drawable player, Rooms roomType, Point location) {
        super(location);
        this.roomType = roomType;
        initializeRoom(gameValues, player, roomType);
    }
    
    /*
    public Room(GameValues gameValues, Drawable player, Rooms roomType, Point location, Traversable above, Traversable below, Traversable left, Traversable right) {
        super(location, above, below, left, right);
        initializeRoom(gameValues, player, roomType);
    }*/

    private void initializeRoom(GameValues gameValues, Drawable player, Rooms roomType) {
        this.gameValues = gameValues;

        this.elements = new BST();
        //this.elements.add(player);

        this.movables = new ArrayList<Mobile>();
        //this.movables.add((Mobile)player);

        setPictures(roomType);
        createWalls();
        createMobiles();
        createImmovables();
    }

    private void setPictures(Rooms room) {
        SpriteSheet icons = new SpriteSheet(gameValues.ICON_SPRITE_SHEET);
        
        //TODO go through these backgrounds
        switch(room) {
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

    /**
     * Called once the surrounding rooms are set
     */
    protected void createDoors() {
        
        if (getAbove()!=null) {
            System.out.println("Creating door above");
            elements.add(new Door(gameValues, ((Room)getAbove()).roomType, DoorPosition.Top));
        }
        if (getRight()!=null) {
            System.out.println("Creating door right");
            elements.add(new Door(gameValues, ((Room)getRight()).roomType, DoorPosition.Right));
        }
        if (getBelow()!=null) {
            System.out.println("Creating door below");
            elements.add(new Door(gameValues, ((Room)getBelow()).roomType, DoorPosition.Below));
        }
        
        if (getLeft()!=null) {
            System.out.println("Creating door left");
            elements.add(new Door(gameValues, ((Room)getLeft()).roomType, DoorPosition.Left));
        }
        
    }

    private void createWalls() {
        System.out.println("Creating Walls for " + this.getClass());
        Drawable wallTop, wallBottom, wallLeft, wallRight;
        wallTop = new Wall(gameValues, Wall.WallType.Top);
        wallBottom = new Wall(gameValues, Wall.WallType.Bottom);
        wallLeft = new Wall(gameValues, Wall.WallType.Left);
        wallRight = new Wall(gameValues, Wall.WallType.Right);
        System.out.println("Elements: " + wallTop + "- " + "- " + wallRight + "- " + wallBottom + "- " + wallLeft);
        System.out.println("Elements Heap: " + elements);

        System.out.println("Adding elements to room");
        this.elements.add(wallTop);
        this.elements.add(wallBottom);
        this.elements.add(wallLeft);
        this.elements.add(wallRight);
    }

    protected abstract void createMobiles();
    protected abstract void createImmovables();

    public void render(Graphics g) {
        drawBackground(g);
        renderElements(g);
    }

    protected void drawBackground(Graphics g) {
        g.drawImage(background, (int)gameValues.fieldXStart, (int)gameValues.fieldYStart, (int)gameValues.fieldXSize, (int)gameValues.fieldYSize, null);
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
     * MUST do regular to ensure that things ontop of other things (doors on walls) will be checked for collision first
     */
    public Drawable recursiveCollisionCheck(Drawable main, Point2D.Double testLocation) {
        //System.out.println("Checking collisions");
        return testEachCollision(main, testLocation);
        //return recursiveCollisionCheck(main, testLocation, (Drawable)elements.getRoot());
    }

    private Drawable testEachCollision(Drawable main, Point2D.Double testLocation) {
        ArrayList<Drawable> drawableArray = getAllDrawables();
        int arrayLength = drawableArray.size();
        for (int i = 0; i < arrayLength; i++) {
            Drawable currentlyChecking = getLowestPriority(drawableArray);
            //System.out.println("\tTesting " + currentlyChecking + " with Priority " + currentlyChecking.getPriority());
            if (main != currentlyChecking && main.contains(testLocation, currentlyChecking)) {
                return currentlyChecking;
            }
        }
        return null;
    }

    private ArrayList<Drawable> getAllDrawables() {
        Queue<Drawable> toGetChildren = new LinkedList<Drawable>();
        ArrayList<Drawable> drawableArray = new ArrayList<Drawable>();
        toGetChildren.add((Drawable)elements.getRoot());

        while (!toGetChildren.isEmpty()) {
            Drawable current = toGetChildren.remove();
            Drawable left = (Drawable)current.getLeft();
            Drawable right = (Drawable)current.getRight();
            if (left!=null) {
                toGetChildren.add(left);
            }
            if (right!=null) {
                toGetChildren.add(right);
            }
            drawableArray.add(current);
        }
        return drawableArray;
    }

    private Drawable getLowestPriority(ArrayList<Drawable> drawableArray) {
        Point indexAndPriority = new Point(0, drawableArray.get(0).getPriority());
        for (int i = 0 ; i < drawableArray.size(); i++) {
            if (drawableArray.get(i).getPriority() < indexAndPriority.getY()) {
                indexAndPriority = new Point(i, drawableArray.get(i).getPriority());
            }
        }
        return drawableArray.remove((int)indexAndPriority.getX());
    }

    /*
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
    */

    
    public void destroyElement(Drawable element) {
        if (element.getClass().equals(Mobile.class)) {
            movables.remove(element);
        }
        elements.bubbleDown(element);
    }

    public void addPlayer(Drawable player) {
        this.elements.add(player);
        this.movables.add((Mobile)player);
    }

    public void removePlayer(Drawable player) {
        destroyElement(player);
        this.movables.remove((Mobile)player);
    }

    public ArrayList<Mobile> getMovables() {
        return movables;
    }

}