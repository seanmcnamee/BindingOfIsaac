package src;

import java.awt.Graphics;

public interface Moveable {
	
	public void draw(Graphics g);
	public void move(Room r);
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
}
