package src;

//import java.awt.Graphics;
import java.awt.Image;
//import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Horf_Enemy extends Enemy {

	private double targetX, targetY;
	private double distance;
	
	public Horf_Enemy(int x, int y)
	{

		super(x, y, selectImg(), 100, 100, 3, 2);
		targetX = 0;
		targetY = 0;
		distance = 0;//Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
	}
	
	public static Image selectImg()
	{
		Image i = null;
		try {
			i = ImageIO.read(new File("Horf.png"));
			//System.out.println("Made a Greed!");
		} catch (IOException e) {
		}
		return i;
		
	}
	
	/*
	public void move(Room rm, MainChar mc) {
		if (bullets != null)
		{
			for (Bullet b: bullets)
			{
				b.move();
			}
		}
		
		/*
		 * 
		 * ArrayList<Bullet> b = bullets;
		Rectangle[] bull = new Rectangle[b.size()];
		
		ArrayList<RoomObject> o = rm.getObjects();
		Rectangle[] obj = new Rectangle[o.size()];
		
		//Create the bullets
		for (int i = 0; i < bull.length; i++)
		{
			bull[i] = new Rectangle(b.get(i).getX(), b.get(i).getY(), b.get(i).getImage().getWidth(null), b.get(i).getImage().getHeight(null));
		}
		
		//Create the objects
		for (int i = 0; i < obj.length; i++)
		{
			obj[i] = new Rectangle(o.get(i).getX(), o.get(i).getY(), RoomObject.xSize, RoomObject.ySize);
		}
		
		
		//Check bullets hitting the outside wall/room objects
				for (int i = bull.length-1; i >= 0; i--)
				{
					
					//If the bullet collides with something, destroy the bullet (and possibly call a method for that obect)
					
					//If the bullet hits a wall

					//Bullet hitting the outside wall
					if (  (bull[i].getCenterX() > Isaac.WIDTH-70) && (b.get(i).getXVel() > 0) || (bull[i].getCenterX() < 70) && (b.get(i).getXVel() < 0) || (bull[i].getCenterY() > Isaac.HEIGHT-70) && (b.get(i).getYVel() > 0) || (bull[i].getCenterY() < 70) &&  ! (b.get(i).getYVel() > 0))
					{
						//System.out.println("Removing a Bullet!");
						b.remove(i);
					}
					
					//Bullet hitting an object
					if (o != null)
					{
						for (int k = 0; k < obj.length; k++)
						{
							//System.out.println("Block at: " + ob.getCenterX() + ", " + ob.getCenterY());
							if (obj[k].intersects(bull[i]))
							{
								
								
								//System.out.println("Removing a Bullet!");
								b.remove(i);
								bullets = b;
								break;
								//System.out.println(o.get(k).getClass());
								
							}
						}
					}	
				}

				bullets = b;
			
	}
		 */
	
	
	public void att(MainChar mc)
	{
		
		targetX = mc.getX() + mc.getImg().getWidth(null)/2;
		targetY = mc.getY() + (mc.getImg().getHeight(null)/2);
		distance = Math.sqrt(Math.pow(targetX - getX()+getXSize()/4, 2) + Math.pow(targetY - getY()+getYSize()/2, 2));
		
		
		if (distance < 550 && (System.currentTimeMillis() - getTime() > 2000))
		{
			setTime(System.currentTimeMillis());
			
			//Send a bullet at Isaac
			double xV = (getVector() / distance) * (targetX - getX()+getXSize()/2);
			double yV = (getVector() / distance) * (targetY - getY()+getYSize()/2);
			
			bullets.add(new Bullet((int)(getX() + getXSize()/2 - 25), (int)(getY() + getYSize()/2 - 25), xV, yV, true));
		
		}
	}

	
	
}
