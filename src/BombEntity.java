import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BombEntity {

	private int xPos, yPos;
	private Image img;
	private long startTime;
	public static final int xSize = 50, ySize = 50;
	
	public BombEntity(int x, int y)
	{
		xPos = x;
		yPos = y;
		img = null;
		try {
			img = ImageIO.read(new File("Bomb.png"));
		} catch (IOException e) {
		}
		startTime = System.currentTimeMillis();
	}
	
	public void draw(Graphics g)
	{
		img = null;
		try {
			if ((System.currentTimeMillis() - startTime)%4 == 0)
			{
				img = ImageIO.read(new File("ActiveBomb.png"));
			}	else	{
				
				img = ImageIO.read(new File("Bomb.png"));
			}
			
		} catch (IOException e) {
		}
		
		g.drawImage(img, xPos, yPos, xSize, ySize, null);
	}
	
	
	public void setXPos(int x) { xPos = x; }
	public void setYPos(int y) { yPos = y; }
	public int getXPos() { return xPos; }
	public int getYPos() { return yPos; }
	public Image getImage() { return img; }
	public void setImage(Image i) { img = i; }
	public long getTime() { return startTime; }
	
	public void explode(Room r)
	{
		double bombRange = 1.5; //In blocks
		//Destroy shit
		
		ArrayList<RoomObject> newObjs = r.getObjects();
		ArrayList<Enemy> newEnemies = r.getEnemies();
		Door[] newDoors = r.getDoors();
		
		for (int index = newObjs.size()-1; index >= 0; index--)
		{
			int objX = (newObjs.get(index).getX() + newObjs.get(index).getXSize()/2);
			int objY = (newObjs.get(index).getY() + newObjs.get(index).getYSize()/2);
			
			//Check bombing blocks
			double distance = Math.sqrt(Math.pow(objY - yPos, 2) + Math.pow(objX - xPos, 2));
			if (distance <= bombRange*100)
			{
				if (Math.random() >= .85)//15% chance to drop something
				{
					if (!newObjs.get(index).getClass().toString().equals("class FireObject"))
					{
						r.rndItemDrop(newObjs.get(index));
					}
					
				}
				newObjs.remove(index);
			}
		}
		
		r.setObjects(newObjs);
		
		//Check bombing enemies
		for (int index = newEnemies.size()-1; index >= 0; index--)
		{
			int enmyX = ((int)newEnemies.get(index).getX() + newEnemies.get(index).getXSize()/2);
			int enmyY = ((int)newEnemies.get(index).getY() + newEnemies.get(index).getYSize()/2);
			
			
			double distance = Math.sqrt(Math.pow(enmyY - yPos, 2) + Math.pow(enmyX - xPos, 2));
			if (distance <= bombRange*100)
			{
				if (!newEnemies.get(index).getClass().toString().equals("class MoterFly_Enemy"))
				{
					if (Math.random() >= .85)//15% chance to drop something
					{
						r.rndItemDrop(newEnemies.get(index));
					}
				}	else
				{
					double sinangle = (Math.random()*2*Math.PI);
					//System.out.println(sinangle);
					double distance2 = Math.random()*30;
					
					int newX1 = ((int)(newEnemies.get(index).getX() + Math.cos(sinangle)*distance2));
					int newY1 = ((int)(newEnemies.get(index).getY() + Math.sin(sinangle)*distance2));
					
					int newX2 = ((int)(newEnemies.get(index).getX() - Math.cos(sinangle)*distance2));
					int newY2 = ((int)(newEnemies.get(index).getY() - Math.sin(sinangle)*distance2));
					
					newEnemies.add(new AttackFly_Enemy(newX1, newY1));
					newEnemies.add(new AttackFly_Enemy(newX2, newY2));
				}
				newEnemies.remove(index);
				
			}
		}
		
		r.setEnemies(newEnemies);
		
		//Check bombing doors
		for (int index = newDoors.length-1; index >= 0; index--)
		{
			if (newDoors[index] != null)
			{
				int doorX = (newDoors[index].getX() + newDoors[index].getWidth()/2);
				int doorY = (newDoors[index].getY() + newDoors[index].getHeight()/2);
				
				
				double distance = Math.sqrt(Math.pow(doorY - yPos, 2) + Math.pow(doorX - xPos, 2));
				if (distance <= bombRange*100)
				{
					//Change the locked status of that door
					newDoors[index].setEnemyLocked(false);
				}
			}
			
		}
		
		r.setDoors(newDoors);
	}
}
