package app.game.gamefield.elements.mobiles.stats;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;
/**
 * CharacterStats
 */
public class CharacterStats extends LivingStats{

    private int money, bombs, keys;
    
    public enum Character {
        Issac;
    }

    public CharacterStats(GameValues gameValues, Character character) {
        super(gameValues);
        money = bombs = keys = 0;

        switch(character) {
            case Issac:
                this.maxSpeed = 6;
                this.maxHealth = 6;
                this.image = new BufferedImageLoader(gameValues.ISSAC_FILE).getImage();
                break;
            default:
                break;
        }

        setFullHealth();
    }
}