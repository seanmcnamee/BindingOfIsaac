package app.game.gamefield.elements.immovables;

import java.awt.geom.Point2D;

import app.game.gamefield.elements.Destructible;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;
import java.awt.image.BufferedImage;

/**
 * Degradable
 */
public class Degradable extends Destructible {
    private BufferedImage[] images;

    public enum Degradables {
        Poop, Rock;
    }

    public Degradable(GameValues gameValues, Degradables degradable, Point2D.Double location) {
        super(gameValues, location);
        setDegradingImages(degradable);
        setFullHealth();
        updateImage();
    }

    public void setDegradingImages(Degradables degradable) {
        SpriteSheet spriteSheet = new SpriteSheet(gameValues.SPRITE_SHEET);
        
        switch(degradable) {
            case Poop:
                this.maxHealth = 3;
                this.sizeInBlocks = new Point2D.Double(1, 1);
                images = new BufferedImage[maxHealth];
                images[0] = spriteSheet.shrink(spriteSheet.grabImage(6, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                images[1] = spriteSheet.shrink(spriteSheet.grabImage(8, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                images[2] = spriteSheet.shrink(spriteSheet.grabImage(10, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                break;
            case Rock:
                this.maxHealth = 1;
                this.sizeInBlocks = new Point2D.Double(1, 1);
                images = new BufferedImage[maxHealth];
                images[0] = spriteSheet.shrink(spriteSheet.grabImage(14, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                break;
            default: //default
                this.maxHealth = 1;
                this.sizeInBlocks = new Point2D.Double(1, 1);
                images = new BufferedImage[maxHealth];
                images[0] = spriteSheet.shrink(spriteSheet.grabImage(2, 6, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
        }
    }

    @Override
    public boolean damage(int damage) {
        super.damage(damage);
        updateImage();
        return isDead();
    }

    private void updateImage() {
        if (!isDead()) {
            this.image = images[maxHealth-health];
        }
    }
    
}