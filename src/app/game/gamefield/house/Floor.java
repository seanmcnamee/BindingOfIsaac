package app.game.gamefield.house;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import app.game.gamefield.elements.mobiles.Mobile;
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
 * Floor
 * Holds the rooms for this floor. Converts from a Class<?> matrix to a Room array
 */
public class Floor {
    
    private Room[] rooms;
    private Room currentRoom;
    private FloorName floorName;

    private GameValues gameValues;
    private Player player;
    private boolean changingRoom;

    public enum FloorName {
        Basement1, Basement2, Caves1, Caves2, TheDepths1, TheDepths2, TheWomb;
    }

    public Floor(GameValues gameValues, Player player, FloorName floorName, Map<Class<?>, Integer> roomCounts) {
        this.gameValues = gameValues;
        this.player = player;
        this.floorName = floorName;
        rooms = generateFloorMap(roomCounts);
        this.changingRoom = false;
        printRooms();
    }

    private void printRooms() {
        System.out.println();
        System.out.println("               Printing Overall Rooms");
        System.out.println("--------------------------------------------------");
        for (Room r : rooms) {
            System.out.println("Room object: " + r + "\tSeen: " + r.isSeen() + ", Explored: " + r.isExplored());
        }
    }

    private Room[] generateFloorMap(Map<Class<?>, Integer> roomCounts) {
        Level levelFromCounts = new Level(roomCounts, gameValues);
        return convertFromEnumMatrixToRoomArray(levelFromCounts.getRoomMap());
    }

    private Room[] convertFromEnumMatrixToRoomArray(Class<?>[][] enumMatrix) {
        System.out.println();
        System.out.println();
        System.out.println("Converting to Room Matrix");
        Room[][] roomMatrix = convertFromEnumToRoomMatrix(enumMatrix);
        ArrayList<Room> roomArrayList = new ArrayList<Room>();

        System.out.println();
        System.out.println("Converting to arrayList");
        for (int x = 0; x < roomMatrix.length; x++) {
            for (int y = 0; y < roomMatrix[x].length; y++) {
                if (roomMatrix[x][y] != null) {
                    roomMatrix[x][y].setSurroundingAndLock(getRoomAt(x, y - 1, roomMatrix),
                            getRoomAt(x + 1, y, roomMatrix), getRoomAt(x, y + 1, roomMatrix),
                            getRoomAt(x - 1, y, roomMatrix));
                    roomArrayList.add(roomMatrix[x][y]);
                }
            }
        }

        System.out.println();
        System.out.println("Converting to array");
        Room[] convertedRooms = new Room[roomArrayList.size()];
        for (int i = 0; i < convertedRooms.length; i++) {
            convertedRooms[i] = roomArrayList.get(i);
            if (convertedRooms[i].getClass() == SpawnRoom.class) {
                System.out.println("Setting current room...");
                setCurrentRoom(convertedRooms[i]);
            }
        }

        return convertedRooms;
    }

    private Room getRoomAt(int x, int y, Room[][] roomMatrix) {
        if (x >= 0 && x < roomMatrix.length && y >= 0 && y < roomMatrix[0].length) {
            return roomMatrix[x][y];
        } else {
            return null;
        }
    }

    private Room[][] convertFromEnumToRoomMatrix(Class<?>[][] enumMatrix) {
        Room[][] roomMatrix = new Room[enumMatrix.length][enumMatrix[0].length];

        for (int x = 0; x < roomMatrix.length; x++) {
            for (int y = 0; y < roomMatrix[x].length; y++) {
                if (enumMatrix[x][y] != null) {
                    Point location = new Point(x, y);
                    roomMatrix[x][y] = makeDisconnectedRoom(enumMatrix[x][y], location);
                    System.out.println("Room object: " + roomMatrix[x][y]);
                }
            }
        }

        return roomMatrix;
    }

    private Room makeDisconnectedRoom(Class<?> type, Point location) {
        System.out.println("\nMaking a new " + type + " room");
        if (type.equals(SpawnRoom.class)) {
            return new SpawnRoom(this.gameValues, this.player, (this.floorName == FloorName.Basement1), location);
        } else if (type.equals(BossRoom.class)) {
            return new BossRoom(this.gameValues, this.player, location);
        } else if (type.equals(TreasureRoom.class)) {
            return new TreasureRoom(this.gameValues, this.player, location);
        } else if (type.equals(ShopRoom.class)) {
            return new ShopRoom(this.gameValues, this.player, location);
        } else if (type.equals(RegularRoom.class)) {
            return new RegularRoom(this.gameValues, this.player, location);
        } else {
            throw new NullPointerException("Invalid Choice given when creating specialty room!");
            
            /*
            try {
                
                return (Room) type.getConstructor(GameValues.class, Drawable.class, Point.class)
                        .newInstance(this.gameValues, this.player, location);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new ExceptionInInitializerError("Class types are fucked on creation");
            }*/
        }
    }

    public void setCurrentRoom(Room room) {
        this.changingRoom = true;
        if (currentRoom!=null) {
            removePlayerFrom(this.currentRoom);
        }
        this.currentRoom = room;
        room.setExplored();
        addPlayerTo(room);
        this.changingRoom = false;
    }

    private void addPlayerTo(Room room) {
        room.addPlayer(this.player);
    }

    private void removePlayerFrom(Room room) {
        this.currentRoom.removePlayer(this.player);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Room[] getRooms() {
        return this.rooms;
    }

    public void tick() {
        ArrayList<Mobile> mobiles = currentRoom.getMovables();
        for (int m = 0; m < mobiles.size(); m++) {
            if (this.changingRoom) {
                return;
            }
            mobiles.get(m).tick(this);
        }
    }
}