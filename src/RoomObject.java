import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RoomObject {

	private int xPos, yPos;
	private Image img;
	public static final int xSize = 100, ySize = 100;
	
	
	public RoomObject()
	{
		xPos = 0;
		yPos = 0;
		
		img = null;
		try {
		    img = ImageIO.read(new File("BasicRock.png"));
		} catch (IOException e) {
		}
		
	}
	
	public RoomObject(int x, int y, int t)
	{
		xPos = x;
		yPos = y;
		
		img = null;
		try {
			if (t == 0)
			{
				img = ImageIO.read(new File("BasicRock.png"));
			}	else if (t == 1)
			{
				img = ImageIO.read(new File("BasicRock2.png"));
			}	else
			{
				img = ImageIO.read(new File("Greed.png"));
			}
		} catch (IOException e) {
		}
		//System.out.println("Creating Rock");
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(img, xPos, yPos, xSize, ySize, null);
	}
	
	public RoomObject(int x, int y, Image i)
	{
		xPos = x;
		yPos = y;
		
		img = i;
	}
	
	
	public int getXSize() { return xSize; }
	public int getYSize() { return ySize; }
	
	public int getX() { return xPos; }
	public int getY() { return yPos; }
	
	public void setImage(Image i) { img = i; }
	
	
}
