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
    private GameField gameField;
    private BufferedImage coins, bombs, keys, emptyHearts, halfHearts, fullHearts;
    private MiniMap minimap;
    private PlayerStatsPrinter playerStatsPrinter;


    public GameBar(GameValues gameValues, GameField gameField) {
        this.gameValues = gameValues;
        this.gameField = gameField;
        //this.minimap = new MiniMap(gameField.getLevels().getCurrentFloor());  //TODO add this
        //this.playerStatsPrinter = new PlayerStatsPrinter(gameField.getPlayer()); //TODO add this
        loadImages();
    }

    private void loadImages() {
        SpriteSheet generalPictures = new SpriteSheet(gameValues.GENERAL_GAMEBAR_SPRITE_SHEET);
        this.fullHearts = generalPictures.shrink(generalPictures.grabImage(2,
                        0, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.coins = generalPictures.shrink(generalPictures.grabImage(0,
                        1, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.bombs = generalPictures.shrink(generalPictures.grabImage(1,
                        1, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.keys = generalPictures.shrink(generalPictures.grabImage(2,
                        1, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
    }

    public void render(Graphics g) {
        //Use these values to figure out everyting else
        this.gameValues.barXSize = gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_WIDTH;
        this.gameValues.barYSize = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.GAME_BAR_HEIGHT;

        double excessWidth = gameValues.frameWidth-(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        double excessHeight = gameValues.frameHeight-(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        this.gameValues.barXStart = excessWidth/2.0;//gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.GAME_BAR_WIDTH)*.5;
        this.gameValues.barYStart = excessHeight/2.0;
        

        drawBackgroundAndBorder(g);
        //miniMap.render(g);
        //playerStatsPrinter.render(g);
    }

    private void drawBackgroundAndBorder(Graphics g) {
        Color background = new Color(25, 25, 25);
        Color border = Color.BLACK;

        g.setColor(background);
        g.fillRect((int)gameValues.barXStart, (int)gameValues.barYStart, (int)gameValues.barXSize, (int)gameValues.barYSize);

        final double thickness = .1;
        g.setColor(border);
        g.fillRect(0, (int)(gameValues.barYSize*(1-thickness)), (int)gameValues.barXSize, (int)(gameValues.barYSize*(thickness)));
    }

}