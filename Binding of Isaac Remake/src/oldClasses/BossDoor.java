package src;

import java.awt.Graphics;
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BossDoor extends Door{
	
	public BossDoor(boolean i, int p)
	{
		super(i, p, setImages());
	}
	
	private static BufferedImage[] setImages()
	{
		BufferedImage[] image = new BufferedImage[2];
		image[0] = null;
		image[1] = null;
		try {
		    image[0] = ImageIO.read(new File("BossDoorOpen.png"));
		} catch (IOException e) {
		}
		try {
		    image[1] = ImageIO.read(new File("BossDoorClosed.png"));
		} catch (IOException e) {
		}
		return image;
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
	
}
