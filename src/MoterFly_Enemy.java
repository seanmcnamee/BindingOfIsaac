//import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MoterFly_Enemy extends AttackFly_Enemy{

	public MoterFly_Enemy(int x, int y) {
		super(x, y);
		Image i1 = null;
		Image i2 = null;
		
		try {
			i1 = ImageIO.read(new File("Moter_Fly.png"));
			i2 = ImageIO.read(new File("Moter_Fly2.png"));
		} catch (IOException e) {
		}
		
		setImages(i1, i2);
	}
	
	
}
