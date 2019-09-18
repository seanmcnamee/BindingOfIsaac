import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin extends Pickup{

	private int centValue;
	
	public Coin()
	{
		super(0, 0, getImg(1));
		centValue = 1;
	}
	
	public Coin(int x, int y, int v)
	{
		super(x, y, getImg(v));
		centValue = v;
	}

	private static Image getImg(int v)
	{
		Image i = null;
		try {
			if (v == 10)
			{
				i = ImageIO.read(new File("Dime.png"));
			}	else if (v == 5)
			{
				i = ImageIO.read(new File("Nickel.png"));
			}	else if (v == 2)
			{
				i = ImageIO.read(new File("TwoCents.png"));
			}	else
			{//1 cent
				i = ImageIO.read(new File("Penny.png"));
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
			mc.addMoney(centValue);
		}
		return ans;
	}
}
