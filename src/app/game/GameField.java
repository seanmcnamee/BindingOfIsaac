package app.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.awt.Font;
import java.awt.Color;

import app.game.gamefield.house.House;
import app.supportclasses.GameValues;
import app.game.gamefield.elements.mobiles.players.Player;
/**
 * GameField
 */
public class GameField {
    
    private GameValues gameValues;
    private Player player;

    //TODO make a map class and stuff
    private House house;
    

    public GameField(GameValues gameValues, Player player) {
        this.gameValues = gameValues;
        this.player = player;
        house = new House(this.gameValues, this.player);
    }

    public void tick() {
        house.getCurrentFloor().tick(); 
        //TODO does this need to be for the ROOM and not the FLOOR?
        
        //TODO add a check in here so that the next floor can be loaded
        //if *floorcomplete* then house.updateCurrentFloor()
    }

    public void render(Graphics g) {
        //this.gameValues.fieldXStart = gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        //this.gameValues.fieldYStart = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;
        this.gameValues.fieldXSize = gameValues.WIDTH_SCALE_1*(gameValues.gameScale);
        this.gameValues.fieldYSize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*(1-gameValues.GAME_BAR_HEIGHT);

        this.gameValues.singleSquareX = (gameValues.fieldXSize)/(gameValues.FIELD_X_SPACES+gameValues.WALL_THICKNESS*2);// - gameValues.WALL_THICKNESS*2;
        this.gameValues.singleSquareY = (gameValues.fieldYSize)/(gameValues.FIELD_Y_SPACES+gameValues.WALL_THICKNESS*2);// - gameValues.WALL_THICKNESS*2;

        double excessWidth = gameValues.frameWidth-(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        double excessHeight = gameValues.frameHeight-(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        this.gameValues.fieldXStart = excessWidth/2.0;//gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        this.gameValues.fieldYStart = gameValues.barYSize + excessHeight/2.0;

        //TODO add wall space accountability
        //TODO fix this adjustment when walls are added
        final double halfABlock = .5;
        this.gameValues.fieldXZero = gameValues.fieldXStart+(gameValues.singleSquareX*(gameValues.WALL_THICKNESS+halfABlock));
        this.gameValues.fieldYZero = gameValues.fieldYStart+(gameValues.singleSquareY*(gameValues.WALL_THICKNESS+halfABlock));

        house.getCurrentFloor().getCurrentRoom().render(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        g.drawString("(" + df.format(player.getX()) + ", " + df.format(player.getY()) + ")", (int)(gameValues.fieldXStart), (int)(gameValues.fieldYStart+.5*gameValues.singleSquareX));
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    public House getHouse() {
        return this.house;
    }
}