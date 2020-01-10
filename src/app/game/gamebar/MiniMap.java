package app.game.gamebar;

import app.game.gamefield.elements.rendering.DrawingCalculator;
import app.game.gamefield.house.Floor;
import app.game.gamefield.house.rooms.Room;
import app.game.gamefield.house.rooms.types.RegularRoom;
import app.supportclasses.GameValues;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Point;

/**
 * MiniMap
 */
public class MiniMap extends DrawingCalculator{
    private Floor floor;
    private Point2D.Double adjustedMapSize;
    private Point2D.Double singleRoomSize = new Point2D.Double(1.0, 1.0);;
    private final Point2D.Double generalIconSize = new Point2D.Double(1, 2.5);
    private final double outsideEdge = .5;
    
    public MiniMap(Floor f, GameValues gameValues) {
        super(gameValues);
        init(f);
    }

    public void setNewFloor(Floor newFloor) {
        init(newFloor);
    }

    private void init(Floor floor) {
        this.floor = floor;
        
        adjustedMapSize = new Point2D.Double(floor.getSizeOfMap().getX()+outsideEdge*3, floor.getSizeOfMap().getY()+outsideEdge*3);
    }

    public void render(Graphics g) {
        double miniMapXSize = gameValues.barXSize*gameValues.MINIMAP_X_SIZE;
        double miniMapYSize = gameValues.barYSize*gameValues.MINIMAP_Y_SIZE;

        Point2D.Double miniMapPixelSize = new Point2D.Double(miniMapXSize, miniMapYSize);
        Point2D.Double blockSize = findSingleBlockSize(miniMapPixelSize, adjustedMapSize);

        Point2D.Double miniMapStart = new Point2D.Double(gameValues.barXStart+blockSize.getX()*2*outsideEdge, gameValues.barYStart+blockSize.getY()*2*outsideEdge);

        Color emptyRoom = new Color(100, 100, 100);
        Color borderOfRoom = Color.WHITE;//new Color(25, 25, 25);

        for (Room room : this.floor.getRooms()) {
            int xPos = findPixelLocation(room.getLocation().getX(), singleRoomSize.getX(), miniMapStart.getX(), blockSize.getX());
            int yPos = findPixelLocation(room.getLocation().getY(), singleRoomSize.getY(), miniMapStart.getY(), blockSize.getY());
            int xSize = findPixelSize(singleRoomSize.getX(), blockSize.getX());
            int ySize = findPixelSize(singleRoomSize.getY(), blockSize.getY());

            g.setColor(emptyRoom);
            g.fillRect(xPos, yPos, xSize, ySize);
            g.setColor(borderOfRoom);
            g.drawRect(xPos, yPos, xSize, ySize);
        }

        for (Room room : this.floor.getRooms()) {
            if (room.getClass()!=RegularRoom.class) {

                Point2D.Double iconSize = new Point2D.Double(generalIconSize.getX()*(room.getIcon().getWidth()/(double)gameValues.ICON_SPRITE_SHEET_BOX_SIZE),
                                                            generalIconSize.getY()*(room.getIcon().getHeight()/(double)gameValues.ICON_SPRITE_SHEET_BOX_SIZE));

                int xPos = findPixelLocation(room.getLocation().getX(), iconSize.getX(), miniMapStart.getX(), blockSize.getX());
                int yPos = findPixelLocation(room.getLocation().getY(), iconSize.getY(), miniMapStart.getY(), blockSize.getY());
                
                
                int xSize = findPixelSize(iconSize.getX(), blockSize.getX());
                int ySize = findPixelSize(iconSize.getY(), blockSize.getY());
                g.drawImage(room.getIcon(), xPos, yPos, xSize, ySize, null); 
            }   
        }
        
    }
}