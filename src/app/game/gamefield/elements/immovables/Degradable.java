package app.game.gamefield.elements.immovables;

import java.awt.geom.Point2D.Double;

import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;
import java.awt.image.BufferedImage;

/**
 * Degradable
 */
public class Degradable extends Immovable {
    private BufferedImage[] images;

    public enum Degradables {
        Poop;
    }

    public Degradable(GameValues gameValues, Degradables degradable, Double location) {
        super(gameValues, Immovable.Objects.Degradable, location);
        setDegradingImages(degradable);
    }

    public void setDegradingImages(Degradables degradable) {
        SpriteSheet spriteSheet = new SpriteSheet(gameValues.SPRITE_SHEET);
        
        switch(degradable) {
            case Poop:
                this.maxHealth = 3;
                images = new BufferedImage[maxHealth];
                images[0] = spriteSheet.shrink(spriteSheet.grabImage(6, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                images[1] = spriteSheet.shrink(spriteSheet.grabImage(8, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                images[2] = spriteSheet.shrink(spriteSheet.grabImage(10, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
            default: //default
                this.maxHealth = 1;
                images = new BufferedImage[maxHealth];
                images[0] = spriteSheet.shrink(spriteSheet.grabImage(2, 6, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
        }
        setFullHealth();
    }

    private void updateImage() {
        if (!isDead()) {
            this.image = images[maxHealth-health];
        }
    }
    
}