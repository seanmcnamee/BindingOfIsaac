package app;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * SpriteSheet, deals with all the pictures for the game
 */
public class SpriteSheet {
    private BufferedImage image;

    public SpriteSheet(String path) {
        image = loadImage(path);
    }

    public BufferedImage loadImage(String path) {
        BufferedImage img = null;
		try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            System.out.println("Can't find picture at: " + path);
            e.printStackTrace();
        }
		return img;
    }
    
    /**
	 * returns an image of designated size and at certain position within the spritesheet.
	 * All start with 0,0
	 * @param col - x position
	 * @param row - y position
	 * @param width - width of image
     * @param height - height of image
     * @param SIZE - number of pixels defined by '1' unit
	 */
	public BufferedImage grabImage(int col, int row, int width, int height, int size)
	{
		//square is the size of each side of a 'box' in the spritesheet
		//for example, a 4x4 spritesheet with square 8 would be 32pixels x 32pixels
		//So square should technically be the smallest size of a picture you use in your game,
		//But other things should be multiples of that size.
		//The top left image is at 0,0 and moves like an array does in rows and collums
		
		
		BufferedImage img = image.getSubimage((col*size),(row*size),size*width,size*height);
		return img;
	}
}