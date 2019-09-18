import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends Pickup{

	public Key()
	{
		super(0, 0, getImg());
	}
	
	public Key(int x, int y)
	{
		super(x, y, getImg());
	}
	
	public Key(int x, int y, int cost)
	{
		super(x, y, getImg(), cost);
	}

	private static Image getImg()
	{
		Image i = null;
		try {
			i = ImageIO.read(new File("Key.png"));
		} catch (IOException e) {
		}
		
		return i;
	}

	@Override
	public boolean pickedUp(MainChar mc)
	{
		boolean ans = super.pickedUp(mc);
		if (ans) {	
			mc.addKeys(1);
		}
		return ans;
	}
}
