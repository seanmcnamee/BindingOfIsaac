//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AttackFly_Enemy extends Enemy{
	
	
	private double friction;
	private double targetX, targetY;
	private double angle;
	
	private Image img1, img2;
	
	public AttackFly_Enemy(int x, int y)
	{
		super(x, y, selectImg(), 50, 50, 1.3, 2);
		friction = .95;
		targetX = targetY = angle = 0;
		img1 = null;
		img2 = null;
		
		try {
			img1 = ImageIO.read(new File("Attack_Fly.png"));
			img2 = ImageIO.read(new File("Attack_Fly2.png"));
	} catch (IOException e) {
	}
	

	}
	
	public static Image selectImg()
	{
		Image i = null;
		try {
			i = ImageIO.read(new File("Attack_Fly.png"));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void setImages(Image i1, Image i2)
	{
		img1 = i1;
		img2 = i2;
	}
	
	public void draw(Graphics g) {
		Image i = null;
		
		if ((System.currentTimeMillis() - getAnimateT()) % 3 == 0)
		{
			i = img1;
		}	else
		{
			i = img2;
		}
		
		setImg(i);
		
		super.draw(g);
	}
	
	public void move(Room r, MainChar mc)
	{
		
		//Every 2 seconds, reset the position
		if (System.currentTimeMillis() > getTime())
		{
			targetX = (mc.getX() + mc.getImg().getWidth(null)/2) + (int)((Math.random()*100)-50);
			targetY = mc.getY() + mc.getImg().getHeight(null)/2 + (int)((Math.random()*100)-50);
			setTime(System.currentTimeMillis() + 1500 + ((int)(Math.random()*800)) );
			//System.out.println("Setting Location");
			
			angle = (Math.atan2((getY() + getYSize()/2)-targetY, (getX() + getXSize()/2)-targetX));
			angle -= Math.PI;
			angle *= -1;
			//System.out.println(angle*180/Math.PI);
			//System.out.println("X: " + getY() + getYSize()/2 + ", Y: " + getX() + getXSize()/2);
			
		}	else if (System.currentTimeMillis() + 1000  > getTime())
		{
			//System.out.println(getXVel() + ", " + getYVel());
			double xV = getXVel()*friction;
			double yV = getYVel()*friction;
			if (xV == 0 || yV == 0)
			{
				xV = yV = 0;
			}
			//System.out.println("Sliding.");
			setXVel(xV);
			setYVel(yV);
		}	else	{
			
			
			
			//This is where the fun begins
			double x = getX() + getXSize()/2;
			double y = getY() + getYSize()/2;
			
			
			
			double xC = (targetX - x);
			double yC = (targetY - y);
			//System.out.println(xC + ", " + yC);
			
			if (xC != 0 && yC != 0)
			{
				double c = Math.sqrt(Math.pow(xC, 2) + Math.pow(yC, 2));
				
				if (c < 50)
				{
					setTime(System.currentTimeMillis()+1000);
				}	else
				{
					//System.out.print("Distance is: " + c);
					
					double angle = Math.atan(yC/xC);
					
					double ratio = (getVector()) / (c);
					double xV = xC*ratio;
					double yV = yC*ratio;
					
					double addAngle = angle+ (Math.PI/2);
					
					double sinangle = (((Math.abs(System.currentTimeMillis()-getTime()))%(1000.0))/(1000))*2*Math.PI;

					//System.out.println("Out of " + 2*Math.PI + ": " + sinangle);
							//System.out.println(xC + " : " + angle);
					
					double distance = 5*Math.sin(sinangle);
					
					//System.out.println(distance);
					//System.out.println("Out of 10: " + distance);
					//System.out.println((angle*180)/(2*Math.PI));
					
					if ((xV / yV) < 0)  //If its +- or -+
					{
						//Negate the y
						double newX = xV + Long.signum((long)(xV))*distance*Math.abs(Math.cos(addAngle));
						double newY = yV + -1*Long.signum((long)(yV))*distance*Math.abs(Math.sin(addAngle));
						
						xV += newX;
						yV += newY;
					}
					if ((xV / yV) > 0) //If its ++ or --
					{
						//Negate x
						double newX = xV + -1*Long.signum((long)(xV))*distance*Math.abs(Math.cos(addAngle));
						double newY = yV + Long.signum((long)(yV))*distance*Math.abs(Math.sin(addAngle));

						xV += newX;
						yV += newY;
					}
					

					//System.out.println("Movement Distance: " + xV + ", " + yV);
					setXVel(xV);
					setYVel(yV);
				}
				
				
			}
			
		}//Movement If
		
		
		super.move(r,  mc);
		
		
	}//End of Method
	
	
	
}//End of Class
