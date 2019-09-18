import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Room {

	//private int size; //There are 2 different sizes. 1 = normal, 2 = large
	private Door[] doors;
	private ArrayList<Enemy> enemies;
	
	private int selectedLayout; //Used after all enemies are killed
	private boolean killed, beenLocked;
	
	private ArrayList<RoomObject> roomLayout;
	private ArrayList<Pickup> items;
	ArrayList<BombEntity> activeBombs;
	
	private boolean isAbove, isBelow, isLeft, isRight;
	private boolean delete;
	private String type;
	private Image background;
	
	private long enterTime;
	
	
	
	public Room()
	{
		items = new ArrayList<> ();
		enemies = new ArrayList<> ();
		roomLayout = new ArrayList<> ();
		activeBombs = new ArrayList<> ();
		
		isAbove = isBelow = isLeft = isRight = false;
		doors = new Door[0];
		type = "Undefined";
		selectedLayout = -1;
		killed = beenLocked = false;
		setEnterTime(System.currentTimeMillis());
		
		delete = false;
		
		background = null;
		try {
		    background = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
		}
		
	}
	
	public Room(boolean del)
	{
		items = new ArrayList<> ();
		enemies = new ArrayList<> ();
		roomLayout = new ArrayList<> ();
		activeBombs = new ArrayList<> ();
		
		isAbove = isBelow = isLeft = isRight = false;
		doors = new Door[0];
		type = "Undefined";
		selectedLayout = -1;
		killed = beenLocked = false;
		setEnterTime(System.currentTimeMillis());
		
		delete = del;
		
		background = null;
		try {
		    background = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
		}
	}
	
	public Room(boolean up, boolean down, boolean left, boolean right, String t)
	{
		items = new ArrayList<> ();
		enemies = new ArrayList<> ();
		roomLayout = new ArrayList<> ();
		activeBombs = new ArrayList<> ();
		
		isAbove = up;
		isBelow = down;
		isLeft = left;
		isRight = right;
		doors = new Door[4];
		
		type = t;
		selectedLayout = -1;
		killed = beenLocked = false;
		setEnterTime(System.currentTimeMillis());
		
		delete = false;
		
		background = null;
		try {
		    background = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
		}
		

		
	}
	
	public void att(MainChar mc)
	{
			if (enemies.size() > 0)
			{
				for (Enemy e : enemies)
				{
					e.att(mc);
				}
			}
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(background, 0, 0, Isaac.WIDTH, Isaac.HEIGHT, null);
		for (Door d : getDoors())
		{
			if (d != null)
			{
				d.draw(g);
			}
		}
		if (roomLayout != null)
		{
			for (RoomObject r : roomLayout)
			{
				if (r != null)
				{
					//System.out.println("Drawing a RoomObject");
					r.draw(g);
				}
				
			}
		}
		
		if (items != null)
		{
			for (Pickup p : items)
			{
				if (p != null)
				{
					//System.out.println("Drawing a RoomObject");
					p.draw(g);
				}
				
			}
			
			for (int b = 0; b < activeBombs.size(); b++)
			{
				activeBombs.get(b).draw(g);
				if (System.currentTimeMillis() - activeBombs.get(b).getTime() > 3000)
				{
					activeBombs.get(b).explode(this);
					activeBombs.remove(b);
					b--;
				}
			}
			
		}
		
		if (enemies != null)
		{
			for (Enemy e: enemies)
			{
				if (e != null)
				{
					//System.out.println("Drawing Enemy");
					e.draw(g);
					if (e.getBullets().size() > 0)
					{
						for (int b = 0; b < e.getBullets().size(); b++)
						{
							if (e.getBullets().get(b) != null)
							{
								e.getBullets().get(b).draw(g);
							}
						}
					}
				}
			}
		}
		
		if (enemies.size() <= 0)
		{
			 if (!killed)
				{
						System.out.println("Layout: " + selectedLayout + " all Enemies gone!");
						//ENEMIES KILLED
						int xCenter = 50 + 25 + ((6)*100);
						int yCenter = 50 + 25 + ((3)*100);
						
						if (selectedLayout == 0)
						{
							items.add(new Bomb(xCenter, yCenter, (int)((Math.random()*2)+1)));
						}	else if (selectedLayout == 1)
						{
							
						}	else if (selectedLayout == 2)
						{
							
						}	else if (selectedLayout == 3)
						{
							items.add(new Heart(xCenter, yCenter, 1));
						}	else if (selectedLayout == 4)
						{
							
						}	else if (selectedLayout == 5)
						{
							items.add(new Key(xCenter, yCenter));
						}	else if (selectedLayout == 6)
						{
							items.add(new Bomb(xCenter, yCenter, (int)((Math.random()*2)+1)));
						}	else if (selectedLayout == 999)
						{
							
						}
						
						killed = true;
						for (Door d: doors)
						{
							if (d != null)
							{
								d.setEnemyLocked(false);
							}
						}
						System.out.println("Unlocking All Doors");
				}
		}
	}
	
	
	public void move(Room r, MainChar mc)
	{
		
		if ((System.currentTimeMillis() - enterTime) > 1500)
		{
			if (enemies != null)
			{
				for (int e = 0; e < enemies.size(); e++)
				{
					if (enemies.get(e) != null)
					{
						
						//System.out.println("Moving Enemy");
						enemies.get(e).move(r, mc);
						
						if (enemies.get(e).getBullets() != null)
						{
							
							for (Bullet b : enemies.get(e).getBullets())
							{
								//Never moves any bullets
								
								b.move();
							}
						}
					}
				}
			}
		}
	}
	
	

	//Setters/Getters for BOOLEANS
	public boolean getAbove() { return isAbove;}
	public void setAbove(boolean b){ isAbove = b;}
	
	public boolean getBelow() { return isBelow;}
	public void setBelow(boolean b){ isBelow = b;}
	
	public boolean getRight() { return isRight;}
	public void setRight(boolean b){ isRight = b;}

	public boolean getLeft() { return isLeft;}
	public void setLeft(boolean b){ isLeft = b;}
	
	public boolean getDel() { return delete; }
	
	public String getType() { return type; }
	
	public Door[] getDoors() { return doors; }
	public void setDoors(Door[] d) { doors = d; }
	
	public ArrayList<RoomObject> getObjects() {return roomLayout; }
	public void setObjects(ArrayList<RoomObject> r) { roomLayout = r; }
	
	public ArrayList<Pickup> getItems() { return items; }
	public void setItems(ArrayList<Pickup> i) { items = i; }
	
	public ArrayList<Enemy> getEnemies() { return enemies; }
	public void setEnemies(ArrayList<Enemy> e) { enemies = e; }
	
	public void setBackground(Image im) {	background = im;	}
	
	public int getselectedLayout() { return selectedLayout; }
	public void setSelectedLayout(int s) { selectedLayout = s; }
	
	public boolean getKilled() { return killed; }
	public void setKilled(boolean b) { killed = b; }
	
	public boolean getBeenLocked() { return beenLocked; }
	public void setBeenLocked(boolean l) { beenLocked = l; }
	
	public int surroundingRooms()
	{
		int result = 0;
		
		//Transfers the boolean value to an integer value to add up all surrounding rooms
		//(boolean? 1: 0) == either 1 or 0. This corresponds to true = 1 and false = 0
		result += getAbove()? 1: 0;
		result += getBelow()? 1: 0;
		result += getRight()? 1: 0;
		result += getLeft()? 1: 0;
		
		//System.out.println(x);
		
		return result;
	}
	
	
	public void rndItemDrop(RoomObject ro)
	{
		//PoopObject poop = new PoopObject();
		//RoomObject robj = new RoomObject();
		
		
		
		if (Math.random() >= .85)
		{//Drop a heart (15% chance)
			items.add(new Heart(ro.getX() + 25, ro.getY() + 25, ((Math.random() >= .5)? 1: .5)));
		}	else
		{//Drop a Coin (85% chance)
			//is.add(new Coin(o.get(k).getX(), o.get(k).getY(), ((Math.random() >= .333)? ((Math.random() >= .5)? 1: 5): 10)));
			items.add(new Coin(ro.getX() + 25, ro.getY() + 25, ((Math.random() >= .3)? ((Math.random() >= .3)? 1: 2): ((Math.random() >= .2)? 5: 10))));
		}
		
	}
	
	public void rndItemDrop(Enemy e)
	{
		//PoopObject poop = new PoopObject();
		//RoomObject robj = new RoomObject();
		
			//is.add(new Coin(o.get(k).getX(), o.get(k).getY(), ((Math.random() >= .333)? ((Math.random() >= .5)? 1: 5): 10)));
			items.add(new Coin((int)e.getX() + 25, (int)e.getY() + 25, ((Math.random() >= .3)? ((Math.random() >= .3)? 1: 2): ((Math.random() >= .2)? 5: 10))));
		
	}
	
	
	//Doors
	public void setDoors(String[] around)
	{
		
		//System.out.println("In setDoors...");
		Door[] d = getDoors();
		//System.out.println(getDoors() + " doors.");
		
		if (getAbove())
		{
			//System.out.println(around[doorindex]);
			makeDoor(0, around[0], 0);
		}
		if (getBelow())
		{
			//System.out.println(around[doorindex]);
			makeDoor(2, around[2], 2);
		}
		if (getLeft())
		{
			//System.out.println(around[doorindex]);
			makeDoor(1, around[1], 1);
		}
		if (getRight())
		{
			//System.out.println(around[doorindex]);
			makeDoor(3, around[3], 3);
		}
		
		setDoors(d);

	}
	
	private void makeDoor(int index, String type, int dir)
	{
		//System.out.print(type + " " + index);
		
		if (type.equals("ShopRoom"))
		{
			//System.out.println("ShopDoor Made");
			doors[index] = new ShopDoor(true, dir);
		}	else if (type.equals("BossRoom"))
		{
			//System.out.println("BossDoor Made");
			doors[index] = new BossDoor(false, dir);
		}	else if (type.equals("TreasureRoom"))
		{
			//System.out.println("TreasureDoor Made");
			doors[index] = new TreasureDoor(false, dir);
		}	else //if (type.equals("EnemyRoom"))
		{
			//Made a Default Door
			//System.out.println("Default Door Made");
			doors[index] = new Door(false, dir);
		}	//else
		/*
		{
			System.out.print("X");
		}
		*/
	}
	
	//RoomLayout (Whats inside it?)
	public void setLayout(int index)
	{
		//Here is where we are going to create the mainframe for the construction of the Layout of a room.
		//It will include the array of RoomObjects which will each have a location of the field.
		//The rooms are 7 x 13
		//We need an array of 2D RoomLayouts
		
		
		//THIS ONLY WORKS UP TO INDEX 6!!
		
		
		/*
		//Example Layout
			layout = new RoomObject[roomheight][roomwidth];
			
			layout[0][0] = new RoomObject();
			
			layout = reflectOverHorizMid(layout);
			layout = reflectOverVertMid(layout);
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			
			//Item Layout
			roomItems = new Pickup[roomheight][roomwidth];
			
			//Could have randomization here for various items
			//Note that these items are the ones that BEGIN in the room
			roomItems[3][6] = new Heart();
			
			roomItems = setItemPos(roomItems);
			
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		B  [] [] [] [] [] [] [] [] [] [] [] B
			 * 1		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 2		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 3		[] [] [] [] [] [] H [] [] [] [] [] []
			 * 4		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 5		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 6		B  [] [] [] [] [] [] [] [] [] [] [] B
			 * 
			 * 			P = Poop
			 * 			B = Block
			 * 			H = Heart
			 * 			
			 */
		
		
		//index = 6;
		//ArrayList<RoomObject[][]> possibleLayouts = new ArrayList<> ();
		//Creating Layouts
		//index = 1;
		selectedLayout = index;
		
		
		final int roomwidth = 13, roomheight = 7;
		
		RoomObject[][] layout = null;
		Pickup[][] roomItems = null;
		
		ArrayList<Enemy> enemy = new ArrayList<> ();
		
		
		if (index == 0)
		{
			//Layout #0
			layout = new RoomObject[roomheight][roomwidth];
			
			layout[0][0] = new RoomObject();
			
			layout = reflectOverHorizMid(layout);
			layout = reflectOverVertMid(layout);
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			
			//Item Layout
			
			enemy.add(new AttackFly_Enemy(700, 300));
			enemy.add(new AttackFly_Enemy(400, 300));
			enemy.add(new AttackFly_Enemy(1000, 300));
			
			enemy.add(new RegularFly_Enemy(700, 300));
			
			setEnemies(enemy);
			
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		B  [] [] [] [] [] [] [] [] [] [] [] B
			 * 1		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 2		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 3		[] [] [] [] [] []  * [] [] [] [] [] []
			 * 4		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 5		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 6		B  [] [] [] [] [] [] [] [] [] [] [] B
			 * 
			 * 			P = Poop
			 * 			B = Block
			 * 			* = Bomb
			 * 			H = Heart
			 * 			
			 */
			
			//End of Layout #0
		}	else	if (index == 1) {
			//Layout #1
			layout = new RoomObject[roomheight][roomwidth];
			
			layout[0][0] = new PoopObject();
			layout[2][0] = new RoomObject();
			layout[3][1] = new RoomObject();
			layout[4][2] = new RoomObject();
			layout[5][3] = new RoomObject();
			layout[5][4] = new RoomObject();
			layout[3][5] = new RoomObject();
			
			layout = reflectOverNegX(layout);
			
			layout[4][6] = new RoomObject();
			layout[2][6] = new RoomObject();
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			
			//Item Layout
			roomItems = new Pickup[roomheight][roomwidth];
			
			
			roomItems[3][6] = new Heart();
			
			roomItems = setItemPos(roomItems);
			
			enemy.add(new MoterFly_Enemy(700, 300));
			enemy.add(new MoterFly_Enemy(400, 300));
			enemy.add(new MoterFly_Enemy(1000, 300));
			
			enemy.add(new RegularFly_Enemy(500, 360));
			enemy.add(new RegularFly_Enemy(850, 360));
			enemy.add(new RegularFly_Enemy(680, 200));
			enemy.add(new RegularFly_Enemy(680, 550));
			
			setEnemies(enemy);
			
			
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		P  [] [] [] [] [] [] [] [] [] [] [] []
			 * 1		[] [] [] [] [] [] [] [] B  B  [] [] []
			 * 2		B  [] [] [] [] [] B [] [] [] B [] []
			 * 3		[] B  [] [] [] B  H B [] [] [] B []
			 * 4		[] [] B  [] [] [] B [] [] [] [] [] B
			 * 5		[] [] [] B  B [] [] [] [] [] [] [] []
			 * 6		[] [] [] [] [] [] [] [] [] [] [] [] P
			 * 
			 * 	 		P = Poop
			 * 			B = Block
			 * 			H = Heart
			 * 			
			 */
			
			//End of Layout #1
		
		}	else if (index == 2) {
			//Layout #2
			layout = new RoomObject[roomheight][roomwidth];
			
			layout[0][0] = new RoomObject();
			layout[2][0] = new RoomObject();
			layout[2][1] = new RoomObject();
			layout[2][2] = new RoomObject();
			layout[2][3] = new RoomObject();
			layout[2][4] = new RoomObject();
			
			layout = reflectOverHorizMid(layout);
			
			layout = reflectOverVertMid(layout);
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			//Item Layout
			
			enemy.add(new Horf_Enemy(50, 150));
			enemy.add(new Horf_Enemy(1250, 150));
			enemy.add(new Horf_Enemy(1250, 550));
			enemy.add(new Horf_Enemy(50, 550));
			
			setEnemies(enemy);
			
			
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		B  [] [] [] [] [] [] [] [] [] [] [] B
			 * 1		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 2		B  B B  B B  [] [] [] B  B B  B B
			 * 3		[] [] [] [] [] []  K [] [] [] [] [] []
			 * 4		B  B B  B B  [] [] [] B  B B  B B
			 * 5		[] [] [] [] [] [] [] [] [] [] [] [] []
			 * 6		B  [] [] [] [] [] [] [] [] [] [] [] B
			 * 
			 * 	 		P = Poop
			 * 			B = Block
			 * 			H = Heart
			 * 			K = Key
			 * 			
			 */
			
			//End of Layout #2
		}	else if(index == 3) {
			//Layout #3
			layout = new RoomObject[roomheight][roomwidth];
			
			layout[0][1] = new RoomObject();
			layout[1][0] = new RoomObject();
			layout[2][4] = new RoomObject();
			
			layout = reflectOverHorizMid(layout);
			
			layout[5][5] = new RoomObject();
			
			layout = reflectOverNegX(layout);
			
			layout[1][6] = new RoomObject();
			layout[5][6] = new RoomObject();
			
			//Finalize the Layout
			layout = setObjectPos(layout);
			
			//Item Layout
			
			//                                                 X      Y
			enemy.add(new MoterFly_Enemy(1000, 300));
			enemy.add(new MoterFly_Enemy(400, 300));
			enemy.add(new MoterFly_Enemy(400, 700));
			enemy.add(new MoterFly_Enemy(1000, 700));
			
			setEnemies(enemy);
			
			
			/**
			 * 		//Layout of things VISUALLY
			 * 			0 1  2  3  4  5  6 7  8  9  A B C
			 * 0		[] B  [] [] [] [] [] [] [] [] [] B []
			 * 1		B  [] [] [] [] [] B  B [] [] [] [] B
			 * 2		[] [] [] []  B [] [] [] B [] [] [] []
			 * 3		[] [] [] [] [] []  H [] [] [] [] [] []
			 * 4		[] [] [] []  B [] [] [] B [] [] [] []
			 * 5		B  [] [] [] [] B B  [] [] [] [] [] B
			 * 6		[] B  [] [] [] [] [] [] [] [] [] B []
			 * 
			 * 	 		P = Poop
			 * 			B = Block
			 * 			H = Heart
			 * 			
			 */
			
			//End of Layout #3
	}	else if(index == 4)	{
		//Layout #4
		layout = new RoomObject[roomheight][roomwidth];
		
		layout[0][1] = new RoomObject();
		layout[1][0] = new RoomObject();
		
		layout = reflectOverHorizMid(layout);
		
		layout = reflectOverVertMid(layout);
		
		//Finalize the Layout
		layout = setObjectPos(layout);
		
		//Item Layout
		roomItems = new Pickup[roomheight][roomwidth];
		//roomItems[3][6] = new Heart();
		
		roomItems = setItemPos(roomItems);
		
		enemy.add(new AttackFly_Enemy(700, 300));
		enemy.add(new AttackFly_Enemy(400, 300));
		enemy.add(new AttackFly_Enemy(1000, 300));
		
		enemy.add(new RegularFly_Enemy(700, 300));
		
		setEnemies(enemy);
		
		/**
		 * 		//Layout of things VISUALLY
		 * 			0 1  2  3  4  5  6 7  8  9  A B C
		 * 0		[] B  [] [] [] [] [] [] [] [] [] B []
		 * 1		B  [] [] [] [] [] [] [] [] [] [] [] B
		 * 2		[] [] [] [] [] [] [] [] [] [] [] [] []
		 * 3		[] [] [] [] [] []  H [] [] [] [] [] []
		 * 4		[] [] [] [] [] [] [] [] [] [] [] [] []
		 * 5		B  [] [] [] [] [] [] [] [] [] [] [] B
		 * 6		[] B  [] [] [] [] [] [] [] [] [] B []
			 * 
			 * 	 		P = Poop
			 * 			B = Block
			 * 			H = Heart
			 * 			
			 */
		
		//End of Layout #4
	}else if(index == 5)	{
		//Layout #5
		layout = new RoomObject[roomheight][roomwidth];
		
		layout[4][0] = new RoomObject();
		layout[5][0] = new RoomObject();
		layout[6][0] = new RoomObject();
		layout[6][1] = new RoomObject();
		layout[6][2] = new RoomObject();
		layout[4][2] = new RoomObject();
		
		layout = reflectOverNegX(layout);
		//Finalize the Layout
		layout = setObjectPos(layout);
		
		//Item Layout
		
		enemy.add(new AttackFly_Enemy(700, 300));
		enemy.add(new AttackFly_Enemy(400, 300));
		enemy.add(new AttackFly_Enemy(1000, 300));
		
		enemy.add(new RegularFly_Enemy(700, 300));
		
		enemy.add(new Horf_Enemy(150, 550));
		enemy.add(new Horf_Enemy(1150, 150));
		
		setEnemies(enemy);
		
		/**
		 * 		//Layout of things VISUALLY
		 * 			0 1  2  3  4  5  6 7  8  9  A B C
		 * 0		[] [] [] [] [] [] [] [] [] [] B  B B
		 * 1		[] [] [] [] [] [] [] [] [] [] [] [] B
		 * 2		[] [] [] [] [] [] [] [] [] [] B  [] B
		 * 3		[] [] [] [] [] [] * [] [] [] [] [] []
		 * 4		B  [] B  [] [] [] [] [] [] [] [] [] []
		 * 5		B  [] [] [] [] [] [] [] [] [] [] [] []
		 * 6		B  B B  [] [] [] [] [] [] [] [] [] []
			 * 
			 * 	 		P = Poop
			 * 			B = Block
			 * 			* = Bomb
			 * 			H = Heart
			 * 			
			 */
		
		//End of Layout #5
	}else if(index == 6)	{
		//Layout #6
		layout = new RoomObject[roomheight][roomwidth];
		
		layout[0][0] = new PoopObject();
		layout[2][3] = new RoomObject();
		layout[3][3] = new RoomObject();
		layout[4][3] = new RoomObject();
		layout[5][3] = new RoomObject();
		layout[6][3] = new RoomObject();
		layout[6][0] = new FireObject();
		
		layout = reflectOverNegX(layout);
		
		//Finalize the Layout
		layout = setObjectPos(layout);

		//Item Layout
		roomItems = new Pickup[roomheight][roomwidth];
		//roomItems[3][6] = new Bomb();
		
		roomItems = setItemPos(roomItems);
		
		enemy.add(new Horf_Enemy(450, 350));
		enemy.add(new Horf_Enemy(850, 350));
		
		enemy.add(new AttackFly_Enemy(700, 300));
		enemy.add(new AttackFly_Enemy(100, 600));
		enemy.add(new AttackFly_Enemy(1200, 100));
		
		enemy.add(new RegularFly_Enemy(700, 300));

		setEnemies(enemy);
		
		
		/**
		 * 		//Layout of things VISUALLY
		 * 			0 1  2  3  4  5  6 7  8  9  A B C
		 * 0		P  [] [] [] [] [] [] [] [] B  [] [] []
		 * 1		[] [] [] [] [] [] [] [] [] B  [] [] []
		 * 2		[] [] [] B  [] [] [] [] [] B [] [] []
		 * 3		[] [] [] B  [] [] [] [] [] B [] [] []
		 * 4		[] [] [] B  [] [] [] [] [] B [] [] []
		 * 5		[] [] [] B  [] [] [] [] [] [] [] [] []
		 * 6		[] [] [] B  [] [] [] [] [] [] [] [] P
			 * 
			 * 	 		P = Poop
			 * 			B = Block
			 * 			H = Heart
			 * 			
			 */
		
		//End of Layout #6
	}
	/*else if(index == 7)	{
		//Layout #7
		RoomObject[][] layout7 = new RoomObject[roomheight][roomwidth];
		
		//Finalize the Layout
		layout7 = setObjectPos(layout7);
		
		selectedLayout = layout7;
	}else if(index == 8)	{
		//Layout #8
		RoomObject[][] layout8 = new RoomObject[roomheight][roomwidth];
		
		//Finalize the Layout
		layout8 = setObjectPos(layout8);
		
		selectedLayout = layout8;
	}else if(index == 9)	{
		//Layout #9
		RoomObject[][] layout9 = new RoomObject[roomheight][roomwidth];
		
		//Finalize the Layout
		layout9 = setObjectPos(layout9);
		
		selectedLayout = layout9;
	}
	*/
		
		finalizeLayout(layout, roomItems);
		
	}
	
	
	
	
	public void finalizeLayout(RoomObject[][] layout, Pickup[][] roomItems)
	{
		for (RoomObject[] r: layout)
		{
			for (RoomObject o: r)
			{
				if (o != null)
				{
					roomLayout.add(o);			
				}
			}
		}
		
		if (roomItems != null)
		{
			for (Pickup[] r : roomItems)
			{
				for (Pickup c : r)
				{
					if (c != null)
					{
						items.add(c);
					}
				}
			}
		}
	}
	
	public RoomObject[][] reflectOverNegX(RoomObject[][] layout)
	{
		/*This reflects the created part of the array over -x (starts in the top left and goes to the bottom right)
		 * It actually ADDs the reflected part to the current array, so this can be called to make more of a layout quicker.
		 * 
		 * 
		 * 
		 * 
		 */
		RoomObject[][] result = new RoomObject[layout.length][layout[0].length];
		
		for (int r = 0; r < layout.length; r++)
		{
			for (int c = 0; c < layout[r].length/2; c++)
			{
				if (layout[r][c] != null)
				{
					result[r][c] = layout[r][c];
					result[layout.length - 1 - r][layout[r].length - 1 - c] = layout[r][c];
					//System.out.println("Block at " + r + ", " + c + " corresponds to " + (layout.length-1-r) + ", " + (layout[r].length-1-c));
				}
			}
		}
		
		
		
		return result;
	}
	
	public RoomObject[][] reflectOverVertMid(RoomObject[][] layout)
	{
		/*This reflects the created part of the array over the vertical center (top middle to bottom middle)
		 * It actually ADDs the reflected part to the current array, so this can be called to make more of a layout quicker.
		 * 
		 * 
		 */
		RoomObject[][] result = new RoomObject[layout.length][layout[0].length];
		
		for (int r = 0; r < layout.length; r++)
		{
			for (int c = 0; c < layout[r].length/2; c++)
			{
				/*
				result[r][c] = layout[r][c];
				result[r][layout[r].length-1-c] = new RoomObject();
				*/
				if (layout[r][c] != null)
				{
					result[r][c] = layout[r][c];
					result[r][layout[r].length-1-c] = layout[r][c];
					//System.out.println("Block at " + r + ", " + c + " corresponds to " + r + ", " + (layout[r].length-1-c));
				}
				
			}
		}
		return result;
	}

	public RoomObject[][] reflectOverHorizMid(RoomObject[][] layout)
	{
		/*This reflects the created part of the array over the horizontal center (left middle to right middle)
		 * It actually ADDs the reflected part to the current array, so this can be called to make more of a layout quicker.
		 * 
		 * 
		 */
		RoomObject[][] result = new RoomObject[layout.length][layout[0].length];
		
		for (int r = 0; r < layout.length/2; r++)
		{
			for (int c = 0; c < layout[r].length; c++)
			{
				/*
				result[r][c] = layout[r][c];
				result[r][layout[r].length-1-c] = new RoomObject();
				*/
				if (layout[r][c] != null)
				{
					result[r][c] = layout[r][c];
					result[layout.length-1-r][c] = layout[r][c];
					//System.out.println("Block at " + r + ", " + c + " corresponds to " + r + ", " + (layout[r].length-1-c));
				}
				
			}
		}
		return result;
	}
	
	
	public RoomObject[][] setObjectPos(RoomObject[][] layout)
	{
		RoomObject[][] result = new RoomObject[layout.length][layout[0].length];
		RoomObject block = new RoomObject();
		PoopObject poop = new PoopObject();
		FireObject fire = new FireObject();
		
		
		for (int r = 0; r < layout.length; r++)
		{
			for (int c = 0; c < layout[r].length; c++)
			{
				if (layout[r][c] != null)
				{
					//System.out.println(layout[r][c].getClass() + " and " + block.getClass() + " then " + poop.getClass());
					if (layout[r][c].getClass().equals(block.getClass()))
					{
						result[r][c] = new RoomObject(50 + (c*layout[r][c].getXSize()), 50 + (r*layout[r][c].getYSize()), ((int)(Math.random()*2)));
					}	else if(layout[r][c].getClass().equals(poop.getClass()))	{
						result[r][c] = new PoopObject(50 + (c*layout[r][c].getXSize()), 50 + (r*layout[r][c].getYSize()));
					}	else if(layout[r][c].getClass().equals(fire.getClass()))	{
						result[r][c] = new FireObject(50 + (c*layout[r][c].getXSize()), 50 + (r*layout[r][c].getYSize()));
					}
				}
			}
		}
		//System.out.println("Test");
		return result;
	}
	
	public Pickup[][] setItemPos(Pickup[][] p)
	{
		Pickup[][] result = new Pickup[p.length][p[0].length];
		Heart hrt = new Heart();
		Coin cn = new Coin();
		Bomb bm = new Bomb();
		Key ky = new Key();
		
		
		for (int r = 0; r < p.length; r++)
		{
			for (int c = 0; c < p[r].length; c++)
			{
				if (p[r][c] != null)
				{
					//System.out.println(layout[r][c].getClass() + " and " + block.getClass() + " then " + poop.getClass());
					if (p[r][c].getClass().equals(hrt.getClass()))
					{
						result[r][c] = new Heart(50 + 25 + (c*100), 50 + 25 + (r*100), ((Math.random() >= .5)? 1: .5));
					}	else if (p[r][c].getClass().equals(cn.getClass()))
					{
						result[r][c] = new Coin(50 + 25 + (c*100), 50 + 25 + (r*100), ((Math.random() >= .5)? ((Math.random() >= .5)? 1: 2): ((Math.random() >= .5)? 5: 10)));
						//else if(layout[r][c].getClass().equals(poop.getClass()))	{
					}	else if (p[r][c].getClass().equals(bm.getClass()))
					{
						result[r][c] = new Bomb(50 + 25 + (c*100), 50 + 25 + (r*100), ((Math.random() >= (1.0/7.0))? 2: 1) );
					}	else if (p[r][c].getClass().equals(ky.getClass()))
					{
						result[r][c] = new Key(50 + 25 + (c*100), 50 + 25 + (r*100));
					}	else
					{
						result[r][c] = new Special_Item(50 + 25 + (c*100), 50 + 25 + (r*100), ((Math.random() < .66)? ((Math.random() < .5)? 1: 2): 3));
					}
						//result[r][c] = new PoopObject(50 + (c*layout[r][c].getXSize()), 50 + (r*layout[r][c].getYSize()));
					//}
				}
			}
		}
		//System.out.println("Test");
		return result;
	}
	
	//Static METHODS (used to create the map of rooms)
	
	/*
	 * Updates booleans that represent if a room exists above or below or left or right of a given room.
	 */
	public static void updateBools(Room[][] room)
	{
		for (int r = 0; r < room.length; r++)
		{
			for (int c = 0; c < room[r].length; c++)
			{
				if (room[r][c] != null)
				{
					
					//Sets values and makes sure nothing goes out of bounds
					/*
					if (r > 0)                          { room[r][c].setAbove(room[r-1][c] == null || (room[r-1][c] != null && room[r-1][c].getDel()));	}
					if (r < room.length - 1)     { room[r][c].setBelow(room[r+1][c] == null || (room[r+1][c] != null && room[r+1][c].getDel())); }
					if (c > 0)                          { room[r][c].setLeft(room[r][c-1] == null || (room[r][c-1] != null && room[r][c-1].getDel())); }
					if (c < room[r].length - 1)  { room[r][c].setRight(room[r][c+1] == null || (room[r][c+1] != null && room[r][c+1].getDel())); }
					*/
					
					/*
					if (r > 0)                          { room[r][c].setAbove(room[r-1][c] != null);	}
					if (r < room.length - 1)     { room[r][c].setBelow(room[r+1][c] != null); }
					if (c > 0)                          { room[r][c].setLeft(room[r][c-1] != null); }
					if (c < room[r].length - 1)  { room[r][c].setRight(room[r][c+1] != null); }
					*/
					
					//if r == 0, you are at the bottom 
					//When is there a room above???
					//room[r][c].setAbove(r > 0 && room[r-1][c] != null);
					//room[r][c].setBelow((r < room.length - 1) || room[r+1][c] != null);
					
					
					room[r][c].setAbove(!((r == 0) || room[r-1][c] == null));
					room[r][c].setBelow(!((r == room.length - 1) || room[r+1][c] == null));
					room[r][c].setLeft(!((c == 0) || room[r][c-1] == null));
					room[r][c].setRight(!((c == room[r].length - 1) || room[r][c+1] == null));
					
					
					//room[r][c].setAbove(b);
				}
			}
		}
	}
	
	
	
	/*
	 * Returns the amount of available paths another room can be generated at.
	 * This is used for the map generation. 
	 */
	public static int available(int r, int c, Room[][] map)
	{
		//Calls 'updateBools' to make sure the directions are correct.
		updateBools(map);
		int result = 0;
		
		//Transfers the boolean value to an integer value to add up all the possible paths
		//(boolean? 1: 0) == either 1 or 0. This corresponds to true = 1 and false = 0
		result += !map[r][c].getAbove()? 1: 0;
		result += !map[r][c].getBelow()? 1: 0;
		result += !map[r][c].getRight()? 1: 0;
		result += !map[r][c].getLeft()? 1: 0;
		
		//System.out.println(x);
		
		return result;
	}
	
	
	
	/*
	 * Used for map generation...
	 * This picks a random 'direction' out of available ones that will be used for the map generation
	 * Rather than return an actual DIRECTION, this returns the point in 'map' that the next method should call.
	 */
	public static Point pickDirection(int r, int c, Room[][] map)
	{
		//Sets this random value first so .available can call updateBools and assure all the added rooms are available.
		int rndPick = (int)((Math.random()*Room.available(r, c, map)));
		
		//Will contain all the possible choices
		List<Point> choices  = new ArrayList<> ();
		
		//Adds available slots to the ArrayList 'choices'
		if (!map[r][c].getBelow())	{choices.add(new Point(r+1, c));}
		if (!map[r][c].getAbove())	{choices.add(new Point(r-1, c));}
		if (!map[r][c].getLeft())		{choices.add(new Point(r, c-1));}
		if (!map[r][c].getRight())		{choices.add(new Point(r, c+1));}
		
		/* 
		List<Point> test = new ArrayList<> ();
		test.add(new Point(1,2));
		test.add(new Point(3,5));
		test.add(new Point(6,7));
		
		System.out.println(test.get(2));
		System.out.println(Room.available(r, c, map));
		*/
		
		
		//System.out.println("pickDirection int : " + rndPick);
		//System.out.println("pickDirection : " + choices.get((rndPick)));
		
		//Returns a random slot in 'choices'
		return choices.get((rndPick));
	}

	public long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}
	
	
	
}
