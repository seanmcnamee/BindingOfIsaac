package app.game.gamefield.elements.rendering;
/**
 * BTNode
 */
public abstract class Node {
    //Class variables
    private Node parent, leftChild, rightChild;
    private ParentDir parentDir;

    public enum ParentDir {
        left, right, none;
    }

    //Constructors
    public Node(){
        this.parent = null;
        this.parentDir = ParentDir.none;
        this.leftChild = this.rightChild = null;
    }

    //Priority Number
    public abstract int getPriority();
    
    //Getters
    public Node getLeft() {
        return this.leftChild;
    }

    public Node getRight() {
        return this.rightChild;
    }

    public Node getParent() {
        return this.parent;
    }

    public ParentDir getParentDir() {
        return this.parentDir;
    }

    public Node choosePriorityChild() {
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

    //Setters
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
        if (this.leftChild!= null) {
            this.leftChild.parent = this;
            //System.out.println("Left parent direction for " + leftChild.getPriority());
            this.leftChild.parentDir = ParentDir.left;
        }
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        if (this.rightChild!= null) {
            this.rightChild.parent = this;
            //System.out.println("Right parent direction for " + rightChild.getPriority());
            this.rightChild.parentDir = ParentDir.right;
        }
    }

    public void setChild(Node node, ParentDir dir) {
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
    
    public Node resetConnections() {
        this.parent = null;
        this.parentDir = ParentDir.none;
        this.leftChild = this.rightChild = null;
        return this;
    }
    
}