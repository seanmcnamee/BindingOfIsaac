package app.game.gamefield.elements.rendering;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import app.game.gamefield.elements.Stats;
/**
 * BTNode
 */
public abstract class Drawable {
    //Class variables
    private Drawable parent, leftChild, rightChild;
    private ParentDir parentDir;
    protected Stats stats;

    public enum ParentDir {
        left, right, none;
    }

    //Constructors
    public Drawable(){
        this.parent = null;
        this.parentDir = ParentDir.none;
        this.leftChild = this.rightChild = null;
    }

    //Priority Number
    public abstract int getPriority();

    public abstract void render(Graphics g);

    //public abstract Drawable setPriority(int... data);
    
    //Getters
    public Drawable getLeft() {
        return this.leftChild;
    }

    public Drawable getRight() {
        return this.rightChild;
    }

    public Drawable getParent() {
        return this.parent;
    }

    public void setLeftChild(Drawable leftChild) {
        this.leftChild = leftChild;
        if (this.leftChild!= null) {
            this.leftChild.parent = this;
            //System.out.println("Left parent direction for " + leftChild.getPriority());
            this.leftChild.parentDir = ParentDir.left;
        }
    }

    public void setRightChild(Drawable rightChild) {
        this.rightChild = rightChild;
        if (this.rightChild!= null) {
            this.rightChild.parent = this;
            //System.out.println("Right parent direction for " + rightChild.getPriority());
            this.rightChild.parentDir = ParentDir.right;
        }
    }

    public void setChild(Drawable node, ParentDir dir) {
        if (dir==ParentDir.left) {
            setLeftChild(node);
        }   else {
            setRightChild(node);
        }
    }

    public void resetParent() {
        parent = null;
        //System.out.println("Resetting parent direction for " + getPriority());
        this.parentDir = ParentDir.none;
    }

    public ParentDir getParentDir() {
        return this.parentDir;
    }

    public Drawable choosePriorityChild() {
        if (leftChild==null && rightChild==null) {
            return null;
        }   else if (leftChild==null) {
            return rightChild;
        }   else if (rightChild==null) {
            return leftChild;
        }   else {
            if (leftChild.getPriority()>rightChild.getPriority()) {
                return leftChild;
            }   else {
                return rightChild;
            }
        }
    }
    
    public Drawable resetConnections() {
        this.parent = null;
        this.parentDir = ParentDir.none;
        this.leftChild = this.rightChild = null;
        return this;
    }
    
}