import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
//import java.awt.Font;
//import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//import javax.imageio.ImageIO;

public class Isaac extends Applet implements Runnable, KeyListener
{

	private static final long serialVersionUID = 1L;
	final static int WIDTH = 1400, HEIGHT = 800;
	Thread thread;
	
	Graphics gfx;
	Image img;
	
	AudioClip backgroundMusic;
	MiniMap mMap;
	
	//A map of rooms, the enemyRoomLayout to choose the random layout of rooms, the level so we know how hard to make
	//Stuff, and the currentRoom. when that changes, the entire load of the room changes.
	Room[][] map;
	ArrayList<Integer> enemyRoomLayout; private final int numOfLayouts = 7;

	int level;
	Point currentRoom;
	
	//An instance of isaac himself. Let's go Isaac!
	MainChar isaac;
	
	private boolean created, started, win, lose;
	
	
	//Instantiate everything from the form size to the room types.
	public void init(){
		this.resize(WIDTH, HEIGHT);
		
		created = started = win = false;
		
		img = createImage(WIDTH, HEIGHT);
		gfx = img.getGraphics();
		
		isaac = new MainChar();
		//System.out.println(isaac.getWidth(null) + " x " + isaac.getHeight(null));
		
		thread = new Thread(this);
		thread.start();
		
		this.addKeyListener(this);//Allows for key handling
		this.setFocusable(true);
		
		backgroundMusic = getAudioClip(getCodeBase(), "IsaacMusic.wav");
		backgroundMusic.play();

		enemyRoomLayout = new ArrayList<> ();
		for (int x = 0; x < numOfLayouts; x++)
		{
			enemyRoomLayout.add(x);
		}
		
		//Creates map of rooms
		level = 0;
		System.out.println("Start to create Map");
		createNewLevel();
	
		created = true;
		//this.showStatus("TEST");
		//this.setName("TEST THIS SHIT");
	}
	
	
	
