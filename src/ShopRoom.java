package src;

import java.awt.Graphics;

public class ShopRoom extends Room{

	public ShopRoom(boolean up, boolean down, boolean left, boolean right)
	{
		super(up, down, left, right, "ShopRoom");
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
	
	public void setShopLayout()
	{
		setSelectedLayout(-1);
		
		
		final int roomwidth = 13, roomheight = 7;
		
		RoomObject[][] layout = null;
		Pickup[][] roomItems = null;
		
		layout = new RoomObject[roomheight][roomwidth];
		layout[1][1] = new RoomObject();
		layout[1][5] = new RoomObject();
		
		layout = reflectOverVertMid(layout);
		
		layout[1][6] = new RoomObject();
		
		//Finalize the Layout
		layout = setObjectPos(layout);
		
		layout[1][6] = new RoomObject(layout[1][6].getX(), layout[1][6].getY(), 2);
		
		
		//Item Layout
		roomItems = new Pickup[roomheight][roomwidth];
		
		//Randomize this
		//There will be two items in the shop.
		for (int itemnum = -1; itemnum <= 1; itemnum+=2)
		{
			if (Math.random() >= .66)
			{
				//Heart: 3 cents
				roomItems[4][6 + (2*itemnum)] = new Heart();
			}	else if (Math.random() >= .5)
			{
				//Key: 5 cents
				roomItems[4][6 + (2*itemnum)] = new Key();
			}	else
			{
				//Bomb: 5 cents
				roomItems[4][6 + (2*itemnum)] = new Bomb();
			}
		}
		
		roomItems = setItemPos(roomItems);
		
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
			
		finalizeLayout(layout, roomItems);
		
	}
	public Pickup[][] setItemPos(Pickup[][] p)
	{
		Pickup[][] result = new Pickup[p.length][p[0].length];
		Heart hrt = new Heart();
		//Coin cn = new Coin();
		Bomb bm = new Bomb();
		
		
		for (int r = 0; r < p.length; r++)
		{
			for (int c = 0; c < p[r].length; c++)
			{
				if (p[r][c] != null)
				{
					//System.out.println(layout[r][c].getClass() + " and " + block.getClass() + " then " + poop.getClass());
					if (p[r][c].getClass().equals(hrt.getClass()))
					{
						result[r][c] = new Heart(50 + 25 + (c*100), 50 + 25 + (r*100), 1, 3);
					}	else if (p[r][c].getClass().equals(bm.getClass()))
					{
						result[r][c] = new Bomb(50 + 25 + (c*100), 50 + 25 + (r*100), 1, 5);
					}	else
					{
						result[r][c] = new Key(50 + 25 + (c*100), 50 + 25 + (r*100), 5);
					}
						//result[r][c] = new PoopObject(50 + (c*layout[r][c].getXSize()), 50 + (r*layout[r][c].getYSize()));
					//}
				}
			}
		}
		//System.out.println("Test");
		return result;
	}
	
}
