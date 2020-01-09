package app.game.gamefield.house;

import java.util.EnumMap;
import java.util.Map;

import app.game.gamefield.house.rooms.Room;

/**
 * Levels
 */
public class House {
    //Holds the different floors
    Floor[] floors;
    private int currentFloor;

    public House() {
        currentFloor = 0;
        createFloors();
    }

    private void createFloors() {
        final int floorCount = 1;
        this.floors = new Floor[floorCount];

        
    }

    private void setBasement1() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[0] = new Floor(roomCounts);
    }

    private void setBasement2() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[1] = new Floor(roomCounts);
    }

    private void setCaves1() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[2] = new Floor(roomCounts);
    }

    private void setCaves2() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[3] = new Floor(roomCounts);
    }

    private void setTheDepths1() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[4] = new Floor(roomCounts);
    }

    private void setTheDepths2() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[5] = new Floor(roomCounts);
    }

    
    //Spawn, Regular, Shop, Treasure, Arcade, Boss, Secret;
    //Unlocked after beating Mom once
    private void setTheWomb() {
        Map<Room.Rooms, Integer> roomCounts = new EnumMap<Room.Rooms, Integer>(Room.Rooms.class);

        
        this.floors[6] = new Floor(roomCounts);
    }




    public void updateCurrentFloor() {
        currentFloor++;
    }

    public Floor getCurrentFloor() {
        return floors[currentFloor];
    }
}