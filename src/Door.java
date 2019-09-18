import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Door {

	//private String pos;
	private boolean locked;
	private boolean enemyLocked;
	private BufferedImage[] img;
	//private BufferedImage doorImg;
	private Point pos; //can do sin/cos for position
	private int xSize, ySize;
	private final int doorheight = 65, doorwidth = 120;
	//Seems as if this is (y, x)
	private final Point p0 = new Point(0, Isaac.WIDTH/2-(doorwidth/2)), //Top
			p1 = new Point(Isaac.HEIGHT/2-(doorheight)+10, -5), //Left
			p2 = new Point(Isaac.HEIGHT-(doorheight), Isaac.WIDTH/2-(doorwidth/2)), //Bottom
			p3 = new Point(Isaac.HEIGHT/2-(doorheight)+10, Isaac.WIDTH-(doorheight)+5);//Right
	
	
	
	public Door()
	{
		locked = false;
		enemyLocked = true;
		
		img = new BufferedImage[2];
		img[0] = null;
		img[1] = null;
		
		try {
		    img[0] = ImageIO.read(new File("NormalOpenDoor.png"));
		} catch (IOException e) {
		}
		
		try {
		    img[1] = ImageIO.read(new File("NormalClosedDoor.png"));
		} catch (IOException e) {
		}
		img[2] = null;
		
		pos = new Point(0,0);
	}
	
	public Door(boolean l, int p)
	{
		locked = l;
		enemyLocked = true;
		
		img = new BufferedImage[2];
		img[0] = null;
		img[1] = null;
		
		try {
		    img[0] = ImageIO.read(new File("NormalOpenDoor.png"));
		} catch (IOException e) {
		}
		
		try {
		    img[1] = ImageIO.read(new File("NormalClosedDoor.png"));
		} catch (IOException e) {
		}
		
		img[0] = rotate(img[0], p);
		img[1] = rotate(img[1], p);
		

		if (p == 0)//TOP
		{
			pos = p0;
			xSize = doorwidth; ySize = doorheight;
		}	else if (p == 2)	
		{ //Bottom
			xSize = doorwidth; ySize = doorheight;
			pos = p2;
		}	else if (p == 1)	
		{ //LEFT
			pos = p1;
			xSize = doorheight; ySize = doorwidth;
		}	else 
		{	//RIGHT
			pos = p3;
			xSize = doorheight; ySize = doorwidth;
		}
	}
	
	public Door(boolean l, int p, BufferedImage[] i)
	{
		locked = l;
		enemyLocked = true;
		
		img = i;
		
		img[0] = rotate(img[0], p);
		img[1] = rotate(img[1], p);
		if (img.length > 2)
		{
			img[2] = rotate(img[2], p);
		}
		

		if (p == 0)//TOP
		{
			pos = p0;
			xSize = doorwidth; ySize = doorheight;
		}	else if (p == 2)	
		{ //Bottom
			xSize = doorwidth; ySize = doorheight;
			pos = p2;
		}	else if (p == 1)	
		{ //LEFT
			pos = p1;
			xSize = doorheight; ySize = doorwidth;
		}	else 
		{	//RIGHT
			pos = p3;
			xSize = doorheight; ySize = doorwidth;
		}
	}
/*
	public Door(boolean l, int p, BufferedImage[] i, int largerWidth, int largerHeight)
	{
		locked = l;
		
		img = i;
		
		img[0] = rotate(img[0], p);
		img[1] = rotate(img[1], p);
		
		if (locked)
		{
			doorImg = img[1];
		}	else	{
			doorImg = img[0];
		}
		

		if (p == 1)//TOP
		{
			pos = p1;
			xSize = largerWidth; ySize = largerHeight;
		}	else if (p == 3)	
		{ //Bottom
			pos = p3;
			xSize = largerWidth; ySize = largerHeight;
		}	else if (p == 2)	
		{ //LEFT
			pos = p2;
			xSize = largerHeight; ySize = largerWidth;
		}	else 
		{	//RIGHT
			pos = p4;
			xSize = largerHeight; ySize = largerWidth;
		}
	}
*/
	
	public int getWidth() { return xSize; }
	public int getHeight() { return ySize; }
	
	public BufferedImage rotate( BufferedImage inputImage, int p) {
		//We use BufferedImage because it’s provide methods for pixel manipulation
			int width = inputImage.getWidth(); //the Width of the original image
			int height = inputImage.getHeight();//the Height of the original image

			if (p == 2)
			{
			//180 Degrees
			
			BufferedImage returnImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB  );
		//we created new BufferedImage, which we will return in the end of the program
		//it set up it to the same width and height as in original image
		// inputImage.getType() return the type of image ( if it is in RBG, ARGB, etc. )

			
			//180 Degrees
			for( int x = 0; x < width; x++ ) {
				for( int y = 0; y < height; y++ ) {
					returnImage.setRGB( width-x-1, height-y-1, inputImage.getRGB( x, y  )  );
				}
			}
		//so we used two loops for getting information from the whole inputImage
		//then we use method setRGB by whitch we sort the pixel of the return image
		//the first two parametres is the X and Y location of the pixel in returnImage and the last one is the //source pixel on the inputImage
		//why we put width – x – 1 and height –y – 1 is hard to explain for me, but when you rotate image by //180degree the pixel with location [0, 0] will be in [ width, height ]. The -1 is for not to go out of
		//Array size ( remember you always start from 0 so the last index is lower by 1 in the width or height
		//I enclose Picture for better imagination  ... hope it help you
			
			
			return returnImage;
		//and the last return the rotated image
			} 	else if (p == 1)
			{
			
			
			//90 Degrees Left
			BufferedImage returnImage = new BufferedImage( height, width , BufferedImage.TYPE_INT_ARGB  );
			//We have to change the width and height because when you rotate the image by 90 degree, the
			//width is height and height is width <img src='http://forum.codecall.net/public/style_emoticons/<#EMO_DIR#>/smile.png' class='bbc_emoticon' alt=':)' />

				for( int x = 0; x < width; x++ ) {
					for( int y = 0; y < height; y++ ) {
						returnImage.setRGB(y, width-x-1, inputImage.getRGB( x, y  )  );
			//Again check the Picture for better understanding
						}
					}
				return returnImage;
				
			}	else if (p == 3)
			{
				
				//90 Degrees Right
				BufferedImage returnImage = new BufferedImage( height, width , BufferedImage.TYPE_INT_ARGB  );

				for( int x = 0; x < width; x++ ) {
					for( int y = 0; y < height; y++ ) {
						returnImage.setRGB( height-y-1, x, inputImage.getRGB( x, y  )  );
			//Again check the Picture for better understanding
					}
				}
				return returnImage;
			}	else
			{
				return inputImage;
			}
				

		}
	
	public void draw(Graphics g)
	{
		g.drawImage(img[locked? 2: (enemyLocked? 1: 0)], pos.getCol(), pos.getRow(), xSize, ySize, null);
	}
	
	public void setImages(Image[] im) {
		BufferedImage[] nim = new BufferedImage[im.length];
		for (int i = 0; i < im.length; i++)
		{
			nim[i] = (BufferedImage)im[i];
		}
		img = nim;	
		setDoor(locked);
		}
	
	public Point getPos() { return pos; }
	public int getX() { return pos.getCol(); }
	public int getY() { return pos.getRow(); }
	
	public Image getImage() { 
		return (Image)img[locked? 2: (enemyLocked? 1: 0)]; 
		}
	
	public void setDoor(boolean lock)
	{
		locked = lock;
	}
	
	public void setEnemyLocked(boolean lock)
	{
		enemyLocked = lock;
		//System.out.println("Locking Door");
	}
	
	public boolean isLocked() { return locked; }
	public boolean isEnemyLocked() { return enemyLocked; }
	
	public void toggleDoor()
	{
		locked = false;
	}


}