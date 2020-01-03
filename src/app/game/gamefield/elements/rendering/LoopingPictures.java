package app.game.gamefield.elements.rendering;

import java.awt.Point;

/**
 * LoopingPictures
 */
public class LoopingPictures extends InterchangeableImage {
    public Point[] ranges;
    private final int ticksPerImage;
    private int tickCount = 0;

    public LoopingPictures(int size, HitBox hitbox, int ticksPerImage, Point[] ranges) {
        super(size, hitbox);
        this.ticksPerImage = ticksPerImage;
        this.ranges = ranges;
    }

    public void tick() {
        tickCount++;
        if (this.tickCount >= ticksPerImage) {
            setNextImageIndex();
            tickCount = 0;
        }
    }

    private void setNextImageIndex() {
        Point range = findRange();
        double low = range.getX();
        double high = range.getY();
        int nextIndex = (int)((this.currentImageIndex-low+1)%(high-low+1) + low);
        if (goodIndex(nextIndex)) {
            this.currentImageIndex = nextIndex;
        }
    }

    @Override
    public void setCurrentImageIndex(int index) throws IndexOutOfBoundsException{
        if (goodIndex(index)) {
            Point range = findRange();
            double low = range.getX();
            double high = range.getY();
            if (!(index >= low && index <= high)) {
                this.currentImageIndex = index;
            }
        }
    }

    private Point findRange() throws IndexOutOfBoundsException{
        for (Point range : ranges) {
            double low = range.getX();
            double high = range.getY();
            if (this.currentImageIndex >= low && this.currentImageIndex <= high) {
                return range;
            }
        }
        throw new IndexOutOfBoundsException("The current index: " + this.currentImageIndex + " is not within a range!");
    }
}