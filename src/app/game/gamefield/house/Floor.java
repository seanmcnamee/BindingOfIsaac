package app.game.gamefield.house;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import app.game.gamefield.elements.mobiles.players.Player;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.Traversable;
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
    private Integer roomIndex = 0;
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
        System.out.println("rooms.length: " + rooms.length + ", finalIndex: " + roomIndex);
    }

    private int getTotal(Map<Room.Rooms, Integer> roomCounts) {
        int sum = 0;
        for (Room.Rooms roomType : Room.Rooms.values()) {
            sum += roomCounts.get(roomType);
        }
        return sum;
    }

    private void generateFloorMap(Map<Room.Rooms, Integer> roomCounts) {
        Queue<Room> unconnectedRooms = new LinkedList<Room>();

        Room startRoom = new SpawnRoom(gameValues, player, this.floorName==FloorName.Basement1, pickRandomSpawnStart());
        System.out.println("Spawn is at " + startRoom.getLocation());
        this.rooms[roomIndex] = startRoom;
        roomIndex++;
        System.out.println("-spawn--Roomindex: " + roomIndex);
        unconnectedRooms.add(startRoom);
        currentRoom = startRoom;

        while (!unconnectedRooms.isEmpty()) {
            //Take off stack
            Room unconnectedRoom = unconnectedRooms.remove();

            //Add neighbors
            System.out.println("Creating neigbors for " + unconnectedRoom.getLocation());
            Room[] neighbors = createNeighbors(roomCounts, unconnectedRooms, unconnectedRoom);
            unconnectedRoom.setSurroundingAndLock(neighbors[0], neighbors[1], neighbors[2], neighbors[3]);
        }

        // Pick location for starting room
        // Set current room to starting room
        // Add rooms procedurally

        this.currentRoom = rooms[0];
    }

    private Point pickRandomSpawnStart() {
        final int edgeSize = 2;
        int lowX = edgeSize;
        int lowY = edgeSize;
        int highX = (int)sizeOfMap.getX()-edgeSize;
        int highY = (int)sizeOfMap.getY()-edgeSize;

        return new Point(rndInRange(new Point(lowX, highX)), rndInRange(new Point(lowY, highY)));
    }

    private int rndInRange(Point range) {
        int min = (int)range.getX();
        int max = (int)range.getY();
        return (int) (Math.random()*(max-min+1)+min);
    }

    

    private Room[] createNeighbors(Map<Room.Rooms, Integer> roomCounts, Queue<Room> unconnectedRooms, Room room) {
        //Start with the current surrounding rooms
        final int REGULAR = 0, SPECIAL = 1;
        Traversable[] surroundingLocations = getSurroundingLocations(room);
        Room[] surroundingRooms = findRoomsAt(surroundingLocations);
        boolean[][] availableNeighboringSpaces = findAvailableSpaces(surroundingLocations);

        System.out.println("Available surrounding: " + availableNeighboringSpaces[REGULAR][0]+" "+availableNeighboringSpaces[REGULAR][1]+" "+availableNeighboringSpaces[REGULAR][2]+" "+availableNeighboringSpaces[REGULAR][3]);
        System.out.println("Special avail surrounding: " + availableNeighboringSpaces[SPECIAL][0]+" "+availableNeighboringSpaces[SPECIAL][1]+" "+availableNeighboringSpaces[SPECIAL][2]+" "+availableNeighboringSpaces[SPECIAL][3]);


        //Figure out how many rooms to add
        int specialtyLeft = getSpecialtyLeftToAdd(roomCounts);
        int min = (int)Math.ceil((double)specialtyLeft/(getRegularLeftToAdd(roomCounts) + unconnectedRooms.size()));
        int max = Math.min(getAvailable(availableNeighboringSpaces[REGULAR]), specialtyLeft);
        min = Math.min(min, max);
        int roomsToCreate = rndInRange(new Point(min, max));

        System.out.println("Range to add: " + min + "-" + max+ ". Adding " + roomsToCreate);
        //For each of them, keep choosing the larger of the regular or specialty pool
        //add to surrounding room array
        for (int i = 0; i < roomsToCreate; i++) {
            System.out.println("\t" + i);
            int regularAvailable = getAvailable(availableNeighboringSpaces[REGULAR]);
            int specialtyAvailable = getAvailable(availableNeighboringSpaces[SPECIAL]);
            
            int regular = regularAvailable*getRegularLeftToAdd(roomCounts);
            int specialty = specialtyAvailable*getSpecialtyLeftToAdd(roomCounts);
            int first = 1;
            int last = (specialty>regular)? specialtyAvailable:regularAvailable;

            int nthOpenLocation = rndInRange(new Point(first, last));
            int locationIndex = getIndexInNeighborArray(nthOpenLocation, availableNeighboringSpaces[(specialty>regular)? SPECIAL:REGULAR]);
            
            System.out.println("Reg and Special:");
            System.out.println("\tRegAvail: " + regularAvailable);
            System.out.println("\tSpecialAvail: " + specialtyAvailable);
            System.out.println("\tregular vs specialty: " + regular + "-" + specialty);
            System.out.println("\tLocation index: " + locationIndex);
            Point newRoomLocation = getNewRoomLocation(room, locationIndex);
            availableNeighboringSpaces[REGULAR][locationIndex] = false;
            availableNeighboringSpaces[SPECIAL][locationIndex] = false;

            Room newRoom;
            if (specialty>regular) {
                //Add a random specialty
                newRoom = randomSpecialtyRoom(roomCounts, room, newRoomLocation);
            }   else {
                //Add a regular
                newRoom = new RegularRoom(gameValues, player, newRoomLocation);
                roomCounts.put(Room.Rooms.Regular, roomCounts.get(Room.Rooms.Regular)-1);
                unconnectedRooms.add(newRoom);
            }
            this.rooms[roomIndex] = newRoom;
            this.roomIndex++;
            surroundingRooms[locationIndex] = newRoom;
        }

        //return complete neighbor set
        return surroundingRooms;
    }

    private Traversable[] getSurroundingLocations(Traversable room) {
        Traversable[] surroudningRoomLocations = {new Traversable(room.getLocationAbove()), 
            new Traversable(room.getLocationRight()),
            new Traversable(room.getLocationBelow()), 
            new Traversable(room.getLocationLeft())};
        return surroudningRoomLocations;
    }

    private Room[] findRoomsAt(Traversable[] traversables) {
        Room[] neighboringRooms = new Room[traversables.length];
        
        for (int t = 0; t < traversables.length; t++) {
            for (int r = 0; r < roomIndex; r++) {
                if (traversables[t].getLocation()==rooms[r].getLocation()) {
                    neighboringRooms[t] = rooms[r];
                    break; //TODO hopefully this doesn't break from the method?
                }
            }
        }

        return neighboringRooms;
    }

    private boolean[][] findAvailableSpaces(Traversable[] traversables) {
        final int REGULAR = 0, SPECIAL = 1;
        boolean[][] neighboringRooms = new boolean[2][traversables.length];
        
        for (int t = 0; t < traversables.length; t++) {
            Traversable currentRoomToCheck = traversables[t];
            System.out.println("Checking location: " + currentRoomToCheck.getLocation()  + " for eligibility");
            neighboringRooms[SPECIAL][t] = isEligibleSpecialtyLocation(currentRoomToCheck);
            neighboringRooms[REGULAR][t] = isEligibleRoom(currentRoomToCheck);
        }

        return neighboringRooms;
    }

    private boolean isEligibleSpecialtyLocation(Traversable room) {
        return isEligibleRoom(room) && (findSurroundingRoomCount(room) <= 1);
    }

    private boolean isEligibleRoom(Traversable room) {
        return isWithinMap(room.getLocation()) && !isRoomAt(room.getLocation()) && !areSurroundingRoomsLocked(room);
    }

    private boolean isWithinMap(Point location) {
        boolean xGood = location.getX()>=0 && location.getX()<=sizeOfMap.getX();
        boolean yGood = location.getY()>=0 && location.getY()<=sizeOfMap.getY();
        return xGood&&yGood;
    }

    private boolean isRoomAt(Point location) {
        for (int r = 0; r < roomIndex; r++) {
            if (this.rooms[r].getLocation().getX()==location.getX() && this.rooms[r].getLocation().getY()==location.getY()) {
                return true;
            }
        }
        return false;
    }

    private boolean areSurroundingRoomsLocked(Traversable room) {
        Traversable[] surroundingLocations = getSurroundingLocations(room);

        for (int r = 0; r < roomIndex; r++) {
            for (Traversable t : surroundingLocations) {
                if (rooms[r].getLocation().getX() == t.getLocation().getX() && rooms[r].getLocation().getY() == t.getLocation().getY()) {
                    if (rooms[r].isLocked()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private int findSurroundingRoomCount(Traversable room) {
        int count = 0;
        Traversable[] surroundingLocations = getSurroundingLocations(room);
        for (int r = 0; r < roomIndex; r++) {
            for (Traversable t : surroundingLocations) {
                if (rooms[r].getLocation().getX() == t.getLocation().getX() && rooms[r].getLocation().getY() == t.getLocation().getY()) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getSpecialtyLeftToAdd(Map<Room.Rooms, Integer> roomCounts) {
        return roomCounts.get(Room.Rooms.Boss) + 
                roomCounts.get(Room.Rooms.Treasure) + 
                roomCounts.get(Room.Rooms.Shop);
    }

    private int getRegularLeftToAdd(Map<Room.Rooms, Integer> roomCounts) {
        return roomCounts.get(Room.Rooms.Regular);
    }

    private int getAvailable(boolean[] spaces) {
        int count = 0;
        for (boolean b : spaces) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    private int getIndexInNeighborArray(int nthOpenLocation, boolean[] available) {
        int alterredIndex = nthOpenLocation;
        for (int i = 0; i < alterredIndex; i++) {
            if (!available[i]) {
                alterredIndex++;
            }
        }
        return alterredIndex-1;
    }

    private Point getNewRoomLocation(Room r, int indexLocation) {
        switch(indexLocation) {
            case 0:
                return r.getLocationAbove();
            case 1:
                return r.getLocationRight();
            case 2:
                return r.getLocationBelow();
            case 3:
                return r.getLocationLeft();
        }
        throw new NullPointerException("An Error with the Nth Open Location!");
    }

    private Room randomSpecialtyRoom(Map<Room.Rooms, Integer> roomCounts, Room room, Point location) {
        Room.Rooms[] choices = new Room.Rooms[getSpecialtyLeftToAdd(roomCounts)];
        for (int i = 0; i < roomCounts.get(Room.Rooms.Boss); i++) {
            choices[i] = Room.Rooms.Boss;
        }
        for (int i = 0; i < roomCounts.get(Room.Rooms.Treasure); i++) {
            choices[i+roomCounts.get(Room.Rooms.Boss)] = Room.Rooms.Treasure;
        }
        for (int i = 0; i < roomCounts.get(Room.Rooms.Shop); i++) {
            choices[i+roomCounts.get(Room.Rooms.Boss)+roomCounts.get(Room.Rooms.Treasure)] = Room.Rooms.Shop;
        }

        for(int i = 0; i < choices.length; i++) {
            System.out.println("Choice " + i + ": " + choices[i]);
        }
        Room.Rooms choice = choices[rndInRange(new Point(0, choices.length-1))];
        System.out.println("Specialty room type: " + choice);
        roomCounts.put(choice, roomCounts.get(choice)-1);
        return createSpecialtyRoom(choice, room, location);
    }

    private Room createSpecialtyRoom(Room.Rooms type, Room room, Point location) {
        Traversable[] surroundingLocations = getSurroundingLocations(room);
        Room[] surroundingRooms = findRoomsAt(surroundingLocations);
        switch(type) {
            case Boss:
                return new BossRoom(this.gameValues, this.player, location, surroundingRooms[0], surroundingRooms[1], surroundingRooms[2], surroundingRooms[3]);
            case Treasure:
                return new TreasureRoom(this.gameValues, this.player, location, surroundingRooms[0], surroundingRooms[1], surroundingRooms[2], surroundingRooms[3]);
            case Shop:
                return new ShopRoom(this.gameValues, this.player, location, surroundingRooms[0], surroundingRooms[1], surroundingRooms[2], surroundingRooms[3]);
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

    public Point getSizeOfMap() {
        return sizeOfMap;
    }
}