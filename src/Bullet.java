import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {

	private int xPos, yPos; 
	private double xVel, yVel;
	private int distance;
	private Image img;
	boolean enemy;
	
	public Bullet(int x, int y, double xV, double yV)
	{
		xPos = x;
		yPos = y;
		xVel = xV;
		yVel = yV;
		img = null;
		enemy = false;
		try {
		    img = ImageIO.read(new File("tear.png"));
		} catch (IOException e) {
		}
		
	}
	
	public Bullet(int x, int y, double xV, double yV, boolean en)
	{
		xPos = x;
		yPos = y;
		xVel = xV;
		yVel = yV;
		img = null;
		enemy = en;
		try {
			if (enemy)
			{
				img = ImageIO.read(new File("EnemyTear.png"));
			}	else
			{
				img = ImageIO.read(new File("tear.png"));
			}
		} catch (IOException e) {
		}
		
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(img, xPos, yPos, null);
	}
	
	public void move()
	{
		xPos += xVel;
		yPos += yVel;
		distance += Math.abs(xVel);
		distance += Math.abs(yVel);
	}
	
	public int getX() { return xPos; }
	public int getY() { return yPos; }
	public double getXVel() { return xVel; }
	public double getYVel() { return yVel; }
	
	public int getDistance() { return distance; }
	
	public Image getImage() { return img; }
	public void setImage(Image i) { img = i; }
	
}