package src;

import java.awt.Graphics;
import java.util.ArrayList;

public class BossRoom extends Room{

	public BossRoom(boolean up, boolean down, boolean left, boolean right)
	{
		super(up, down, left, right, "BossRoom");
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
	
	public void setBossLayout()
	{
		setSelectedLayout(999);
		
		
		//final int roomwidth = 13, roomheight = 7;
		
		ArrayList<Enemy> enemy = getEnemies();
		
		enemy.add(new Boss_Enemy(300, 200));
		
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		F  [] [] [] [] [] [] [] [] [] [] [] F
			 * 1		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 2		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 3		[] [] [] [] B []  * [] B [] [] [] []
			 * 4		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 5		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 6		F  [] [] [] [] [] [] [] [] [] [] [] F
			 * 
			 * 			P = Poop
			 * 			B = Block
			 * 			* = Bomb
			 * 			H = Heart
			 * 			F = Fire
			 * 			
			 */
		
	}
}
