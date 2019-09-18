import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Heart extends Pickup {
	

	double hearttype;
	
	public Heart()
	{
		super(0, 0, getImg(1.0));
		hearttype = 1.0;
	}
	
	public Heart(int x, int y, double type)
	{
		super(x, y, getImg(type));
		hearttype = type;
	}
	
	public Heart(int x, int y, double type, int cost)
	{
		super(x, y, getImg(type), cost);
		hearttype = type;
	}

	private static Image getImg(double type)
	{
		Image i = null;
		try {
			if (type == .5)
			{
				i = ImageIO.read(new File("Half_Red_Heart.png"));
			}	else
			{
				i = ImageIO.read(new File("Red_Heart.png"));
			}
			
		} catch (IOException e) {
		}
		
		return i;
	}

	@Override
	public boolean pickedUp(MainChar mc)
	{
		boolean ans = super.pickedUp(mc);
		if (ans) {	
			if (mc.getMaxHealth() - mc.getHealth() >= hearttype)
			{
				mc.addHealth(hearttype);
			}	else	{
				mc.addMoney(getCost());
				
				ans = false;
			}
			
		}
		return ans;
	}
}
