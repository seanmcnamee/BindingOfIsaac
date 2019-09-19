package src;

import java.awt.Graphics;
//import java.util.ArrayList;

public class TreasureRoom extends Room{

	public TreasureRoom(boolean up, boolean down, boolean left, boolean right)
	{
		super(up, down, left, right, "TreasureRoom");
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}
	
	public void setTreasureLayout(int index)
	{
		setSelectedLayout(-1);
		
		
		final int roomwidth = 13, roomheight = 7;
		
		RoomObject[][] layout = null;
		Pickup[][] roomItems = null;
		

		
		
		if (index == 0)
		{
			//Layout #0
			layout = new RoomObject[roomheight][roomwidth];
			
			layout[0][0] = new FireObject();
			
			layout = reflectOverHorizMid(layout);
			
			layout [3][4] = new RoomObject();
			
			layout = reflectOverVertMid(layout);
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			
			//Item Layout
			roomItems = new Pickup[roomheight][roomwidth];
			
			//Randomize this
			
			roomItems[3][6] = new Special_Item();
			
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
			
			//End of Layout #0
		}	else	if (index == 1) {
			//Layout #1
			layout = new RoomObject[roomheight][roomwidth];
			
			
			layout [3][4] = new FireObject();
			
			layout = reflectOverVertMid(layout);
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			
			//Item Layout
			roomItems = new Pickup[roomheight][roomwidth];
			
			roomItems[3][6] = new Special_Item();
			
			roomItems = setItemPos(roomItems);
			
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 1		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 2		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 3		[] [] [] [] F []  * []  F [] [] [] []
			 * 4		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 5		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 6		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 
			 * 			P = Poop
			 * 			B = Block
			 * 			* = Bomb
			 * 			H = Heart
			 * 			F = Fire
			 * 			
			 */
	
			//End of Layout #1
		
		}
		
		finalizeLayout(layout, roomItems);
		
	}
	
}
