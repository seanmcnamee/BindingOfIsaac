package app.game.gamefield.house;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import app.game.gamefield.elements.mobiles.players.Player;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.types.ArcadeRoom;
import app.game.gamefield.house.rooms.types.BossRoom;
import app.game.gamefield.house.rooms.types.RegularRoom;
import app.game.gamefield.house.rooms.types.SecretRoom;
import app.game.gamefield.house.rooms.types.ShopRoom;
import app.game.gamefield.house.rooms.types.SpawnRoom;
import app.game.gamefield.house.rooms.types.TreasureRoom;
import app.supportclasses.GameValues;

/**
 * Levels
 */
public class House {
    //Holds the different floors
    Floor[] floors;
    private int currentFloor;
    private Player player;
    private GameValues gameValues;

    public House(GameValues gameValues, Player player) {
        this.gameValues = gameValues;
        this.player = player;
        currentFloor = 0;
        createFloors();
    }

    private void createFloors() {
        final int floorCount = 1;
        this.floors = new Floor[floorCount];
        setBasement1();
    }

    //Spawn, Regular, Shop, Treasure, Arcade, Boss, Secret;
    private void setBasement1() { //Around 6
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 5);
        
        this.floors[0] = new Floor(this.gameValues, this.player, Floor.FloorName.Basement1, roomCounts);
    }

    private void setBasement2() {
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 1);
        
        this.floors[1] = new Floor(gameValues, player, Floor.FloorName.Basement2, roomCounts);
    }

    private void setCaves1() {
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 1);

        this.floors[2] = new Floor(gameValues, player, Floor.FloorName.Caves1, roomCounts);
    }

    private void setCaves2() {
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 1);
        
        this.floors[3] = new Floor(gameValues, player, Floor.FloorName.Caves2, roomCounts);
    }

    private void setTheDepths1() {
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 1);
        
        this.floors[4] = new Floor(gameValues, player, Floor.FloorName.TheDepths1, roomCounts);
    }

    private void setTheDepths2() {
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 1);
        
        this.floors[5] = new Floor(gameValues, player, Floor.FloorName.TheDepths2, roomCounts);
    }

    
    //Spawn, Regular, Shop, Treasure, Arcade, Boss, Secret;
    //Unlocked after beating Mom once
    private void setTheWomb() { //Around 20
        Map<Class<?>, Integer> roomCounts = new HashMap<Class<?>, Integer>();

        addGuaranteedFloors(roomCounts);

        roomCounts.put(RegularRoom.class, 1);

        this.floors[6] = new Floor(gameValues, player, Floor.FloorName.TheWomb, roomCounts);
    }

    private void addGuaranteedFloors(Map<Class<?>, Integer> roomCounts) {
        roomCounts.put(SpawnRoom.class, 0);
        roomCounts.put(ShopRoom.class, 1);
        roomCounts.put(TreasureRoom.class, 1); //Should require key everywhere except Basement1
        roomCounts.put(BossRoom.class, 1);
        roomCounts.put(SecretRoom.class, 1);
        roomCounts.put(ArcadeRoom.class, 0);
    }

    public void updateCurrentFloor() {
        currentFloor++;
    }

    public Floor getCurrentFloor() {
        return floors[currentFloor];
    }
}