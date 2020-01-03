package app.game.gamefield.elements.rendering;

import java.awt.image.BufferedImage;
/**
 * Movement
 */
public class InterchangeableImage {
    private BufferedImage[] images;
    private HitBox hitbox;
    public int currentImageIndex = 0;

    public InterchangeableImage(int size, HitBox hitbox) {
        images = new BufferedImage[size];
        this.currentImageIndex = 0;
        this.hitbox = hitbox;
    }

    public void setImage(int index, BufferedImage image) {
        if (goodIndex(index)) {
            images[index] = image;
        }
    }   

    public void setCurrentImageIndex(int index) throws IndexOutOfBoundsException{
        if (goodIndex(index)) {
            currentImageIndex = index;
        }
    }

    protected boolean goodIndex(int index) {
        if (index < this.images.length && index >= 0) {
            return true;
        }   else {
            throw new IndexOutOfBoundsException("An image won't exist for " + index +". The max is " + this.images.length);
        }
    }

    public int getCurrentImageIndex() {
        return this.currentImageIndex;
    }

    public BufferedImage getCurrentImage() {
        return images[currentImageIndex];
    }

    public HitBox getHitBox() { 
        return this.hitbox;
    }
}