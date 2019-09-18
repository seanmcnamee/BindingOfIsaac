import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PoopObject extends RoomObject {

	private int life;
	
	public PoopObject()
	{
		super();
		life = 2;
	}
	
	public PoopObject(int x, int y)
	{
		super(x, y, findImage());
		life = 2;
	}
	
	public static Image findImage()
	{
		Image i = null;
		try {
		    i = ImageIO.read(new File("BasicPoop.png"));
		} catch (IOException e) {
		}
		return i;
	}
	
	public int getLife() { return life; }
	
	public void gotHit()
	{
		life --;
		Image i = null;
		
		if (life == 1)
		{
			try {
			    i = ImageIO.read(new File("HalfPoop.png"));
			} catch (IOException e) {
			}
		}	else
		{
			try {
			    i = ImageIO.read(new File("QuarterPoop.png"));
			} catch (IOException e) {
			}
		}
		
		super.setImage(i);
	}
}
