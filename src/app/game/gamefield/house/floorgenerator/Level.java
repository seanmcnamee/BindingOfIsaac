package app.game.gamefield.house.floorgenerator;

import app.game.gamefield.house.floorgenerator.Direction;
import app.game.gamefield.house.rooms.Room.Rooms;
import app.supportclasses.GameValues;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.awt.Point;

/**
 * Level
 */
public class Level {
    
    private Rooms[][] roomMap;
    private Point mapSize;

    public Level(Map<Rooms, Integer> roomCounts, GameValues gameValues) {
        this.mapSize = gameValues.MAPSIZE;
        roomMap = generateRooms(roomCounts);
        System.out.println("done making map");
        
    }

    public Rooms[][] getRoomMap() {
        return this.roomMap;
    }

    public void print() {
        for (Rooms[] roomArr : roomMap) {
            for (Rooms r : roomArr) {
                System.out.print("-");
                if (r!=null) {
                    System.out.print(r);
                }
            }
            System.out.println();
        }
        System.out.println("Yaaa");
    }

    private void quickPrint(Rooms[][] rooms) {
        for (Rooms[] roomArr : rooms) {
            for (Rooms r : roomArr) {
                System.out.print("-");
                if (r!=null) {
                    System.out.print(r);
                }
            }
            System.out.println();
        }
        System.out.println("Yaaa");
    }

    private Rooms[][] generateRooms(Map<Rooms, Integer> roomCounts) {
        Rooms[][] rooms = new Rooms[(int)mapSize.getX()][(int)mapSize.getY()];
        Queue<Point> locationsToExtend = new LinkedList<Point>();

        Point spawnLocation = pickSpawnLocation();
        setRoomAtLocationInMatrixAndQueue(Rooms.Spawn, spawnLocation, rooms, locationsToExtend);
        decreaseCountOf(Rooms.Spawn, roomCounts);

        System.out.println("Spawn was just made at + " + spawnLocation);

        while (!locationsToExtend.isEmpty() && (getSpecialtyLeftToAdd(roomCounts)+getRegularLeftToAdd(roomCounts)) > 0) {
            //Choose a certain amount to add
            System.out.println("Enter while loop... Queue: " + locationsToExtend.size());
            int specialtyLeft = getSpecialtyLeftToAdd(roomCounts);
            int regularLeftToExtend = locationsToExtend.size() + roomCounts.get(Rooms.Regular);
            Direction roomCurrentlyExtending = new Direction(locationsToExtend.remove());

            MutableBoolean upAvail = new MutableBoolean(isSpotAvailable(new Direction(roomCurrentlyExtending.upLocation()), rooms));
            MutableBoolean rightAvail = new MutableBoolean(isSpotAvailable(new Direction(roomCurrentlyExtending.rightLocation()), rooms));
            MutableBoolean downAvail = new MutableBoolean(isSpotAvailable(new Direction(roomCurrentlyExtending.downLocation()), rooms));
            MutableBoolean leftAvail = new MutableBoolean(isSpotAvailable(new Direction(roomCurrentlyExtending.leftLocation()), rooms));
            int totalAvailable = upAvail.intVal() + rightAvail.intVal() + downAvail.intVal() + leftAvail.intVal();

            Point rangeToAdd = new Point((int)Math.ceil((double)specialtyLeft/(double)regularLeftToExtend),
                                            Math.min(totalAvailable, specialtyLeft));
            System.out.println("Range to add: " + rangeToAdd);
            int amountToAdd = Math.max(Direction.rndInRange(rangeToAdd), Direction.rndInRange(rangeToAdd));
            System.out.println("Making " + amountToAdd + " rooms for " + rooms[(int)roomCurrentlyExtending.getLocation().getX()][(int)roomCurrentlyExtending.getLocation().getY()] + " at " + roomCurrentlyExtending.getLocation());

            for (int i = 0 ; i < amountToAdd; i++) {
                quickPrint(rooms);
                System.out.println("Room num: " + i);
                System.out.println("Available num: " + totalAvailable);
                System.out.println("Available bool: " + upAvail.getBoolean() + "-" + rightAvail.getBoolean() +"-" + downAvail.getBoolean() + "-" + leftAvail.getBoolean());
                if (roomCurrentlyExtending.oneAvailable(upAvail, rightAvail, downAvail, leftAvail)) {
                    Point newPoint = roomCurrentlyExtending.getRandomAdjacentFromAvailableAndUpdateBooleans(upAvail, rightAvail, downAvail, leftAvail);
                    Rooms newRoomType = getNextRoomType(roomCounts);
                    setRoomAtLocationInMatrixAndQueue(newRoomType, newPoint, rooms, locationsToExtend);
                    decreaseCountOf(newRoomType, roomCounts);
                    totalAvailable--;
                }
            }
        }

        return rooms;
    }

