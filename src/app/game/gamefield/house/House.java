package app.game.gamefield.house;

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

        this.floors[0] = new Floor();
    }

    private void setBasement2() {

        this.floors[0] = new Floor();
    }

    private void setCaves1() {

        this.floors[0] = new Floor();
    }

    private void setCaves2() {

        this.floors[0] = new Floor();
    }

    private void setTheDepths1() {

        this.floors[0] = new Floor();
    }

    private void setTheDepths2() {

        this.floors[0] = new Floor();
    }

    
    //Spawn, Regular, Shop, Treasure, Arcade, Boss, Secret;
    //Unlocked after beating Mom once
    private void setTheWomb() {
        
        this.floors[0] = new Floor();
    }

    public void updateCurrentFloor() {
        currentFloor++;
    }

    public Floor getCurrentFloor() {
        return floors[currentFloor];
    }
}