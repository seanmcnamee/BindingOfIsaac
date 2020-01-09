package app.game.gamefield.house;

import app.game.gamefield.house.rooms.Room;

import java.awt.Point;
/**
 * GameMap
 */
public class Floor {
    //Holds the rooms for this floor
    private Room[][] roomMap;
    private Room[] rooms;
    private Room currentRoom;
    private Point sizeOfMap = new Point(7, 5);
    private int maxRooms;

    //TODO have input as num of each type of Room
    public Floor(int... roomCounts) {
        this.maxRooms = getTotal(roomCounts);
        rooms = new Room[this.maxRooms];
        roomMap = new Room[(int)(sizeOfMap.getX())][(int)(sizeOfMap.getY())];
        generateFloorMap(roomCounts);
        loadIntoArray();
        setStartingRoom(); //TODO Might remove this if the starting room isn't in the center of the roomMap
    }

    private int getTotal(int[] values) {
        int sum = 0;
        for (int i : values) {
            sum+=i;
        }
        return sum;
    }

    //TODO If the starting room is not in the center of the roomMap, load it in here!!!
    private void generateFloorMap(int... roomCounts) {

    }

    private void loadIntoArray() {

    }

    /**
     * TODO Starting room is in the middle of the roomMap??? then load it here else remove this
     */
    private void setStartingRoom() {
        currentRoom = roomMap[(int)(sizeOfMap.getX()/2)][(int)(sizeOfMap.getY()/2)];
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}