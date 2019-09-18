package src;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomb extends Pickup{
	
	private int value;
	
	public Bomb()
	{
		super(0, 0, getImg(1));
		value = 1;
	}
	
	public Bomb(int x, int y, int v)
	{
		super(x, y, getImg(v));
		value = v;
	}
	
	public Bomb(int x, int y, int v, int cost)
	{
		super(x, y, getImg(v), cost);
		value = v;
	}

	private static Image getImg(int v)
	{
		Image i = null;
		try {
			if (v == 1)
			{
				i = ImageIO.read(new File("Bomb.png"));
			}	else
			{
				i = ImageIO.read(new File("TwoBombs.png"));
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
			mc.addBomb(value);
		}
		return ans;
	}
}
