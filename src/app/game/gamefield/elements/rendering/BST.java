package app.game.gamefield.elements.rendering;

import app.game.gamefield.elements.rendering.Node;

/**
 * BST
 */
public class BST {
    private Node root;
    private int nodeCount;

    public BST() {
        this.root = null;
        this.nodeCount = 0;
    }

    public BST(Node root) {
        this.root = root;
        this.nodeCount = 1;
    }

    public Node getRoot() {
        return root;
    }

    public void add(Node newNode) {
        addIterative(newNode);
    }

    public Node deQueue() {
        this.nodeCount--;
        return bubbleDown(root);
    }

    public void updatePriority(Node node) {
        Node largerChild = node.choosePriorityChild();
        if (largerChild != null && largerChild.getPriority()>node.getPriority()) {
            //Bubble Down...
            switchParentAndChild(largerChild);
            updatePriority(node);
        }   else {
            Node parent = node.getParent();
            if (parent != null && node.getPriority()>parent.getPriority()) {
                //Bubble Up...
                switchParentAndChild(node);
                updatePriority(node);
            }
        }
        //Done
    }

    private void bubbleUp(Node node) {
        Node parent = node.getParent();
        if (parent != null && node.getPriority() > parent.getPriority()) {
            switchParentAndChild(node);
            bubbleUp(node); 
        }
    }

    public Node bubbleDown(Node node) {
        Node nextChild = node.choosePriorityChild();
        if (nextChild==null) {
            //Remove from queue
            if (root!=node) {
                node.getParent().setChild(null, node.getParentDir());
            }   else {
                root = null;
            }
            return node;
        }   else {
            //Swap push down and continue
            switchParentAndChild(nextChild);
            return bubbleDown(node);
        }
    }


    /**
     * Switches a parent and child node
     * May switch the tree around but it doesn't matter.
     * @param node
     */
    private void switchParentAndChild(Node node) {
        //System.out.println("Pushing up " + node + " - " + node.getPriority());
        //System.out.println("Node: " + node.getPriority() + ", parent: " + node.getParent() + ", relation: " + node.getParentDir());
        Node parent = node.getParent();
        Node parentParent = parent.getParent();
        Node parentLeft = parent.getLeft();
        Node parentRight = parent.getRight();
        
        Node.ParentDir PPtoP = parent.getParentDir();
        Node.ParentDir PtoNode = node.getParentDir();


        //int PPtoP = findRelation(parentParent, parent);
        //int PtoNode = findRelation(parent, node);

        /*
        System.out.println("Node: " + node + "-" + node.getData());
        System.out.println("Parent: " + parent + "-" + parent.getData());
        System.out.println("\tL: " + parentLeft + "\n\tR: " + parentRight);
        System.out.println("\tPPtoP: " + PPtoP + "\n\tPtoNode: " + PtoNode);
        */

        //Link parent to L and R
        parent.setLeftChild(node.getLeft());
        parent.setRightChild(node.getRight());

        //Link Node to parent and parent's old child
        if (PtoNode==Node.ParentDir.right) {
            node.setRightChild(parent);
            node.setLeftChild(parentLeft);
        } else if (PtoNode==Node.ParentDir.left) {
            node.setLeftChild(parent);
            node.setRightChild(parentRight);
        } else {
            System.out.println("Node: " + node.getPriority() + ", parent: " + node.getParent() + ", relation: " + node.getParentDir());
            System.out.println("Some error with parent to node relation");
        }

        //Link PP to C (or the root if needed)
        if (PPtoP==Node.ParentDir.none) {
            this.root = node;
            node.resetParent();
        }   else {
            parentParent.setChild(node, PPtoP);
        }
    }

    private void addIterative(Node newNode) {
        //System.out.println("Adding node: " + newNode.getPriority());
        Node current = root;

        //For root node insert
        if (root == null) {
            this.root = newNode;

        //For all other cases
        }   else {
            int base = 2;
            int completeRows = (int)Math.floor( (Math.log(nodeCount+1)/Math.log(base)) );
            int spareCount = nodeCount-(int)Math.pow(2, completeRows)+1;
            boolean followRight = false;
            //System.out.println("\tNodeCount: " + nodeCount + "\n\t completeRows: " + completeRows + "\n\t spareCount: " + spareCount);

            
            for (int i = 0; i < completeRows; i++) {
                //System.out.println("\tIteration " + i);
                int spareInPath = spareCount % ( (int)Math.pow(base, completeRows-i) );
                int leftPathFullAmount = (int)Math.pow(base, completeRows-i-1);
                followRight = (spareInPath >= leftPathFullAmount);
                
                //System.out.println("\t\tSpare in path: " + spareInPath + "\n\t\tleftPathFullAmount: " + leftPathFullAmount);
                //System.out.println("\t\tFollowing right: " + followRight);
                //Follow until the last iteration
                if (!(i==completeRows-1)) {
                    if (followRight) {
                        current = current.getRight();
                    }   else {
                        current = current.getLeft();
                    }
                }
            }

            //For the final iteration, make this new node the child node
            //System.out.println("Out of loop. Setting child's right: " + followRight);
            if (followRight) {
                current.setRightChild(newNode);
            }   else {
                current.setLeftChild(newNode);
            }
        }
        
        //Increase node count
        this.nodeCount++;
        bubbleUp(newNode);
    }
    
    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.getPriority()+"_"+node.getParentDir()+" ");
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.getLeft());
            System.out.print(node.getPriority()+"_"+node.getParentDir()+" ");
            inOrder(node.getRight());
        }
    }

    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            System.out.print(node.getPriority()+"_"+node.getParentDir()+" ");
        }
    }


}