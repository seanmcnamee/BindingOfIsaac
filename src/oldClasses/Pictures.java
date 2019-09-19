package src;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Pictures {

	//private Image[] images;
	Image[] img;// = {getImage(getDocumentBase(), "image.jpg")};
	
	public Pictures()
	{
		img = new Image[1];
		
		img[0] = null;
		try {
		    img[0] = ImageIO.read(new File("image.png"));
		} catch (IOException e) {
		}
	}
	
	public Image getImg(int index)
	{
		return img[index];
	}
}
