package app.game.gamefield.house;

import app.game.gamefield.elements.mobiles.players.Player;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.types.BossRoom;
import app.game.gamefield.house.rooms.types.RegularRoom;
import app.game.gamefield.house.rooms.types.SecretRoom;
import app.game.gamefield.house.rooms.types.ShopRoom;
import app.game.gamefield.house.rooms.types.TreasureRoom;
import app.supportclasses.GameValues;

import java.awt.Point;
import java.util.Map;

/**
 * GameMap
 */
public class Floor {
    // Holds the rooms for this floor
    private Room[] rooms;
    private Room currentRoom;
    private Point sizeOfMap = new Point(7, 5);
    private int maxRooms;
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
        this.maxRooms = getTotal(roomCounts);
        rooms = new Room[this.maxRooms];
        generateFloorMap(roomCounts);
    }

    private int getTotal(Map<Room.Rooms, Integer> roomCounts) {
        int sum = 0;
        for (Room.Rooms roomType : Room.Rooms.values()) {
            sum += roomCounts.get(roomType);
        }
        return sum;
    }

    // TODO If the starting room is not in the center of the roomMap, load it in
    // here!!!
    private void generateFloorMap(Map<Room.Rooms, Integer> roomCounts) {
        Room[][] roomMap = new Room[(int) (sizeOfMap.getX())][(int) (sizeOfMap.getY())];

        // Pick location for starting room
        // Set current room to starting room
        // Add rooms procedurally

        // Load map rooms into array
        // loadMapIntoArray(roomMap);

        // For testing, just add directly into the array
        for (int i = 0; i < this.maxRooms; i++) {
            Point p = new Point(i % (int) (sizeOfMap.getX()), i / (int) (sizeOfMap.getX()));
            rooms[i] = loadRandomRoom(p);
        }

        this.currentRoom = rooms[0];
    }

    private Room loadRandomRoom(Point p) {
        double rndNum = Math.random();
        if (rndNum < .1) {
            return new BossRoom(this.gameValues, this.player, p, null, null, null, null);
        } else if (rndNum < .2) {
            return new TreasureRoom(this.gameValues, this.player, p, null, null, null, null);
        } else if (rndNum < .3) {
            return new ShopRoom(this.gameValues, this.player, p, null, null, null, null);
        } else if (rndNum < .4) {
            return new SecretRoom(this.gameValues, this.player, p, null, null, null, null);
        } else {
            return new RegularRoom(this.gameValues, this.player, p, null, null, null, null);
        }
    }

    private void loadMapIntoArray(Room[][] roomMap) {
        Integer index = 0;
        for (Room[] row : roomMap) {
            for (Room room : row) {
                addRoomToArray(room, index);
            }
        }
    }

    private void addRoomToArray(Room r, Integer index) {
        if (r!=null) {
            rooms[index] = r;
            index = index++;
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Room[] getRooms() {
        return this.rooms;
    }

    public Point getSizeOfMap() {
        return sizeOfMap;
    }
}