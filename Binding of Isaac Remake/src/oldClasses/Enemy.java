package src;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Enemy{

	
	private double xPos, yPos;
	private int xSize, ySize;
	private double xVel, yVel, vectorV;
	private long enemyTime, animateTime, touchTime;
	private Image img;
	ArrayList<Bullet> bullets;
	
	private int health;
	
	
	public Enemy()
	{
		xPos = 0;
		yPos = 0;
		xVel = 0;
		yVel = 0;
		vectorV = 0;
		health = 1;
		xSize = ySize = 50;
		bullets = new ArrayList<> ();
		enemyTime = animateTime = touchTime = System.currentTimeMillis();
		img = null;
	}
	
	public Enemy(int x, int y, Image i, int xS, int yS, double v, int h)
	{
		xPos = x;
		yPos = y;
		xSize = xS;
		ySize = yS;
		vectorV = v;
		health = h;
		xVel = 0;
		yVel = 0;
		bullets = new ArrayList<> ();
		enemyTime = animateTime = touchTime = System.currentTimeMillis();
		img = i;
	}
	
	
	public void draw(Graphics g) {
		g.drawImage(img, (int)xPos, (int)yPos, xSize, ySize, null);
		if (bullets.size() > 0)
		{
			for (Bullet b : bullets)
			{
				if (b != null)
				{
					b.draw(g);
				}
			}
		}
	}

	
	public void move(Room r, MainChar mc) {
		// TODO Auto-generated method stub
		xPos += xVel;
		yPos += yVel;
		if (bullets.size() > 0)
		{
			for (Bullet b : bullets)
			{
				if (b != null)
				{
					b.move();
				}
			}
		}
	}
	
	public void att(MainChar mc)
	{
		
	}

	
	public double getX() { return xPos;	 }
	public double getY() { return yPos; }

	public void setX(int x) { xPos = x; }
	public void setY(int y) { yPos = y; }
	
	public int getXSize() { return xSize; }
	public int getYSize() { return ySize; }
	
	
	public double getXVel() { return xVel; }
	public double getYVel() { return yVel; }
	
	public void setXVel(double x) { xVel = x;}
	public void setYVel(double y) { yVel = y; }

	public Image getImg() { return img; }
	public void setImg(Image i) { img = i; }
	
	//Get the Enemy Time
	public long getTime() { return enemyTime; }
	public void setTime(long t) { enemyTime = t; }
	
	public long getAnimateT() { return animateTime; }
	public void setAnimateT(long t) { animateTime = t; }	
	
	public long getlastTouch() { return touchTime; }
	public void setlastTouch(long t) { touchTime = t; }	
	
	public double getVector() { return vectorV; }
	
	public int getHealth() { return health; }
	public void addHealth(int h) { health += h; }
	
	public ArrayList<Bullet> getBullets() { return bullets; }
	public void setBullets(ArrayList<Bullet> b) { bullets = b; }


}