	public void paint(Graphics g){
		//gfx.setColor(Color.black);
		//gfx.fillRect(0, 0, WIDTH,  HEIGHT);
		//gfx.reImage(testing.getImg(), 20, 20, 200, 200, this);
		//System.out.println("Point: " + currentRoom.toString());
		
		
		if (created)
		{
			if (started)
			{
				if (!win){
					if (!lose)
					{
						map[currentRoom.getRow()][currentRoom.getCol()].draw(gfx);
						isaac.draw(gfx);
						mMap = new MiniMap(map, currentRoom.getRow(), currentRoom.getCol(),WIDTH, HEIGHT);
						mMap.draw(gfx,  map);
						g.drawImage(img, 0, 0, this);
						checkEvents();
					} 	else
					{
						Image lose = null;
						try {
							lose = ImageIO.read(new File("lose.jpg"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						gfx.drawImage(lose, 0, 0, WIDTH, HEIGHT, this);
						
						Font display = new Font("SansSerif", Font.BOLD, 50);
						Color color = new Color(255, 0, 0);
						gfx.setFont(display);
						gfx.setColor(color);
						gfx.drawString("Programmers: Sean McNamee and Jacob DeRosa", 100, 100);
						gfx.drawString("Music By: Vincent Fasano", 400, 150);
						gfx.drawString("Thanks for Playing! I hope you had fun!", 220, 700);
						gfx.drawString("(Top left, you can click 'Applet >> Clone')", 200, 750);
						g.drawImage(img, 0, 0, this);
					}
				}	else
				{
					Image winPic = null;
					try {
						winPic = ImageIO.read(new File("win.jpg"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
					gfx.drawImage(winPic, 0, 0, WIDTH, HEIGHT, this);
					
					Font display = new Font("SansSerif", Font.BOLD, 50);
					Color color = new Color(255, 0, 0);
					gfx.setFont(display);
					gfx.setColor(color);
					gfx.drawString("Programmers: Sean McNamee and Jacob DeRosa", 300, 300);
					gfx.drawString("Music By: Vincent Fasano", 430, 350);
					gfx.drawString("YOU WIN!", 600, 250);
					
					g.drawImage(img, 0, 0, this);
				}
			}	else
			{
				Image startBack = null;
				try {
					startBack = ImageIO.read(new File("StartScreen.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				gfx.drawImage(startBack, 0, 0, WIDTH, HEIGHT, this);
				
				Font display = new Font("SansSerif", Font.BOLD, 24);
				Color color = new Color(255, 255, 255);
				gfx.setFont(display);
				gfx.setColor(color);
				gfx.drawString("Programmers: Sean McNamee and Jacob DeRosa", 400, 200);
				gfx.drawString("Music By: Vincent Fasano", 530, 250);
				gfx.drawString("Press Any Key To Begin", 540, 300);
				g.drawImage(img, 0, 0, this);
			}
			
		}
		
		
		/*
		ArrayList<RoomObject> o = map[currentRoom.getRow()][currentRoom.getCol()].getObjects();
		Rectangle[] objs = new Rectangle[o.size()]; 
		
		//Create Room Objects
		for (int k = 0; k < o.size(); k++)
		{
			objs[k] = new Rectangle(o.get(k).getX(), o.get(k).getY(), o.get(k).getXSize(), o.get(k).getYSize());
			g.drawRect((int)objs[k].getMinX(), (int)objs[k].getMinY(), (int)objs[k].getWidth(), (int)objs[k].getHeight());
		}
		*/
	}
	
	
	
	public void update(Graphics g){
		paint(g);
	}
	
	
	
	public void run() {
		System.out.println("Run was called!");
		for(;;){
			//System.out.println("Height: " + this.getHeight() + " , Width: " + this.getWidth());
			//System.out.println(this.getWidth());
			repaint();
			if (created && started)
			{
				if (!win && !lose){
					
					isaac.move(map[currentRoom.getRow()][currentRoom.getCol()]);
					isaac.att();
					
					map[currentRoom.getRow()][currentRoom.getCol()].move(map[currentRoom.getRow()][currentRoom.getCol()], isaac);
					map[currentRoom.getRow()][currentRoom.getCol()].att(isaac);
				}
			}
			//this.setName("test");
			//System.out.println("Repeat...");
			
			
			//Check in game things
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	

	//KEYS_BEING_PRESSED
	//KEY_EVENT_HANDLING
	//KEY_PRESSED_CHECK
	public void keyPressed(KeyEvent e) {
		
		if (!started)
		{
			started = true;
		}
		
		if (lose)
		{
			
		}
		/*
		System.out.println("Key Pressed");
		System.out.println(isaac.getYVel());
		System.out.println(isaac.getY());
		*/
		//System.out.println("WHAT");
		int key = e.getKeyCode();
		//System.out.println(key);
		//System.out.println(KeyEvent.VK_W);
		
		if (key == KeyEvent.VK_W){
			isaac.setMoveUp(true);
		}	
		if (key == KeyEvent.VK_S)	{
			isaac.setMoveDown(true);
		}
		if (key == KeyEvent.VK_A){
			//System.out.println("Set Left true (1)");
			isaac.setMoveLeft(true);
		}	
		if (key == KeyEvent.VK_D)	{
			isaac.setMoveRight(true);	
		}
		
		//Fire a Bullet
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
		{
			isaac.setShooting(true);
			isaac.setXFire((((key == KeyEvent.VK_LEFT)? -1:0) + ((key == KeyEvent.VK_RIGHT)? 1:0)));
			isaac.setYFire((((key == KeyEvent.VK_UP)? -1:0) + ((key == KeyEvent.VK_DOWN)? 1:0)));
			//isaac.att((((key == KeyEvent.VK_LEFT)? -1:0) + ((key == KeyEvent.VK_RIGHT)? 1:0)), (((key == KeyEvent.VK_UP)? -1:0) + ((key == KeyEvent.VK_DOWN)? 1:0)));
		}
		
		
		
		
		if (key == KeyEvent.VK_ENTER){
			//System.out.println(isaac.getX() + ", " + isaac.getY());
			System.out.println("Health: " + isaac.getHealth());
			System.out.println("Money: " + isaac.getMoney());
			System.out.println("Bombs: " + isaac.getBombs());
			System.out.println("Keys: " + isaac.getKeys());
			//createNewLevel();
			//isaac.att();
		}
		
		
		//CHEATS
		if (key == KeyEvent.VK_7)
		{
			//System.out.println("Max Life Up");
			isaac.addMaxHealth(1);
			isaac.addHealth(1);
		}
		
		if (key == KeyEvent.VK_8)
		{
			System.out.println("Added Money and Bombs and a Key");
			isaac.addMoney(1);
			isaac.addBomb(1);
			isaac.addKeys(1);
		}
		
		if (key == KeyEvent.VK_9)
		{
			System.out.println("Nuke");
			ArrayList<Enemy> en = new ArrayList<> ();
			map[currentRoom.getRow()][currentRoom.getCol()].setEnemies(en);
		}
		
		
		if (key == KeyEvent.VK_SHIFT || key == KeyEvent.VK_E)
		{
			if (isaac.getBombs() > 0)
			{
				System.out.println("USING BOMB");
				//ArrayList<RoomObject> objs = map[currentRoom.getRow()][currentRoom.getCol()].getObjects();
				
				map[currentRoom.getRow()][currentRoom.getCol()].activeBombs.add(new BombEntity((isaac.getX() + isaac.getImg().getWidth(null)/2), (isaac.getY() + isaac.getImg().getHeight(null)/2)));
				
				//map[currentRoom.getRow()][currentRoom.getCol()].setObjects(objs);
				
				
				isaac.addBomb(-1);
			}	else
			{
				System.out.println("No More Bombs..");
			}
		}
		//checkEvents();
		
		//Quick move through the map
		/*
		if(e.getKeyCode() == KeyEvent.VK_UP){
			if (map[currentRoom.getRow()][currentRoom.getCol()].getAbove())
			{
				currentRoom = new Point(currentRoom.getRow()-1, currentRoom.getCol());
				//img = createImage(WIDTH, HEIGHT);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if (map[currentRoom.getRow()][currentRoom.getCol()].getBelow())
			{
				currentRoom = new Point(currentRoom.getRow()+1, currentRoom.getCol());
				//img = createImage(WIDTH, HEIGHT);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if (map[currentRoom.getRow()][currentRoom.getCol()].getLeft())
			{
				currentRoom = new Point(currentRoom.getRow(), currentRoom.getCol()-1);
				//img = createImage(WIDTH, HEIGHT);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if (map[currentRoom.getRow()][currentRoom.getCol()].getRight())
				//img = createImage(WIDTH, HEIGHT);
			{
				currentRoom = new Point(currentRoom.getRow(), currentRoom.getCol()+1);
			}
		}
		
		System.out.println("Should be in a... " + map[currentRoom.getRow()][currentRoom.getCol()]);
		*/
			
	}
	
	
	
	public void keyReleased(KeyEvent e) {
		//if(e.getKeyCode() == KeyEvent.VK_UP){}
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_W){
			isaac.setMoveUp(false);
		}	
		if (key == KeyEvent.VK_S)	{
			isaac.setMoveDown(false);
		}
		if (key == KeyEvent.VK_A){
			isaac.setMoveLeft(false);;
		}	
		if (key == KeyEvent.VK_D)	{
			isaac.setMoveRight(false);;
		}
		
		//Fire a Bullet
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
		{
			isaac.setShooting(false);
			isaac.setXFire(0);
			isaac.setYFire(0);
			//isaac.att((((key == KeyEvent.VK_LEFT)? -1:0) + ((key == KeyEvent.VK_RIGHT)? 1:0)), (((key == KeyEvent.VK_UP)? -1:0) + ((key == KeyEvent.VK_DOWN)? 1:0)));
		}
		
	}
	
	
	public void keyTyped(KeyEvent e) {

	}
	

	//ReOccuring Events that must constantly be checked
	//This class should be moved into a timed call event.
	public void checkEvents()
	{
		Room rm = map[currentRoom.getRow()][currentRoom.getCol()];
		Rectangle iscLR = new Rectangle(isaac.getX(), isaac.getY() + isaac.getImg().getHeight(null)-20, 80, 10);
		Rectangle iscUD = new Rectangle(isaac.getX() + 49, isaac.getY() + isaac.getImg().getHeight(null)-30, 2, 30);
		
		Rectangle iscItem = new Rectangle(isaac.getX(), isaac.getY() + isaac.getImg().getHeight(null)-20, 80, 20);
		Rectangle isc = new Rectangle(isaac.getX()+10, isaac.getY()+10, isaac.getImg().getWidth(null)-20, isaac.getImg().getHeight(null)-20);
		
		
		ArrayList<Bullet> b = isaac.getBullets();
		Rectangle[] bull = new Rectangle[b.size()];
		
		Door[] d = rm.getDoors();
		Rectangle[] drs = new Rectangle[d.length];
		
		ArrayList<RoomObject> o = rm.getObjects();
		Rectangle[] obj = new Rectangle[o.size()];
		
		ArrayList<Pickup> is = rm.getItems();
		Rectangle[] itms = new Rectangle[is.size()];
		
		ArrayList<Enemy> e = rm.getEnemies();
		Rectangle[] enmy = new Rectangle[e.size()];
		
		if (is != null)
		{
			//Create item Objects
			//System.out.println(i.size());
			for (int k = 0; k < is.size(); k++) {
				itms[k] = new Rectangle(is.get(k).getXPos(), is.get(k).getYPos(), Pickup.xSize, Pickup.ySize);
			}
			
			//Check person getting to an item
			for (int it = 0; it < itms.length; it++)
			{
				if (itms[it].intersects(iscItem))
				{
					if (is.get(it).pickedUp(isaac)) //Allows for items that cost $
					{
						System.out.println("Picking an item up!");
						is.remove(it);
						rm.setItems(is);
						break;
					}
				}
			}
		}
		
		
		
		boolean remAllBul = false;
		
		//Create the doors
		for (int i = 0; i < d.length; i++)
		{
			if(d[i] != null)
			{
				if (d[i].getPos().getCol() > 400 && d[i].getPos().getCol() < 1000) //Up Down Doors
					{
						drs[i] = new Rectangle(d[i].getPos().getCol()+49, d[i].getPos().getRow(), d[i].getWidth()-80, d[i].getHeight());
					}	else
					{
						drs[i] = new Rectangle(d[i].getPos().getCol(), d[i].getPos().getRow()+40, d[i].getWidth(), d[i].getHeight()-80);
					}
			}
		}
		
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
		
		//Create the enemies
		for (int i = 0; i < enmy.length; i++)
		{
			enmy[i] = new Rectangle((int)e.get(i).getX(), (int)e.get(i).getY(), e.get(i).getXSize(), e.get(i).getYSize());
		}

		//Check isaac hitting into the doors
		for (int r = 0; r < drs.length; r++)
		{
			
			if (d[r] != null)
			{
				//System.out.println(isc.getCenterX() + ", " + isc.getCenterY());
				//System.out.println(r.getCenterX() + ", " + r.getCenterY());
				if (iscUD.intersects(drs[r]))
				{
					if (!rm.getDoors()[r].isEnemyLocked())
					{
						//remAllBul = true;
						//System.out.println("At a door!");
						if (rm.getDoors()[r].isLocked() && isaac.getKeys() > 0 || !rm.getDoors()[r].isLocked())
						{
							if (rm.getDoors()[r].isLocked() && isaac.getKeys() > 0)
							{
								unlockDoor(rm, r);
							}
						
							
							//Top Door
							if (isaac.getY() <= 100 && isaac.getMoveUp())
							{
								//if (rm.getDoors())
								remAllBul = true;
								isaac.setY(660);
								currentRoom = new Point(currentRoom.getRow()-1, currentRoom.getCol());
								rm.setEnterTime(System.currentTimeMillis());
								
								System.out.println("At Top Door");
							}	else
							
							//Bottom Door
							if (isaac.getY() >= 570 && isaac.getMoveDown())
							{
								remAllBul = true;
								isaac.setY(30);
								currentRoom = new Point(currentRoom.getRow()+1, currentRoom.getCol());
								isaac.setBullets(new ArrayList<> ());
								rm.setEnterTime(System.currentTimeMillis());
								System.out.println("At Bottom Door");
							}	
							
						}
						
					
					}
				}
				if (iscLR.intersects(drs[r]))
				{		
					if (!rm.getDoors()[r].isEnemyLocked())
					{
						if (rm.getDoors()[r].isLocked() && isaac.getKeys() > 0 || !rm.getDoors()[r].isLocked())
						{
							if (rm.getDoors()[r].isLocked() && isaac.getKeys() > 0)
							{
								unlockDoor(rm, r);
							}
							
							//Left Door
							if (isaac.getX() <= 100 && isaac.getMoveLeft())
							{
								remAllBul = true;
								isaac.setX(1290);
								currentRoom = new Point(currentRoom.getRow(), currentRoom.getCol()-1);
								isaac.setBullets(new ArrayList<> ());
								rm.setEnterTime(System.currentTimeMillis());
								System.out.println("At Left Door");
							}	else
							
							//Right Door
							if (isaac.getX() >= 1100 && isaac.getMoveRight())
							{
								remAllBul = true;
								isaac.setX(30);
								currentRoom = new Point(currentRoom.getRow(), currentRoom.getCol()+1);
								isaac.setBullets(new ArrayList<> ());
								rm.setEnterTime(System.currentTimeMillis());
								System.out.println("At Right Door");
							}
						
						}
					}
					
					
				}
			}
			
		}
		
		if (e != null)
		{
			for (int k = 0; k < enmy.length; k++)
			{
				if (enmy[k].intersects(isc))
				{
					if (e.get(k).getClass().toString().equals("class Boss_Enemy"))
					{
						if (e.get(k).getImg() != null)
						{
							if ((System.currentTimeMillis() - e.get(k).getlastTouch()) > 400)
							{
								isaac.addHealth(-.5);
								e.get(k).setlastTouch(System.currentTimeMillis());
							}
						}
					}	else
					{
						if ((System.currentTimeMillis() - e.get(k).getlastTouch()) > 400)
						{
							isaac.addHealth(-.5);
							e.get(k).setlastTouch(System.currentTimeMillis());
						}
					}
					//System.out.println("Hitting");
					
					
					break;
					
				}
				ArrayList<Bullet> enmyBulls = e.get(k).getBullets();
				for (int eB = e.get(k).getBullets().size()-1; eB >= 0; eB--)
				{
					Bullet enmyB = e.get(k).getBullets().get(eB);
					if (enmyB != null)
					{
						Rectangle enmyBull = new Rectangle(enmyB.getX(), enmyB.getY(), enmyB.getImage().getWidth(null), enmyB.getImage().getHeight(null));
						for (Rectangle object: obj)
						{
							if (object.intersects(enmyBull))
							{
								
								 enmyBulls.remove(enmyB);
								 e.get(k).setBullets(enmyBulls);
								 System.out.println("Enemy Bullet Hit a Object");
								 break;
							}
						}
						
						if (enmyBull.intersects(isc))
						{
							isaac.addHealth(-.5);
							e.get(k).getBullets().remove(enmyB);
							break;
						}
						
						if (  (enmyB.getX() + enmyB.getImage().getWidth(null)/2 > Isaac.WIDTH-70) && (enmyB.getXVel() > 0) || (enmyB.getX() + enmyB.getImage().getWidth(null)/2 < 70) && (enmyB.getXVel() < 0) || (enmyB.getY() + enmyB.getImage().getHeight(null)/2 > Isaac.HEIGHT-70) && (enmyB.getYVel() > 0) || (enmyB.getY() + enmyB.getImage().getHeight(null)/2 < 70) &&  ! (enmyB.getYVel() > 0))
						{
							//System.out.println("Removing a Bullet!");
							enmyBulls.remove(enmyB);
						}
					}
				}
				
					
				
			}
		}
			
		
		//Check bullets hitting the outside wall/room objects
		for (int i = bull.length-1; i >= 0; i--)
		{
			
			//If the bullet collides with something, destroy the bullet (and possibly call a method for that obect)
			
			//If the bullet hits a wall
			//if ((bull.getX()+b.getImage().getWidth(null) > WIDTH - 28) || (b.getX() < 28) || (b.getY() < 20) || (b.getY()+b.getImage().getHeight(null) > HEIGHT - 20))
			//System.out.println(bull.length);
			//System.out.println(bull[i].getCenterX() + ", " + bull[i].getCenterY());
			
			//Bllet hitting the outside wall
			if (  (bull[i].getCenterX() > WIDTH-70) && (b.get(i).getXVel() > 0) || (bull[i].getCenterX() < 70) && (b.get(i).getXVel() < 0) || (bull[i].getCenterY() > HEIGHT-70) && (b.get(i).getYVel() > 0) || (bull[i].getCenterY() < 70) &&  ! (b.get(i).getYVel() > 0))
			{
				//System.out.println("Removing a Bullet!");
				b.remove(i);
			}
			/*
			for (Rectangle ob: obj)
			{
			*/
			
			//Bullet hitting an object
			if (o != null)
			{
				for (int k = 0; k < obj.length; k++)
				{
					//System.out.println("Block at: " + ob.getCenterX() + ", " + ob.getCenterY());
					if (obj[k].intersects(bull[i]))
					{
						
						if (o.get(k).getClass().toString().equals("class PoopObject"))
						{
							((PoopObject)o.get(k)).gotHit();
							if (((PoopObject)o.get(k)).getLife() < 0)
							{
								
								
								if (Math.random() >= .75)//25% chance to drop something
								{
									map[currentRoom.getRow()][currentRoom.getCol()].rndItemDrop(o.get(k));
								}
								
								o.remove(k);
							}
						}	else	if (o.get(k).getClass().toString().equals("class FireObject"))
						{
							((FireObject)o.get(k)).gotHit();
							
							//System.out.println(((FireObject)o.get(k)).getLife());
							if (((FireObject)o.get(k)).getLife() < 0)
							{
								
								
								if (Math.random() >= .75)//25% chance to drop something
								{
									map[currentRoom.getRow()][currentRoom.getCol()].rndItemDrop(o.get(k));
								}
								
								o.remove(k);
							}
						}
						
						//System.out.println("Removing a Bullet!");
						b.remove(i);
						isaac.setBullets(b);
						break;
						//System.out.println(o.get(k).getClass());
						
					}
				}
			}
			
			
			if (e != null)
			{
				for (int k = 0; k < enmy.length; k++)
				{
					if (enmy[k] != null)
					{
						if (enmy[k].intersects(bull[i]))
						{
							if (e.get(k).getHealth() <= 1)
							{
								if (e.get(k).getClass().toString().equals("class MoterFly_Enemy"))
								{
									double sinangle = (Math.random()*2*Math.PI);
									//System.out.println(sinangle);
									double distance = Math.random()*30;
									
									int newX1 = ((int)(e.get(k).getX() + Math.cos(sinangle)*distance));
									int newY1 = ((int)(e.get(k).getY() + Math.sin(sinangle)*distance));
									
									int newX2 = ((int)(e.get(k).getX() - Math.cos(sinangle)*distance));
									int newY2 = ((int)(e.get(k).getY() - Math.sin(sinangle)*distance));
									
									e.add(new AttackFly_Enemy(newX1, newY1));
									e.add(new AttackFly_Enemy(newX2, newY2));
									
								} 	else if (e.get(k).getClass().toString().equals("class Boss_Enemy"))
								{
									win = true;
								}
								if (Math.random() < .10)
								{
									map[currentRoom.getRow()][currentRoom.getCol()].rndItemDrop(e.get(k));
								}
								
								
								e.remove(k);
							}	else
							{
								e.get(k).addHealth(-1);
							}
							
							if (isaac.getBullets().size() > 0)
							{
								b.remove(i);
								isaac.setBullets(b);
							}
							break;
							
						}
						
					
					}
				}
			}
			
			
		}
		
		if (remAllBul)
		{
			if (b != null)
			{
				for (int i = b.size()-1; i >= 0; i--)
				{
					b.remove(i);
				}
			}
			
			if (d != null)
			{
				if (!rm.getKilled())
				{
					for (Door dr : d)
					{
						if (dr != null)
						{
							dr.setEnemyLocked(true);
							//System.out.println("Locking door--.--");
						}
					}
				}
			}
			//System.out.println("TEst");
			
		}
		//ArrayList<Bullet> a = new ArrayList<> ();
		isaac.setBullets(b);
		
		if (isaac.getHealth() <= 0)
		{
			lose = true;
		}
	}
	
	public void unlockDoor(Room rm, int door)
	{
		isaac.addKeys(-1);
		
		rm.getDoors()[door].toggleDoor();
		System.out.println("Toggling Door " + door);
		
		if (door == 0)
		{
			map[currentRoom.getRow()-1][currentRoom.getCol()].getDoors()[2].toggleDoor();
			System.out.println("Toggling Bottom Door");
		}	else	if (door == 2)
		{
			map[currentRoom.getRow()+1][currentRoom.getCol()].getDoors()[0].toggleDoor();
			System.out.println("Toggling Top Door");
		}	else 	if (door == 1)
		{
			map[currentRoom.getRow()][currentRoom.getCol()-1].getDoors()[3].toggleDoor();
			System.out.println("Toggling Right Door");
		}	else	if (door == 3)
		{
			map[currentRoom.getRow()][currentRoom.getCol()+1].getDoors()[1].toggleDoor();
			System.out.println("Toggling Left Door");
		}	else
		{
			System.out.println("Error with unlocking doors!");
		}
	}
	
	public void createNewLevel()
	{
		level ++;
		int size = 15;
		int rooms = (int)(((Math.random()*4)-1)+(2*level + 7));
		int row = 7;//(int)((Math.random()*size-2)+2);
		int col = 7;//(int)((Math.random()*size-2)+2);
		currentRoom = new Point(0,0);
		
		map = new Room[size][size];
		map[row][col] = new Room(); //Must manually create the starting room
		makeMap(rooms-1, row, col);//Create the map
		
		fixMap(row, col);//Fixes it up and condenses the size (Can technically choose secret rooms here)
		
		printMap();
		
		pickRoomTypes();
		
		printMap();
		
		setMapDoors();
		
		System.out.println("About to pick Room Layouts");
		
		//Choosing layouts
		pickRoomLayouts();
		pickSpecialtyLayout();
	
		
		System.out.println("Level " + level);
	}

	public Room getRoom(Point where) {	return map[where.getRow()][where.getCol()]; }

	public void makeMap(int roomsleft, int r, int c)
	{
		
		//System.out.println("Enter makeMap: " + roomsleft + " " + r + " " + c);
		
		if (roomsleft > 0)
		{
			
			int branches; int max;
			
			//Sets how MANY branches there will be going off the current Room
			if (roomsleft <= (Room.available(r,  c, map)))
			{
				//Lets say you can go three possible ways, but can only make 2 more rooms. The max that this allows
				//is for 2 rooms in that case
				//1 -> the number of rooms that still have to be made (More or less branches based off of random)
				max = roomsleft;
			}	else	{
				//Otherwise the max amount of rooms you can create is the max number of open ways there are.
				max = Room.available(r, c, map);
			}
			
			branches = (int)((Math.random()*( max + 3 )) -2 );
			if (branches <= 0) { branches = 1; }
			
			
			//System.out.println("Max Branches: " + max + ", Chosen: " + branches);
			
			//Creates the rooms around the current room (randomly placed) as to prevent error
			//If the method were to call itself before the rooms were created, it could possibly try to create a room twice
			int[][] info = new int[branches][3];
			
			for(int i = 0; i < max; i++)//Makes 'branches' number of rooms
			{
				Point p = Room.pickDirection(r, c, map);//Picks the spot randomly
				
				
				if (i < branches)
				{
					info[i][0] = p.getRow();
					info[i][1] = p.getCol();
					
					map[info[i][0]][info[i][1]] = new Room(false);
					
					if ((roomsleft - branches) > 0) //Makes sure you don't get a divide by 0 error
					{
						info[i][2] = ((roomsleft - branches) / (branches));
						
						if (i == branches-1)
						{
							info[i][2] += ((roomsleft - branches) % (branches));
						}
						
					}	else	{
						info[i][2] = 0;
					}
					
				}	else
				{
					
					map[p.getRow()][p.getCol()] = new Room(true);
					//System.out.println("Created Void room at " + p.getRow() + ", " +p.getCol());
					
				}

				
			}
				
			//Recalls the method at the end
			for (int[] nmap: info)
			{
				makeMap(nmap[2], nmap[0], nmap[1]);
			}
			
		}
		
	}
	
	public void fixMap(int row, int col)
	{
		//Gets rid of bad rooms and fixes the size
		
		
		int minr = 0, maxr = map.length-1, minc = 0, maxc = map[0].length-1;
		
		//Min Row
		for (int r = row; r >= 0; r--)
		{
			if (isEmptyRow(map[r]))
			{
				minr = r+1;
				r = -1;
			}
		}
		//Max Row
		for (int r = row; r < map.length; r++)
		{
			if (isEmptyRow(map[r]))
			{
				maxr = r;
				r = map.length;
			}
		}
		//Min Column
		for (int c = col; c >= 0; c--)
		{
			if (isEmptyCol(c))
			{
				minc = c+1;
				c = -1;
			}
		}
		//Max Column
		for (int c = col; c < map[0].length; c++)
		{
			if (isEmptyCol(c))
			{
				maxc = c;
				c = map[0].length;
			}
		}
		
		//System.out.println("From (" + minr + ", " + minc + ") to (" + maxr + ", " + maxc + ")");
		
		//Leave an empty set around so Outside rooms can be added.
		maxc ++;
		maxr ++;
		minc -= (minc > 0)? 1: 0;
		minr -= (minr > 0)? 1: 0;
		
		//Keeps only good rooms in array 'map'
		Room[][] newmap = new Room[maxr-minr][maxc-minc];
		for (int r = minr; r < maxr; r++)
		{	
			for (int c = minc; c < maxc; c++)
			{
				if (map[r][c] != null && !map[r][c].getDel())
				{
					newmap[r-minr][c-minc] = map[r][c];
				}
			}
		}
		
		map = newmap;
		
	}

	public boolean isEmptyRow(Room[] row)
	{
		boolean result = true;
		
		for (Room c : row)
		{
			if (c != null && !c.getDel())
			{
				result = false;
			}
		}
		
		return result;
	}
		
	public boolean isEmptyCol(int col)
	{
		boolean result = true;
		
		for (int r = 0; r < map.length; r++)
		{
			if (map[r][col] != null && !map[r][col].getDel())
			{
				result = false;
			}
		}
		
		return result;
	}
	
	public void pickRoomTypes()
	{
		Room.updateBools(map);
		
		Room Spawnroom = null;
		Point spawnspot = null;
		
		int edgeCount = 0;
		
		for (int r = 0; r < map.length; r ++)
		{
			for (int c = 0; c < map[r].length; c++)
			{
				if (map[r][c] != null)
				{
					//Pick the room with the most surrounding rooms as the spawnroom
					if (Spawnroom == null)
					{
						Spawnroom = map[r][c];
						spawnspot = new Point(r, c);
					}	else 	if (map[r][c].surroundingRooms() >= Spawnroom.surroundingRooms())
					{
						if (((int)Math.random()*3 >= 1) || map[r][c].surroundingRooms() > Spawnroom.surroundingRooms())
						{
							/*
							if (r > 0 && map[r-1][c].getType().toString().equals("class BossRoom"))
							{
								
							}
							*/
							Spawnroom = map[r][c];
							spawnspot = new Point(r, c);
						}
					}
					
					//Find out how many edge rooms are available
					if (map[r][c].surroundingRooms() <= 1)
					{
						edgeCount ++;
					}
				}
			}
		}//End of For Each loop
		
		map[spawnspot.getRow()][spawnspot.getCol()] = new SpawnRoom(Spawnroom.getAbove(), Spawnroom.getBelow(), Spawnroom.getLeft(), Spawnroom.getRight());
		System.out.println("SpawnRoom at " + spawnspot.getRow() + ", " + spawnspot.getCol() );
		System.out.println(edgeCount + " edge rooms.");
		currentRoom = spawnspot;
		
		if (edgeCount < 6)
		{
			for (int i = 0; i < (6 - edgeCount); i++)
			{
				System.out.println("Missing an edge... (" + i + " / " + (6 - edgeCount - 1) + " )");
				makeNewEdge();
			}
		}
		
		ArrayList<String> rndRoom = new ArrayList<> ();
		rndRoom.add("Boss");
		rndRoom.add("Shop");
		rndRoom.add("Treasure");
				
		edgeCount = 0;
		for (int r = 0; r < map.length; r++)
		{
			for (int c = 0; c < map[r].length; c++)
			{
				if (map[r][c] != null)
				{
					
					//Find out how many edge rooms are available
					if (map[r][c].surroundingRooms() == 1 && (map[r][c].getAbove() && !map[r-1][c].getType().equals("SpawnRoom") || map[r][c].getBelow() && !map[r+1][c].getType().equals("SpawnRoom") || map[r][c].getLeft() && !map[r][c-1].getType().equals("SpawnRoom") || map[r][c].getRight() && !map[r][c+1].getType().equals("SpawnRoom")))
					{
						edgeCount ++;
						if (edgeCount <= 3)
						{
							
							//if (map[r][c].getAbove() && !map[r-1][c].getType().equals("SpawnRoom") || map[r][c].getBelow() && !map[r+1][c].getType().equals("SpawnRoom") || map[r][c].getLeft() && !map[r][c-1].getType().equals("SpawnRoom") || map[r][c].getRight() && !map[r][c+1].getType().equals("SpawnRoom"))
							
							int pick = (int)(Math.random()*rndRoom.size());
							if (rndRoom.get(pick).equals("Boss"))
							{
								map[r][c] = new BossRoom(map[r][c].getAbove(), map[r][c].getBelow(), map[r][c].getLeft(), map[r][c].getRight());
								System.out.println("New BossRoom made");
							}	else if (rndRoom.get(pick).equals("Shop"))
							{
								map[r][c] = new ShopRoom(map[r][c].getAbove(), map[r][c].getBelow(), map[r][c].getLeft(), map[r][c].getRight());
								System.out.println("New ShopRoom made");
							}	else
							{
								map[r][c] = new TreasureRoom(map[r][c].getAbove(), map[r][c].getBelow(), map[r][c].getLeft(), map[r][c].getRight());
								System.out.println("New TreasureRoom made");
							}
							
							rndRoom.remove(pick);
							
						}	else
						{
							map[r][c] = new EnemyRoom(map[r][c].getAbove(), map[r][c].getBelow(), map[r][c].getLeft(), map[r][c].getRight());
							System.out.println("New EnemyRoom made");
						}
					}	else if (map[r][c].getType().equals("Undefined"))
					{
						map[r][c] = new EnemyRoom(map[r][c].getAbove(), map[r][c].getBelow(), map[r][c].getLeft(), map[r][c].getRight());
						System.out.println("New EnemyRoom made");
					}
				}
			}
		}//End of For Each loop
		
	}
	
	public void makeNewEdge()
	{
		for (int r = 1; r < map.length-1; r++)
		{
			for (int c = 1; c < map[0].length-1; c++)
			{
				
				//Go through every room in the map
				if (map[r][c] != null)
				{
					
					if (!map[r][c].getClass().toString().equals("class SpawnRoom"))//prevents too many specialty rooms from being next to spawn
					{
						System.out.println("checking point " + r + ", " + c);
						if (map[r][c].surroundingRooms() > 1 && map[r][c].surroundingRooms() < 4)
						{
							if (!map[r][c].getAbove())
							{
								if (map[r-1][c-1] == null && map[r-1][c+1] == null && ((r-1) == 0 || map[r-2][c] == null))
								{
									
									map[r-1][c] = new Room(false, true, false, false, "Undefined");
									map[r][c].setAbove(true);
									System.out.println("Made a new room at " + (r-1) + ", " + (c));
									return;
									/*
									r = map.length;
									c = map[0].length;
									*/
								}
							}
							
							if (!map[r][c].getBelow())
							{
								if (map[r+1][c-1] == null && map[r+1][c+1] == null && ((r+1) == map.length-1 || map[r+2][c] == null))
								{
									map[r+1][c] = new Room(true, false, false, false, "Undefined");
									map[r][c].setBelow(true);
									System.out.println("Made a new room at " + (r+1) + ", " + (c));
									return;
									/*
									r = map.length;
									c = map[0].length;
									*/
								}		
							}
							
							if (!map[r][c].getLeft()){
								if (map[r-1][c-1] == null && map[r+1][c-1] == null && ((c-1) == 0 || map[r][c-2] == null))
								{
									map[r][c-1] = new Room(false, false, false, true, "Undefined");
									map[r][c].setLeft(true);
									System.out.println("Made a new room at " + (r) + ", " + (c-1));
									return;
									/*
									r = map.length;
									c = map[0].length;
									*/
								}
							}
							
							if (!map[r][c].getRight())
							{
								if (map[r-1][c+1] == null && map[r+1][c+1] == null && ((c+1) == map[r].length-1 || map[r][c+2] == null))
								{
									map[r][c+1] = new Room(false, false, true, false, "Undefined");
									map[r][c].setRight(true);
									System.out.println("Made a new room at " + (r) + ", " + (c+1));
									return;
									/*
									r = map.length;
									c = map[0].length;
									*/
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public void printMap()
	{
		//Print out the layout of the MAP
		for (Room[] r : map)
		{
			for (Room c : r)
			{
				if (c != null)
				{
					//System.out.println(c.getType());
					if (c.getType().equals("SpawnRoom"))
					{
						System.out.print("=");
					}	else if (c.getType().equals("ShopRoom"))
					{
						System.out.print("S");
					}	else if (c.getType().equals("BossRoom"))
					{
						System.out.print("B");
					}	else if (c.getType().equals("TreasureRoom"))
					{
						System.out.print("T");
					}	else if (c.getType().equals("EnemyRoom"))
					{
						System.out.print("E");
					}	else
					{
						System.out.print("X");
					}
				} else	{
					System.out.print("O");
				}
			}
			System.out.println("");
			
		}
		
		System.out.println("");
		System.out.println("Created a " + map.length + " x "+ map[0].length + " map with " + numofRooms() + " rooms.");
		
	}
	
	public int numofRooms()
	{
		int result = 0;
		
		for (Room[] r : map)
		{
			for (Room c : r)
			{
				result += (c != null)? 1: 0;
			}
		}
		
		return result;
	}
	
	public void setMapDoors()
	{
		
		//System.out.println(map.length + " x " + map[0].length);
				for (int r = 0; r < map.length; r ++)
				{
					for (int c = 0; c < map[r].length; c++)
					{
						//In Each room....
						//System.out.println(r + " x " + c);
						if (map[r][c] != null)
						{
							//Make an array of the surrounding room types
							String[] around = new String[4];
							//int index = -1;
							//Top Door
							if (r > 0 && map[r-1][c] != null)//Make sure you don't go out of bounds when checking surrounding rooms...
							{
								//If the above room is a room
								//index++;
								
								//Add that room type
								if (map[r][c].getType().equals("EnemyRoom") || map[r][c].getType().equals("SpawnRoom"))
								{
									around[0] = map[r-1][c].getType();
								}	else
								{
									around[0] = map[r][c].getType();
								}
								
							}
							
							//Bottom Door
							if (r < map.length-1 && map[r+1][c] != null)
							{
								//index++;

								if (map[r][c].getType().equals("EnemyRoom") || map[r][c].getType().equals("SpawnRoom"))
								{
									around[2] = map[r+1][c].getType();
								}	else
								{
									around[2] = map[r][c].getType();
								}
							}
							
							//Left Door
							if (c > 0 && map[r][c-1] != null)
							{
								//index++;

								if (map[r][c].getType().equals("EnemyRoom") || map[r][c].getType().equals("SpawnRoom"))
								{
									around[1] = map[r][c-1].getType();
								}	else
								{
									around[1] = map[r][c].getType();
								}
								
							}
							
							//Right Door
							if (c < map[0].length-1 && map[r][c+1] != null)
							{
								//index++;

								if (map[r][c].getType().equals("EnemyRoom") || map[r][c].getType().equals("SpawnRoom"))
								{
									around[3] = map[r][c+1].getType();
								}	else
								{
									around[3] = map[r][c].getType();
								}
							}
							
						
							//Print out the room types surrounding the room
							for (String s : around)
							{
								System.out.print(s + " ");
							}
							System.out.println("");
							
							
							map[r][c].setDoors(around);
						}
						
					}
				}
	}
	
	
	public void pickRoomLayouts()
	{
		System.out.println("Hey VSauce");
		
		
		for (Room[] r : map)
		{
			for (Room c : r)
			{
				if (c != null)
				{
					if (c.getType().equals("EnemyRoom"))
					{
						System.out.print(enemyRoomLayout);
						
						//Check if all the room Layout were picked
						
						//If they are all picked, reset them
						if (enemyRoomLayout.size() <= 0)
						{
							System.out.println("Room layouts used up.. Creating More!");
							for (int x = 0; x < numOfLayouts; x++)
							{
								enemyRoomLayout.add(x);
							}
							System.out.println(enemyRoomLayout);
						}
						
						//Pick random room layout
						
						int rndRoom = (int)(Math.random()*enemyRoomLayout.size());
						
						//System.out.println(rndRoom);
						//Make that room
						c.setLayout(enemyRoomLayout.get(rndRoom));
						System.out.println(" " + enemyRoomLayout.get(rndRoom));
						//System.out.println(c.getClass());
						enemyRoomLayout.remove(rndRoom);
					}
				}
			}
			
		}
		
		//System.out.println("Test Picking Room Layout");
		//map[currentRoom.getRow()][currentRoom.getCol()].setLayout(0);
		System.out.println("Michael Here");
	}

	public void pickSpecialtyLayout()
	{
		for (Room[] r : map)
		{
			for (Room c : r)
			{
				if (c != null)
				{
					if (c.getType().equals("TreasureRoom"))
					{
						int rndRoom = (int)(Math.random()*2); //Zero or 1
						((TreasureRoom)c).setTreasureLayout(rndRoom);
					}	else if (c.getType().equals("ShopRoom"))
					{
						((ShopRoom)c).setShopLayout();
					}	else if (c.getType().equals("BossRoom"))
					{
						int rndRoom = (int)(Math.random()*2); //Zero or 1
						((BossRoom)c).setBossLayout();
					}
				}
			}
		}
	}
	
	
}
