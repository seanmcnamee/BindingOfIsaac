package app.game.gamefield.rooms;

import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.GameValues;

/**
 * SpawnRoom
 */
public class SpawnRoom extends Room {
    
    public SpawnRoom(GameValues gameValues, Drawable player) {
        super(gameValues, player, new BufferedImageLoader(gameValues.GAME_BACKGROUND_FILE).getImage());
    }

    
}