import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Boss_Enemy extends Enemy {

	private int attackStage;
	private int part;
	private final double friction = .98;
	private double xTarget, yTarget; 
	private boolean targetSet;
	private boolean attacked;
	
	public Boss_Enemy(int x, int y)
	{
		super(x, y, selectImg(), 150, 150, 3, 20);
		attackStage = 4;
		part = 0;
		xTarget = yTarget = 0; targetSet = false;
		setTime(System.currentTimeMillis()+2000);
	}

	
	public static Image selectImg()
	{
		Image i = null;
		try {
			i = ImageIO.read(new File("MonstroDefault.png"));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void draw(Graphics g)
	{
		Image i = getImg();
		try {
			i = ImageIO.read(new File("MonstroDefault.png"));
		} catch (IOException e) {
		}
		//Set Attack Stage
		if (System.currentTimeMillis() > getTime())
		{
			attackStage = (int)(Math.random()*3 + 1);
			//Switch the attack type every three seconds
			setTime(System.currentTimeMillis()+3000);
		}
		
		
		
		if (attackStage == 1)	//Fire at isaac
		{
			//System.out.println("Fireing!");
			if (getTime() - System.currentTimeMillis() > (3000 - 1000*(.5)))	//Half second
			{
				part = 1;
				try {
					i = ImageIO.read(new File("MonstroAboutToAttack.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(.75)))
			{
				part = 2;
				try {
					i = ImageIO.read(new File("MonstroDefault.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(1)))
			{
				part = 3;
				try {
					i = ImageIO.read(new File("MonstroAboutToAttack.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(2.5)))
			{
				part = 4;
				try {
					i = ImageIO.read(new File("MonstroAttacking.png"));
				} catch (IOException e) {
				}
			}	else
			{
				part = 5;
				try {
					i = ImageIO.read(new File("MonstroDefault.png"));
				} catch (IOException e) {
				}
				setTime(System.currentTimeMillis()+2000);
				attackStage = 4;
			}
		}	else if (attackStage == 2)	//Quick jump at isaac
		{
			if (getTime() - System.currentTimeMillis() > (3000 - 1000*(1)))
			{
				part = 1;
				try {
					i = ImageIO.read(new File("MonstroHopJump.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(1.3)))
			{
				part = 2;
				try {
					i = ImageIO.read(new File("MonstroHopFall.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(2.3)))
			{
				part = 3;
				try {
					i = ImageIO.read(new File("MonstroHopJump.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(2.6)))
			{
				part = 4;
				try {
					i = ImageIO.read(new File("MonstroHopFall.png"));
				} catch (IOException e) {
				}
			}	else
			{
				part = 5;
				try {
					i = ImageIO.read(new File("MonstroDefault.png"));
				} catch (IOException e) {
				}
				setTime(System.currentTimeMillis()+2000);
				attackStage = 4;
			}
		}	else if (attackStage == 3){	//Mega Jump at isaac
			//System.out.println("Mega Jump");
			if (getTime() - System.currentTimeMillis() > (3000 - 1000*(.2)))
			{
				part = 1;
				try {
					i = ImageIO.read(new File("MonstroMegaJumping.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(2)))
			{
				part = 2;
				i = null;
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(2.2)))
			{
				part = 3;
				try {
					i = ImageIO.read(new File("MonstroMegaLanding.png"));
				} catch (IOException e) {
				}
			}	else if (getTime() - System.currentTimeMillis() > (3000 - 1000*(2.4)))
			{
				part = 4;
				try {
					i = ImageIO.read(new File("MonstroMegaSlam.png"));
				} catch (IOException e) {
				}
			}	else
			{
				part = 5;
				try {
					i = ImageIO.read(new File("MonstroDefault.png"));
				} catch (IOException e) {
				}
				setTime(System.currentTimeMillis()+2000);
				attackStage = 4;
			}
		}	else { //Waiting period for player to attack
			part = 1;
			try {
				i = ImageIO.read(new File("MonstroDefault.png"));
			} catch (IOException e) {
			}
		}
		
		setImg(i);
		
		super.draw(g);
	}
	
	public void move(Room r, MainChar mc) {
		
		//System.out.println("M);
		if (attackStage == 2)
		{
			if (part == 1 || part == 3)
			{
				if (!targetSet)
				{
					double xV = ((mc.getX() + mc.getImg().getWidth(null)/2) - (getX()+getXSize()/2));
					double yV = ((mc.getY() + mc.getImg().getHeight(null)/2) - (getY()+getYSize()/2));
					double distance = (Math.sqrt(Math.pow(xV, 2) + Math.pow(yV, 2)));
					double ratio = getVector()/distance;
					
					xV *= ratio;
					yV *= ratio;
					setXVel(xV);
					setYVel(yV);
					targetSet = true;
				}
			}	else if (part == 2 || part == 4)
			{
				targetSet = false;
				double xV = getXVel();
				double yV = getYVel();
				xV *= friction;
				yV *= friction;
				if (xV == 0 || yV == 0)
				{
					xV = yV = 0;
				}
				setXVel(xV);
				setYVel(yV);
			}	else
			{
				setXVel(0);
				setYVel(0);
			}
		} 	else if (attackStage == 3)
		{
			if (part == 1)
			{
				if (!targetSet)
				{
					xTarget = (mc.getX() -25 );
					yTarget = (mc.getY() -25);
					targetSet = true;
				}
			}	else if (part == 2)
			{
				setX((int)xTarget);
				setY((int)yTarget);
				targetSet = false;
			}
		}	else //STAGE 1 or 4 (NO MOVEMENT)
		{
			setXVel(0);
			setYVel(0);
		}
		
		super.move(r, mc);
	}
	
	public void att(MainChar mc)
	{
		
		//Only attack during Stage 1
		if (attackStage == 1)
		{
			if (part == 4)
			{
				if (!attacked)
				{
					//System.out.println("Attacking?");
					double xPos = (getX()+getXSize()/2);
					double yPos = (getY()+getYSize()/2);
					double xV = ((mc.getX() + mc.getImg().getWidth(null)/2) - xPos);
					double yV = ((mc.getY() + mc.getImg().getHeight(null)/2) - yPos);
					double distance = (Math.sqrt(Math.pow(xV, 2) + Math.pow(yV, 2)));
					double ratio = getVector()/distance;
					
					//double angle = Math.atan2(yV, xV);
					xV *= ratio;
					yV *= ratio;
					
					for (int u = 0; u < 15; u++)
					{
						
						int newX = (int)((Math.random()*4)-2);
						int newY = (int)((Math.random()*4)-2);
						bullets.add(new Bullet((int)xPos, (int)yPos, newX + xV, newY + yV, true));
						//System.out.println("Attacking? 2.0");
					}
					attacked = true;
				}
			}	else
			{
				attacked = false;
			}
			
			
			
		}
	}
	
	
	public double getStage() { return attackStage; }
	public double getPart() { return part; }
}
