import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpawnRoom extends Room{

	public SpawnRoom(boolean up, boolean down, boolean left, boolean right)
	{
		super(up, down, left, right, "SpawnRoom");
		Image img = null;
		try {
		    img = ImageIO.read(new File("tutorialbackground.png"));
		} catch (IOException e) {
		}
		setBackground(img);
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
}
