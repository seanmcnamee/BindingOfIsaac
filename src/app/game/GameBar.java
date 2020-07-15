package app.game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

import app.game.gamebar.MiniMap;
import app.game.gamebar.PlayerStatsPrinter;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

/**
 * GameBar
 */
public class GameBar {
    private GameValues gameValues;

    private MiniMap minimap;
    private PlayerStatsPrinter playerStatsPrinter;

    public GameBar(GameValues gameValues, GameField gameField) {
        this.gameValues = gameValues;
        this.minimap = new MiniMap(gameField.getHouse().getCurrentFloor(), gameValues);
        this.playerStatsPrinter = new PlayerStatsPrinter(gameField.getPlayer(), gameValues);
    }

    public void render(Graphics g) {
        //Use these values to figure out everything else
        this.gameValues.barXSize = gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_WIDTH;
        this.gameValues.barYSize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;

        double excessWidth = gameValues.frameWidth-(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        double excessHeight = gameValues.frameHeight-(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        this.gameValues.barXStart = excessWidth/2.0;//gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        this.gameValues.barYStart = excessHeight/2.0;
        

        drawBackgroundAndBorder(g);
        minimap.render(g);
        playerStatsPrinter.render(g);
    }

    private void drawBackgroundAndBorder(Graphics g) {
        Color background = new Color(25, 25, 25);
        Color border = Color.BLACK;

        g.setColor(background);
        g.fillRect((int)gameValues.barXStart, (int)gameValues.barYStart, (int)gameValues.barXSize, (int)gameValues.barYSize);

        final double thickness = .1;
        g.setColor(border);
        g.fillRect((int)gameValues.barXStart, (int)(gameValues.barYStart+gameValues.barYSize*(1-thickness)), (int)gameValues.barXSize, (int)(gameValues.barYSize*(thickness)));
    }

}