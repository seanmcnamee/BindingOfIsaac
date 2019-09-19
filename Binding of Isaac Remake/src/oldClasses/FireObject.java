package src;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FireObject extends RoomObject{

	private int life;
	private long fireTouchTime;
	
	public FireObject()
	{
		super();
		life = 2;
	}
	
	public FireObject(int x, int y)
	{
		super(x, y, findImage());
		life = 2;
	}
	
	public static Image findImage()
	{
		Image i = null;
		try {
		    i = ImageIO.read(new File("Fireplace.png"));
		} catch (IOException e) {
		}
		return i;
	}
	
	public int getLife() { return life; }
	public long getLastTouch() { return fireTouchTime; }
	public void setLastTouch(long t) { fireTouchTime = t; }
	
	public void gotHit()
	{
		life --;
		Image i = null;
		
		if (life == 1)
		{
			try {
			    i = ImageIO.read(new File("HalfFire.png"));
			} catch (IOException e) {
			}
		}	else
		{
			try {
			    i = ImageIO.read(new File("QuarterFire.png"));
			} catch (IOException e) {
			}
		}
		
		super.setImage(i);
	}
}

