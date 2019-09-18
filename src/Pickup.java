import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Pickup {

	private int xPos, yPos;
	private double xVel, yVel;
	private Image img;
	public static final int xSize = 50, ySize = 50;
	//private final double friction = .95;
	private int cost;
	
	public Pickup()
	{
		xPos = 0;
		yPos = 0;
		xVel = yVel = 0;
		cost = 0;
		img = null;
		try {
			img = ImageIO.read(new File("penny.png"));
		} catch (IOException e) {
		}
	}
	
	public Pickup(int x, int y, Image i)
	{
		xPos = x;
		yPos = y;
		img = i;
		cost = 0;
		xVel = yVel = 0;
	}
	
	public Pickup(int x, int y, Image i, int c)
	{
		xPos = x;
		yPos = y;
		img = i;
		cost = c;
		xVel = yVel = 0;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(img, xPos, yPos, xSize, ySize, null);
		if (cost > 0)
		{
			Image i = null;
			
			try {
				
				if (cost ==3)
				{
					i = ImageIO.read(new File("3c.png"));
				}	else if (cost == 5)
				{
					i = ImageIO.read(new File("5c.png"));
				}	else
				{
					System.out.println("Pickup class cannot find defined price!");
					i = ImageIO.read(new File("3c.png"));
				}
				
			} catch (IOException e) {
			}
			g.drawImage(i, xPos + xSize/4, yPos + ySize, xSize/2, ySize/2, null);
		}
	}
	
	/*
	
	public void move(Room r) {
		// System.out.println("Move Called.");
		/*
		 * xPos += (int)xVel; yPos += (int)yVel;
		 		
		

			yVel *= friction;
			if (Math.abs(yVel) < 1) {
				yVel = 0;
			}

			xVel *= friction;
			if (Math.abs(xVel) < 1) {
				xVel = 0;
			}
			
		int minX = 30;
		int maxX = Isaac.WIDTH - img.getWidth(null);
		int minY = 30;
		int maxY = Isaac.HEIGHT - img.getHeight(null);

		boolean checkSHIT = false;//, checkX = false;
		//Rectangle isc = new Rectangle(xPos + 10, yPos + isaac[img].getHeight(null) - 30, width - 20, 20);
		Rectangle thisItem = new Rectangle(xPos, yPos, xSize, ySize);
		
		ArrayList<RoomObject> o = r.getObjects();
		Rectangle[] objs = new Rectangle[o.size()];
		
		ArrayList<Pickup> i = r.getItems();
		Rectangle[] items = new Rectangle[i.size()];
		
		
		
		// Create Room Objects
		for (int k = 0; k < o.size(); k++) {
			objs[k] = new Rectangle(o.get(k).getX(), o.get(k).getY(), o.get(k).getXSize(), o.get(k).getYSize());
		}
		
		
		if (i != null)
		{
			// Create Items
			for (int k = 0; k < i.size(); k++) {
				if (items[k] != null)
				{
					if (!items[k].equals(this))
					{
						items[k] = new Rectangle(i.get(k).getXPos(), i.get(k).getYPos(), xSize, ySize);
					}
				}
			}	
			
			for (int rec = 0; rec < i.size(); rec++)	{
				if (items[rec] != null)
				{
					if (thisItem.intersects(items[rec]))	{
						//Momentum, where collisions are NOT perfectly elastic.
						if (yVel < 0 && thisItem.getMaxY() > items[rec].getCenterY() || yVel > 0 && thisItem.getCenterY() < items[rec].getCenterY() || xVel < 0 && thisItem.getMaxX() > items[rec].getCenterX() || xVel > 0 && thisItem.getCenterX() < items[rec].getCenterX())
						{
							xPos -= xVel;
							yPos -= yVel;
							
							yVel = .7*i.get(rec).getYVel();
							xVel = .7*i.get(rec).getXVel();
							
							i.get(rec).setYVel(0);
							i.get(rec).setXVel(0);
							
						}	else
						{
							System.out.println("Problem with Item-Item collision (Pickup class)");
							yVel = 0;
							xVel = 0;
						}
					}
					checkSHIT = true;
				}	
			}
		}
		
		

		// Check thisItem hitting object
		for (Rectangle rec : objs) {
			if (thisItem.intersects(rec)) {
				//Momentum, where the blocks cannot move.
				
				if (yVel < 0 && thisItem.getCenterY() > rec.getCenterY() || yVel > 0 && thisItem.getCenterY() < rec.getCenterY())
				{
					xPos -= xVel;
					yPos -= yVel;
					
					yVel *= -.5;
				}	else if (xVel < 0 && thisItem.getCenterX() > rec.getCenterX() || xVel > 0 && thisItem.getCenterX() < rec.getCenterX())
				{
					xPos -= xVel;
					yPos -= yVel;
					
					xVel *= -.5;
				}	else
				{
					System.out.println("Problem with Item-Object collision (Pickup class)");
					yVel = 0;
					xVel = 0;
				}
			}
			checkSHIT = true;
		}
		

		/*
		 * if (!checkhit) { System.out.println("Switching!"); checkhit = true; }
		 * else { System.out.
		 * println("This should never truely happen... --------------"); }
		 
		// System.out.println("When Will I occur?");
		// Actually add to the heights
		if (checkSHIT || o.size() <= 0) {
			if ((xPos > minX && xPos < maxX || xPos <= minX && xVel > 0 || xPos >= maxX && xVel < 0) && xVel != 0) {
				// System.out.println("X Velocity: " + xVel);
				xPos += xVel;
				//System.out.println("LEFT: " + moveLeft + ", RIGHT: " + moveRight + " (3)");
				//System.out.println(checkSHIT + ", X: " + checkX);
			}

			if ((yPos > minY && yPos < maxY || yPos <= minY && yVel > 0 || yPos >= maxY && yVel < 0) && yVel != 0) {
				// System.out.println("Y Velocity: " + yVel);
				yPos += yVel;
				//System.out.println("UP: " + moveUp + ", DOWN: " + moveDown);
				//System.out.println(checkSHIT);
			}
		}

	}
*/
	
	public void setXPos(int x) { xPos = x; }
	public void setYPos(int y) { yPos = y; }
	public int getXPos() { return xPos; }
	public int getYPos() { return yPos; }
	public void setXVel(double x) { xVel = x; }
	public void setYVel(double y) { yVel = y; }
	public double getXVel() { return xVel; }
	public double getYVel() { return yVel; }
	public Image getImage() { return img; }
	public void setImage(Image i) { img = i; }
	
	public int getCost() { return cost; }
	
	public boolean pickedUp(MainChar mc)
	{
		boolean bigger = mc.getMoney() >= cost;
		if (bigger)
		{
			mc.addMoney(-cost);
		}
		return bigger;
	}
	
}
