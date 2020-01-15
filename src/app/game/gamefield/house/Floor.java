package app.game.gamefield.house;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import app.game.gamefield.elements.mobiles.players.Player;
import app.game.gamefield.house.floorgenerator.Level;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.types.BossRoom;
import app.game.gamefield.house.rooms.types.RegularRoom;
import app.game.gamefield.house.rooms.types.ShopRoom;
import app.game.gamefield.house.rooms.types.SpawnRoom;
import app.game.gamefield.house.rooms.types.TreasureRoom;
import app.supportclasses.GameValues;

/**
 * GameMap
 */
public class Floor {
    // Holds the rooms for this floor
    private Room[] rooms;
    private Room currentRoom;
    private FloorName floorName;

    private GameValues gameValues;
    private Player player;

    public enum FloorName {
        Basement1, Basement2, Caves1, Caves2, TheDepths1, TheDepths2, TheWomb;
    }

    public Floor(GameValues gameValues, Player player, FloorName floorName, Map<Room.Rooms, Integer> roomCounts) {
        this.gameValues = gameValues;
        this.player = player;
        this.floorName = floorName;
        rooms = generateFloorMap(roomCounts);

        System.out.println("--------------------------------------------------");
        for (Room r : rooms) {
            System.out.println("Room: " + r.getClass());
        }
    }

    private Room[] generateFloorMap(Map<Room.Rooms, Integer> roomCounts) {
        Level levelFromCounts = new Level(roomCounts, gameValues);
        return convertFromEnumMatrixToRoomArray(levelFromCounts.getRoomMap());
    }

    private Room[] convertFromEnumMatrixToRoomArray(Room.Rooms[][] enumMatrix) {
        Room[][] roomMatrix = convertFromEnumToRoomMatrix(enumMatrix);
        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        
        for (int x = 0; x < roomMatrix.length; x++) {
            for (int y = 0; y < roomMatrix[x].length; y++) {
                if (roomMatrix[x][y]!=null) {
                    System.out.println("A ha ha");
                    roomMatrix[x][y].setSurroundingAndLock(getRoomAt(x, y-1, roomMatrix), getRoomAt(x+1, y, roomMatrix), getRoomAt(x, y+1, roomMatrix), getRoomAt(x-1, y, roomMatrix));
                    roomArrayList.add(roomMatrix[x][y]);
                }
            }
        }

        Room[] convertedRooms = new Room[roomArrayList.size()];
        for (int i = 0; i < convertedRooms.length; i++) {
            convertedRooms[i] = roomArrayList.get(i);
        }

        return convertedRooms;
    }

    private Room getRoomAt(int x, int y, Room[][] roomMatrix) {
        if (x >= 0 && x < roomMatrix.length && y >= 0 && y < roomMatrix[0].length) {
            return roomMatrix[x][y];
        }   else {
            return null;
        }
    }

    private Room[][] convertFromEnumToRoomMatrix(Room.Rooms[][] enumMatrix) {
        Room[][] roomMatrix = new Room[enumMatrix.length][enumMatrix[0].length];
        
        for (int x = 0; x < roomMatrix.length; x++) {
            for (int y = 0; y < roomMatrix[x].length; y++) {
                if (enumMatrix[x][y]!=null) {
                    Point location = new Point(x, y);
                    roomMatrix[x][y] = makeDisconnectedRoom(enumMatrix[x][y], location);
                }
            }
        }

        return roomMatrix;
    }


    private Room makeDisconnectedRoom(Room.Rooms type, Point location) {
        switch(type) {
            case Boss:
                return new BossRoom(this.gameValues, this.player, location);
            case Treasure:
                return new TreasureRoom(this.gameValues, this.player, location);
            case Shop:
                return new ShopRoom(this.gameValues, this.player, location);
            case Regular:
                return new RegularRoom(this.gameValues, this.player, location);
            case Spawn:
                Room spawnRoom = new SpawnRoom(this.gameValues, this.player, (this.floorName==FloorName.Basement1), location);
                this.currentRoom = spawnRoom;
                return spawnRoom;
            default: 
                throw new NullPointerException("Invalid Choice given when creating specialty room!");
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Room[] getRooms() {
        return this.rooms;
    }
}