    private int getSpecialtyLeftToAdd(Map<Rooms, Integer> roomCounts) {
        return roomCounts.get(Rooms.Boss) + 
                roomCounts.get(Rooms.Treasure) + 
                roomCounts.get(Rooms.Shop) + 
                roomCounts.get(Rooms.Arcade);
    }

    private int getRegularLeftToAdd(Map<Rooms, Integer> roomCounts) {
        return roomCounts.get(Rooms.Regular);
    }

    private void setRoomAtLocationInMatrixAndQueue(Rooms type, Point location, Rooms[][] matrix, Queue<Point> locationsToExtend) {
        matrix[(int)location.getX()][(int)location.getY()] = type;

        if (type==Rooms.Spawn || type==Rooms.Regular) {
            locationsToExtend.add(location);
        }
    }

    private Point pickSpawnLocation() {
            final int edgeSize = 2;
            int lowX = edgeSize-1;
            int lowY = edgeSize-1;
            int highX = (int)mapSize.getX()-edgeSize-1;
            int highY = (int)mapSize.getY()-edgeSize-1;
    
            return new Point(Direction.rndInRange(new Point(lowX, highX)), Direction.rndInRange(new Point(lowY, highY)));
    }

    private void decreaseCountOf(Rooms type, Map<Rooms, Integer> roomCounts) {
        Integer count = roomCounts.get(type) - 1;
        roomCounts.put(type, count);
    }

    private boolean isSpotAvailable(Direction locationToTestAdjacents, Rooms[][] rooms) {
        //Make sure the count around that spot is <=1
        System.out.println("Checking if " + locationToTestAdjacents.getLocation() + " is available through its surroundings");
        Point roomLoc = locationToTestAdjacents.getLocation();
        if (isSpotInRange(roomLoc) && rooms[(int)roomLoc.getX()][(int)roomLoc.getY()]==null) {
            int adjacentCount = 0;

            adjacentCount += isSpotIsTaken(locationToTestAdjacents.upLocation(), rooms);
            adjacentCount += isSpotIsTaken(locationToTestAdjacents.rightLocation(), rooms);
            adjacentCount += isSpotIsTaken(locationToTestAdjacents.downLocation(), rooms);
            adjacentCount += isSpotIsTaken(locationToTestAdjacents.leftLocation(), rooms);
            if (adjacentCount <= 1) {
                return true;
            }
        }
        return false;
    }

    private int isSpotIsTaken(Point testLocation, Rooms[][] rooms) {
        return (isSpotInRange(testLocation) && isSpotTaken(testLocation, rooms))? 1:0;
    }

    /**
     * Assumes that the location being tested is within range
     */
    private boolean isSpotTaken(Point testLocation, Rooms[][] rooms) {
        return (rooms[(int)testLocation.getX()][(int)testLocation.getY()] != null);
    }

    private boolean isSpotInRange(Point testLocation) {
        //Make sure location is within bounds
        boolean xInRange = (testLocation.getX() >=0 && testLocation.getX() <= mapSize.getX()-1);
        boolean yInRange = (testLocation.getY() >=0 && testLocation.getY() <= mapSize.getY()-1);
        return xInRange && yInRange;
    }

    private Rooms getNextRoomType(Map<Rooms, Integer> roomCounts) {

        int specialty = getSpecialtyLeftToAdd(roomCounts);
        int regular = getRegularLeftToAdd(roomCounts);
        System.out.println("Specialty: " + specialty + ", regular: " + regular);
        if (specialty > regular) {
            return getSpecialtyType(roomCounts);
        }   else {
            return Rooms.Regular;
        }
    }

    private Rooms getSpecialtyType(Map<Rooms, Integer> roomCounts) {
        Rooms[] choices = new Rooms[getSpecialtyLeftToAdd(roomCounts)];
        for (int i = 0; i < roomCounts.get(Rooms.Boss); i++) {
            choices[i] = Rooms.Boss;
        }
        for (int i = 0; i < roomCounts.get(Rooms.Treasure); i++) {
            choices[i+roomCounts.get(Rooms.Boss)] = Rooms.Treasure;
        }
        for (int i = 0; i < roomCounts.get(Rooms.Shop); i++) {
            choices[i+roomCounts.get(Rooms.Boss)+roomCounts.get(Rooms.Treasure)] = Rooms.Shop;
        }

        for(int i = 0; i < choices.length; i++) {
            System.out.println("Choice " + i + ": " + choices[i]);
        }
        Rooms choice = choices[Direction.rndInRange(new Point(0, choices.length-1))];
        System.out.println("Returning specialty type: " + choice);
        return choice;
    }

    
}