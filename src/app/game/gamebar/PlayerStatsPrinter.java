package app.game.gamebar;

import app.game.gamefield.elements.mobiles.players.Player;
import app.game.gamefield.elements.rendering.drawableSupport.DrawingCalculator;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

/**
 * PlayerStatsPrinter
 */
public class PlayerStatsPrinter {
    private Player player;
    private GameValues gameValues;
    private DrawingCalculator calculator;

    private BufferedImage coins, bombs, keys, emptyHearts, halfHearts, fullHearts, arrowsText, spaceText;

    private final Point2D.Double pStatsSize = new Point2D.Double(18.0, 4.5); //TODO fix these numbers
    
    private final Point2D.Double holderSize = new Point2D.Double(2.5, 3.0);
    private final BufferedImage[] cbkImages;
    private final BufferedImage[] asImages;

    private final Point2D.Double[] cbkLocations = {
        new Point2D.Double(0, .8*pStatsSize.getY()/4.0), //coin
        new Point2D.Double(-.05, 1.8*pStatsSize.getY()/4.0), //bomb
        new Point2D.Double(.1, 2.8*pStatsSize.getY()/4.0) //key
    };

    private final Point2D.Double[] cbkSizes = {
        new Point2D.Double(1.0, 1.1), //coin
        new Point2D.Double(1.1, 1.1), //bomb
        new Point2D.Double(.8, 1.1), //key
    };

    private final Point2D.Double[] asLocations = {
        new Point2D.Double(3.0, 1.2), //arrows
        new Point2D.Double(6.0, 1.2) //specials / space
    };
    
    public PlayerStatsPrinter(Player p, GameValues gameValues) {
        this.player = p;
        this.gameValues = gameValues;
        this.calculator = new DrawingCalculator();
        loadImages();
        this.cbkImages = new BufferedImage[3];
        this.cbkImages[0] = coins;
        this.cbkImages[1] = bombs;
        this.cbkImages[2] = keys;
        this.asImages = new BufferedImage[2];
        this.asImages[0] = arrowsText;
        this.asImages[1] = spaceText;
    }

    private void loadImages() {
        SpriteSheet generalPictures = new SpriteSheet(gameValues.GENERAL_GAMEBAR_SPRITE_SHEET);
        this.arrowsText = generalPictures.shrink(generalPictures.grabImage(3,
                        0, 2, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.spaceText = generalPictures.shrink(generalPictures.grabImage(3,
                        1, 2, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.fullHearts = generalPictures.shrink(generalPictures.grabImage(2,
                        0, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.halfHearts = generalPictures.shrink(generalPictures.grabImage(1,
                        0, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.emptyHearts = generalPictures.shrink(generalPictures.grabImage(0,
                        0, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.coins = generalPictures.shrink(generalPictures.grabImage(0,
                        1, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.bombs = generalPictures.shrink(generalPictures.grabImage(1,
                        1, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
        this.keys = generalPictures.shrink(generalPictures.grabImage(2,
                        1, 1, 1, gameValues.GENERAL_GAMEBAR_SPRITE_SHEET_BOX_SIZE));
    }

    public void render(Graphics g) {
        double pStatsXSize = gameValues.barXSize*gameValues.PLAYERSTATS_X_SIZE;
        double pStatsYSize = gameValues.barYSize*gameValues.PLAYERSTATS_Y_SIZE;

        Point2D.Double pStatsStart = new Point2D.Double(gameValues.barXStart+(gameValues.barXSize-pStatsXSize), gameValues.barYStart+(gameValues.barYSize-pStatsYSize));
        
        Point2D.Double pStatsPixelSize = new Point2D.Double(pStatsXSize, pStatsYSize);
        Point2D.Double blockSize = calculator.findSingleBlockSize(pStatsPixelSize, pStatsSize);

        Color mainTextColor = Color.WHITE;
        Color lifeColor = Color.RED;
        Color charged = new Color(100, 100, 100);
        Color itemBackdrop = new Color(50, 50, 50);
        Color uncharged, border;
        uncharged = border = Color.BLACK;

        //Coins, Bombs, and Keys
        g.setColor(mainTextColor);
        double fontsize = 15.1522*gameValues.gameScale - .4976;
        g.setFont(new Font(gameValues.gameFont.getFontName(), Font.BOLD, (int)fontsize)); //TODO create a formula for gameScale to font
        DecimalFormat df = new DecimalFormat("00.##");
        int[] cbkStats = {player.getMoney(), player.getBombs(), player.getKeys()};
        for (int i = 0; i < cbkLocations.length; i++) {
            int xSize = calculator.findPixelSize(cbkSizes[i].getX(), blockSize.getX());
            int ySize = calculator.findPixelSize(cbkSizes[i].getY(), blockSize.getY());
            int xPos = calculator.findPixelLocation(cbkLocations[i].getX(), 1, pStatsStart.getX(), blockSize.getX());
            int xStringPos = calculator.findPixelLocation((int)(.5+cbkLocations[i].getX())+1.1, 1, pStatsStart.getX(), blockSize.getX());
            int yPos = calculator.findPixelLocation(cbkLocations[i].getY(), 1, pStatsStart.getY(), blockSize.getY());
            int yStringPos = calculator.findPixelLocation(cbkLocations[i].getY()+.9, 1, pStatsStart.getY(), blockSize.getY());
            
            g.drawImage(cbkImages[i], xPos, yPos, xSize, ySize, null);

            g.drawString("X " + df.format(cbkStats[i]), xStringPos, yStringPos);
        }

        //Arrows and Specialties
        double borderSize = .2;
        int xSize = calculator.findPixelSize(holderSize.getX(), blockSize.getX());
        int xFillSize = calculator.findPixelSize(holderSize.getX()-borderSize*2, blockSize.getX());
        int ySize = calculator.findPixelSize(holderSize.getY(), blockSize.getY());
        int yFillSize = calculator.findPixelSize(holderSize.getY()-borderSize*2, blockSize.getY());

        int imageXSize = xFillSize;
        int imageYSize = calculator.findPixelSize(.5, blockSize.getY());

        for (int i = 0; i < asLocations.length; i++) {
            int xPos = calculator.findPixelLocation(asLocations[i].getX(), 1, pStatsStart.getX(), blockSize.getX());
            int xFillPos = calculator.findPixelLocation(asLocations[i].getX()+borderSize, 1, pStatsStart.getX(), blockSize.getX());
            int yPos = calculator.findPixelLocation(asLocations[i].getY(), 1, pStatsStart.getY(), blockSize.getY());
            int yFillPos = calculator.findPixelLocation(asLocations[i].getY()+borderSize, 1, pStatsStart.getY(), blockSize.getY());
            
            int imageXPos = calculator.findPixelLocation(asLocations[i].getX()+borderSize, 1, pStatsStart.getX(), blockSize.getX());
            int imageYPos = calculator.findPixelLocation(asLocations[i].getY()-.15, 1, pStatsStart.getY(), blockSize.getY());

            g.setColor(border);
            g.fillRect(xPos, yPos, xSize, ySize);
            g.setColor(itemBackdrop);
            g.fillRect(xFillPos, yFillPos, xFillSize, yFillSize);

            g.setColor(mainTextColor);
            g.drawImage(asImages[i], imageXPos, imageYPos, imageXSize, imageYSize, null);
        }
        


    }

    public void printCoinBombKey(Graphics g) {

    }
}