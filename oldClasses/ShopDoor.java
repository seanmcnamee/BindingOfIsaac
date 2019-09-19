package src;

import java.awt.Graphics;
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShopDoor extends Door{

	public ShopDoor(boolean i, int p)
	{
		super(i, p, setImages());
	}
	
	private static BufferedImage[] setImages()
	{
		BufferedImage[] image = new BufferedImage[3];
		image[0] = null;
		image[1] = null;
		try {
		    image[0] = ImageIO.read(new File("NormalOpenDoor.png"));
		} catch (IOException e) {
		}
		try {
		    image[1] = ImageIO.read(new File("NormalClosedDoor.png"));
		} catch (IOException e) {
		}
		try {
		    image[2] = ImageIO.read(new File("ShopDoor.png"));
		} catch (IOException e) {
		}
		return image;
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
	
}
