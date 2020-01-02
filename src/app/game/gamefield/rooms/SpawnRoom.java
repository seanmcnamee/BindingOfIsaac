package app.game.gamefield.rooms;

import app.game.gamefield.elements.immovables.Degradable;
import app.game.gamefield.elements.immovables.Degradable.Degradables;
import app.game.gamefield.elements.rendering.Drawable;
import app.supportclasses.GameValues;

import java.awt.geom.Point2D;

/**
 * SpawnRoom
 */
public class SpawnRoom extends Room {
    
    public SpawnRoom(GameValues gameValues, Drawable player) {
        super(gameValues, player, Room.Rooms.Spawn);
        createRoomImmovables(gameValues);
    }

    private void createRoomImmovables(GameValues gameValues) {
        for (int i = 0; i < gameValues.XSpaces/2; i++) {
            Drawable rock = new Degradable(gameValues, Degradables.Rock, new Point2D.Double(i, 0));
            elements.add(rock);
        }
        
    }
    
}