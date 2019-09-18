package src;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RegularFly_Enemy extends Enemy{
	private double targetX, targetY;
	private double angle;
	
	public RegularFly_Enemy(int x, int y)
	{
		super(x, y, selectImg(), 50, 50, 20, 1);
		targetX = x;
		targetY = y;
		angle = Math.random()*2*Math.PI;
	}
	
	public static Image selectImg()
	{
		Image i = null;
		try {
			i = ImageIO.read(new File("Regular_Fly.png"));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void draw(Graphics g) {
		Image i = null;
		try {
			if ((System.currentTimeMillis() - getAnimateT()) % 3 == 0)
			{
				//System.out.println("Animate");
				i = ImageIO.read(new File("Regular_Fly.png"));
			}	else
			{
				i = ImageIO.read(new File("Regular_Fly2.png"));
			}
		} catch (IOException e) {
		}
		setImg(i);
		
		super.draw(g);
	}
	
	public void move(Room r, MainChar mc)
	{
		
		angle += Math.random()*.05;
		double sinangle = (((Math.abs(System.currentTimeMillis()-getTime()))%(10000.0))/(10000))*2*Math.PI;
		//System.out.println(sinangle);
		double distance = Math.sin(sinangle)*getVector();
		
		setX((int)(targetX + Math.cos(angle)*distance));
		setY((int)(targetY + Math.sin(angle)*distance));
		
		//super.move(r,  mc);
		
		
	}//End of Method
	
	
}
