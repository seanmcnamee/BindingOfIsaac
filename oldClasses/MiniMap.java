package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MiniMap
{
	Boolean[][] minMap;
	int x;
	int y;
	int wi;
	int he;
	Image[] pic;
	public MiniMap(Room[][] m, int r, int c, int w, int h)
	{
		pic = new Image[3];
		try {
			pic[0] = ImageIO.read(new File("Isaac_coin.jpg"));
		} catch (IOException e) {
		}
		try {
			pic[1] = ImageIO.read(new File("Isaac_treasure.png"));
		} catch (IOException e) {
		}
		try {
			pic[2] = ImageIO.read(new File("Isaac_skull.jpg"));
		} catch (IOException e) {
		}
		minMap = new Boolean[m.length][m[0].length];
		for(int i = 0; i < minMap.length; i++)
		{
			for(int j = 0; j < minMap[i].length; j++)
			{
				if(m[i][j]!= null)
				{
					minMap[i][j] = true;					
				}
			}
		}
		x = r;
		y = c;
		wi = w;
		he = h;
	}
	public void draw(Graphics g, Room[][] m)
	{
		g.setColor(Color.black);
		for(int i = 0; i< minMap.length; i++)
		{
			for(int j = 0; j<minMap[i].length;j++)
			{
				if(minMap[i][j] != null && minMap[i][j])
				{
					if(i == x && j== y)
						//g.drawRect(wi - (j*24) - 10, 10 + (i*24), 24, 24);
						//g.drawRect((wi-(minMap[0].length*24)-10)+(j*24),10+(i*24), 24, 24);
						g.fillRect((wi-(minMap[0].length*22)-10)+(j*22),10+(i*22), 20, 20);
					else
						//g.drawRect(wi - (j*24)-10, 10 + (i*24), 16, 16);
						g.drawRect((wi-(minMap[0].length*22)-10)+(j*22),10+(i*22), 20, 20);
						if (m[i][j] != null && m[i][j].getType().equals("TreasureRoom"))
						{
							g.drawImage(pic[1], (wi-(minMap[0].length*22)-10)+(j*22)+2,10+(i*22)+2, 16, 16, null);
						}else if(m[i][j] != null && m[i][j].getType().equals("ShopRoom"))
						{
							g.drawImage(pic[0], (wi-(minMap[0].length*22)-10)+(j*22)+2,10+(i*22)+2, 16, 16, null);
						}else if(m[i][j] != null && m[i][j].getType().equals("BossRoom"))
						{
							g.drawImage(pic[2], (wi-(minMap[0].length*22)-10)+(j*22)+2,10+(i*22)+2, 16, 16, null);
						}
				}
			}
		}
	}
}
