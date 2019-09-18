package src;

public class Point {

	private int row;
	private int col;
	
	public Point()
	{
		row = 0;
		col = 0;
	}
	
	public Point(int a, int b)
	{
		row = a;
		col = b;
	}
	
	public int getRow(){	return row; }
	public int getCol(){ return col; }
	
	public String toString()
	{
		return ("(" + row + ", " + col + ")");
	}
}
