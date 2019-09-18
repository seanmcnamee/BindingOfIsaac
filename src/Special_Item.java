import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Special_Item extends Pickup{
	
	int type;
	
	public Special_Item()
	{
		super(0, 0, getImg(1));
		type = 1;
	}
	
	public Special_Item(int x, int y, int t)
	{
		super(x, y, getImg(t));
		type = t;
	}

	private static Image getImg(int t)
	{
		Image i = null;
		try {
			if (t == 1)
			{
				i = ImageIO.read(new File("10Bombs.png"));
			}	else if (t == 2)
			{
				i = ImageIO.read(new File("ExtraHeart.png"));
			}	else
			{//1 cent
				i = ImageIO.read(new File("The_Belt_Icon.png"));
			}
			
		} catch (IOException e) {
		}
		
		return i;
	}

	@Override
	public boolean pickedUp(MainChar mc)
	{
		boolean ans = super.pickedUp(mc);
		if (ans)
		{
			if (type == 1)
			{
				mc.addBomb(10);
			}	else if (type == 2)
			{
				mc.addMaxHealth(1);
				mc.addHealth(mc.getMaxHealth()-mc.getHealth());
			}	else
			{
				mc.addMaxVel(1);
			}
		}
		return ans;
	}
}
