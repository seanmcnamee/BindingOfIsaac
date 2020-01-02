package app.game.gamefield.elements.immovables;

import java.awt.geom.Point2D.Double;

import app.game.gamefield.elements.Destroyable;
import app.supportclasses.GameValues;

/**
 * Destructible
 */
public class Destructible extends Immovable implements Destroyable {


    public enum Objects {
        Rock;
    }

    public Destructible(GameValues gameValues, Objects object, Double location) {
        super(gameValues, location);
        this.maxHealth = 1;
        setFullHealth();
        setObjectImages(object);
    }

    public void setObjectImages(Objects degradable) {
        SpriteSheet spriteSheet = new SpriteSheet(gameValues.SPRITE_SHEET);
        
        switch(degradable) {
            default:
            case Rock:
                image = spriteSheet.shrink(spriteSheet.grabImage(12, 4, 2, 2, gameValues.SPRITE_SHEET_BOX_SIZE));
                break;
        }
    }

    @Override
    public boolean damage(int damage) {
        health -= damage;
        return isDead();
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    protected void setFullHealth() {
        this.health = this.maxHealth;
    }
    
}