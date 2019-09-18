import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MainChar implements Moveable {

	Image[] isaac;
	int img;
	private final int height = 100, width = 80;
	
	//Movement
	private int xPos, yPos;
	private double xVel, yVel;
	private final double friction = .90;
	private boolean moveUp, moveDown, moveLeft, moveRight;


	//Bullets
	private ArrayList<Bullet> bullets;
	private int bulletSpeed;
	private boolean isShooting;
	private int xFire, yFire;
	//Bullet Delay
	private long lastShotTime;
	private final int timeDelay = 400;
	

	//Stats
	private double health; private double maxHealth;
	private int money;
	private double bulletRange;
	private int bombs;
	private int keys;
	private double maxVel;

	public MainChar() {
		isaac = new Image[2];
		isaac[0] = null;
		bullets = new ArrayList<>();
		lastShotTime = 0;
		moveUp = moveDown = moveLeft = moveRight = false;
		isShooting = false;
		health = maxHealth = 3.0;
		bulletRange = 5.0;
		bombs = 0;
		keys = 0; 
		xFire = yFire = 0;
		// fireUp = fireDown = fireLeft = fireRight = false;
		bulletSpeed = 5;
		try {
			isaac[0] = ImageIO.read(new File("Isaac_Standing.png"));
		} catch (IOException e) {
		}

		isaac[1] = Toolkit.getDefaultToolkit().getImage("Isaac_Walking.gif");
		img = (Math.abs(xVel) + Math.abs(yVel) > 0) ? 1 : 0;
		xPos = 660;
		yPos = 500;
		maxVel = 3;
	}

	public void att() {
		// int xFire = (fireLeft? -1: 0) + (fireRight? 1: 0);
		// int yFire = (fireUp? -1: 0) + (fireDown? 1: 0);

		// System.out.println(fireUp);
		if (isShooting)
		{
			//System.out.println("Fire!");
			if ((System.currentTimeMillis() - lastShotTime) > timeDelay)
			{
				lastShotTime = System.currentTimeMillis();
				bullets.add(new Bullet(xPos + width / 3, yPos + height / 3, bulletSpeed * xFire, bulletSpeed * yFire));
			}
		}
		
		
		
	}

	public void draw(Graphics g) {
		g.drawImage(isaac[img], xPos, yPos, width, height, null);
		// System.out.println(yVel);
		if (bullets != null)
		{
			//int count = 0;
			for (int b = 0; b < bullets.size(); b++)
			{
				if (bullets.get(b) != null)
				{
					//System.out.println("Count: " + count + ", Range: " + (bullets.size()-1));
					if (bullets.get(b).getDistance() < bulletRange*100)
					{
						bullets.get(b).draw(g);
					}	else
					{
						bullets.remove(b);
						b--;
						//break;
					}
				}
			}
			
			
			if (bullets != null)
			{
				for (int b = 0; b < bullets.size(); b++)
				{
					if (bullets.get(b) != null)
					{
						//System.out.println("Count: " + count + ", Range: " + (bullets.size()-1));
						if (bullets.get(b).getDistance() < bulletRange*100)
						{
							bullets.get(b).draw(g);
						}	else
						{
							bullets.remove(b);
							//break;
						}
						//count++;
					}
				}
			}

		}
		
		Font display = new Font("SansSerif", Font.BOLD, 24);
		Color color = new Color(255, 255, 255, 127);
		g.setFont(display);
		g.setColor(color);
		String m = "x " + money;
		String b = "x " + bombs;
		String k = "x " + keys;

		
		Image bomb = null;
		Image key = null;
		Image coin = null;
		try {
			bomb = ImageIO.read(new File("DispBomb.png"));
			key = ImageIO.read(new File("DispKey.png"));
			coin = ImageIO.read(new File("DispCoin.png"));
		} catch (IOException e) {
		}
		
		int drawX = 30, drawY = 100;
		
		g.drawImage(coin, drawX-20, drawY-20, 20, 20, null);
		g.drawString(m, drawX, drawY);
		
		drawY += 25;
		
		g.drawImage(bomb, drawX-20, drawY-20, 20, 20, null);
		g.drawString(b, drawX, drawY);
		
		drawY += 25;
		
		g.drawImage(key, drawX-20, drawY-20, 20, 20, null);
		g.drawString(k, drawX, drawY);
		
		if (health > 0)
		{
			Image heart = null;
			try {
				heart = ImageIO.read(new File("DispHeart.png"));
			} catch (IOException e) {
			}
			Image halfheart = null;
			try {
				halfheart = ImageIO.read(new File("HalfDispHeart.png"));
			} catch (IOException e) {
			}
			Image noheart = null;
			try {
				noheart = ImageIO.read(new File("EmptyDispHeart.png"));
			} catch (IOException e) {
			}
			
			//Display hearts
			int xStart = 20, yStart = 10, size = 30;
			int xP = xStart;
			int yP = yStart;
			int rowChecker = 1;
			
			for (int count = 0; count < maxHealth; count++)
			{
				xP += (size+5);
				int oneRow = 6;
				if (count+1 > (rowChecker*oneRow))
				{
					xP -= (size+5)*(oneRow);
					yP += (size+5);
					rowChecker ++;
				}
				/*
				if (count > oneRow)
				{
					xP -= (oneRow*size+5);
					yP = 
				}
				*/
				

				
				if ((health-count) >= 1)
				{
					g.drawImage(heart, xP, yP, size, size, null);
				}	else if ((health-count) >= .5)
				{
					g.drawImage(halfheart, xP, yP, size, size, null);
				}	else
				{
					g.drawImage(noheart, xP, yP, size, size, null);
				}
			}
		}
		
		
		
	}

	public void move(Room r) {
		// System.out.println("Move Called.");
		/*
		 * xPos += (int)xVel; yPos += (int)yVel;
		 */
		if (moveUp) {
			if (yVel > -maxVel) {
				yVel -= .07;
			} else {
				yVel = -maxVel;
			}

		} else if (moveDown) {
			if (yVel < maxVel) {
				yVel += .07;
			} else {
				yVel = maxVel;
			}
		} else {
			yVel *= friction;
			if (Math.abs(yVel) < 1) {
				yVel = 0;
			}
		}

		if (moveLeft) {
			if (xVel > -maxVel) {
				xVel -= .07;
			} else {
				xVel = -maxVel;
			}
		} else if (moveRight) {
			if (xVel < maxVel) {
				xVel += .07;
			} else {
				xVel = maxVel;
			}
		} else {
			xVel *= friction;
			if (Math.abs(xVel) < 1) {
				xVel = 0;
			}
		}
		int minX = 30;
		int maxX = Isaac.WIDTH - isaac[img].getWidth(null) - 20;
		int minY = -20;
		int maxY = Isaac.HEIGHT - isaac[img].getHeight(null) - 50;

		boolean checkSHIT = false;//, checkX = false;
		//Rectangle isc = new Rectangle(xPos + 10, yPos + isaac[img].getHeight(null) - 30, width - 20, 20);
		Rectangle isc = new Rectangle(xPos+15, yPos+70, width-30, height-70);
		ArrayList<RoomObject> o = r.getObjects();
		Rectangle[] objs = new Rectangle[o.size()];
		
		
		
		// Create Room Objects
		for (int k = 0; k < o.size(); k++) {
			objs[k] = new Rectangle(o.get(k).getX(), o.get(k).getY(), o.get(k).getXSize(), o.get(k).getYSize());
		}
		
		
		

		// Check person hitting object
		for (int q = 0; q < o.size(); q++) {
			RoomObject ob = o.get(q);
			Rectangle rec = objs[q];
			if (isc.intersects(rec)) {
				
				//Moving up or down
				//Allowed to move up if Isaac is moving up and is above the box (and the Velocity is in that direction)
				//Allowed to move down if Isaac is moving down and is below the box (and the Velocity is in that direction) (Can only move/up and down if Isaac is not in the box L/R wise
				if ((moveUp || moveDown) && !(moveUp && isc.getCenterY() < rec.getMaxY() && yVel < 0) && !(moveDown && isc.getCenterY() > rec.getMinY() && yVel > 0 ) && !(isc.getMinX() + 10 >= rec.getMaxX()) && !(isc.getMaxX() - 10 < rec.getMinX()))
				{
					moveUp = moveDown = false;
					yVel = 0.0;
					
					if (ob.getClass().toString().equals("class FireObject"))
					{
						if (System.currentTimeMillis() - ((FireObject)ob).getLastTouch() > 400)
						{
							((FireObject)ob).setLastTouch(System.currentTimeMillis());
							System.out.println("Taken Half a heart of damage!");
							health -= .5;
						}
					}
					
					//checkSHIT = true;
					//System.out.println("Colliding...Y" + moveUp + moveDown);
				}
				if ((moveLeft || moveRight) && !(moveLeft && isc.getCenterX() < rec.getMaxX() && xVel < 0) && !(moveRight && isc.getCenterX() > rec.getMinX() && xVel > 0) && !(isc.getMinY() + 10 > rec.getMaxY()) && !(isc.getMaxY() - 10 < rec.getMinY())) {
					moveLeft = moveRight = false;
					xVel = 0.0;
					
					if (ob.getClass().toString().equals("class FireObject"))
					{
						if (System.currentTimeMillis() - ((FireObject)ob).getLastTouch() > 400)
						{
							((FireObject)ob).setLastTouch(System.currentTimeMillis());
							System.out.println("Taken Half a heart of damage!");
							health -= .5;
						}
					}
					//checkSHIT = true;
					//checkX = true;
					//System.out.println("Colliding...X" + moveLeft + moveRight);
				}
				
				
				
			}
			checkSHIT = true;
		}

		/*
		 * if (!checkhit) { System.out.println("Switching!"); checkhit = true; }
		 * else { System.out.
		 * println("This should never truely happen... --------------"); }
		 */

		// System.out.println("When Will I occur?");
		// Actually add to the heights
		if (checkSHIT || o.size() <= 0) {
			if ((xPos > minX && xPos < maxX || xPos <= minX && xVel > 0 || xPos >= maxX && xVel < 0) && xVel != 0) {
				// System.out.println("X Velocity: " + xVel);
				xPos += xVel;
				//System.out.println("LEFT: " + moveLeft + ", RIGHT: " + moveRight + " (3)");
				//System.out.println(checkSHIT + ", X: " + checkX);
			}

			if ((yPos > minY && yPos < maxY || yPos <= minY && yVel > 0 || yPos >= maxY && yVel < 0) && yVel != 0) {
				// System.out.println("Y Velocity: " + yVel);
				yPos += yVel;
				//System.out.println("UP: " + moveUp + ", DOWN: " + moveDown);
				//System.out.println(checkSHIT);
			}
		}

		img = (Math.abs(xVel) + Math.abs(yVel) > 0) ? 1 : 0;

		for (Bullet b : bullets) {
			b.move();
		}
	}

	public double getFriction() {
		return friction;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public void setX(int x) {
		xPos = x;
	}

	public void setY(int y) {
		yPos = y;
	}

	public int getXV() {
		return (int) xVel;
	}

	public int getYV() {
		return (int) yVel;
	}

	public void setXV(int speed) {
		xVel = speed;
	}

	public void setYV(int speed) {
		yVel = speed;
	}

	public boolean getMoveUp() {
		return moveUp;
	}

	public boolean getMoveDown() {
		return moveDown;
	}

	public boolean getMoveLeft() {
		return moveLeft;
	}

	public boolean getMoveRight() {
		return moveRight;
	}

	public void setMoveUp(boolean u) {
		moveUp = u;
	}

	public void setMoveDown(boolean d) {
		moveDown = d;
	}

	public void setMoveLeft(boolean l) {
		moveLeft = l;
	}

	public void setMoveRight(boolean r) {
		moveRight = r;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> b) {
		bullets = b;
	}
	
	public double getHealth() {
		return health;
	}

	public void setHealth(double h) {
		health = h;
	}
	
	public void addHealth(double h)
	{
		health += h;
	}
	
	public double getMaxHealth() {
			return maxHealth;
	}
	
	public void addMaxHealth(double h)
	{
		if (maxHealth + h <= 12)
		{
			maxHealth += h;
		}
	}
	/*
	 * public boolean getFireUp() { return fireUp; } public boolean
	 * getFireDown() { return fireDown; } public boolean getFireLeft() { return
	 * fireLeft; } public boolean getFireRight() { return fireRight; }
	 * 
	 * public void setFireUp(boolean u) { fireUp = u; } public void
	 * setFireDown(boolean d) { fireDown = d; } public void setFireLeft(boolean
	 * l) { fireLeft = l; } public void setFireRight(boolean r) { fireRight = r;
	 * }
	 */

	public Image getImg() {
		return isaac[img];
	}

	public int getXFire() {
		return xFire;
	}

	public void setXFire(int xF) {
		xFire = xF;
	}

	public int getYFire() {
		return yFire;
	}

	public void setYFire(int yF) {
		yFire = yF;
	}

	public boolean isShooting() {
		return isShooting;
	}

	public void setShooting(boolean isShoot) {
		isShooting = isShoot;
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int m) {
		money += m;
	}
	
	public int getBombs() { return bombs; }
	public void addBomb(int x) { bombs += x; }

	public int getKeys() {
		return keys;
	}

	public void addKeys(int k) {
		keys += k;
	}
	
	public void addMaxVel(double d)
	{
		maxVel += d;
	}
	
	public double getMaxVel()
	{
		return maxVel;
	}

	
}
