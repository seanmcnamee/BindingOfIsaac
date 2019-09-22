package app;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Graphics;

/**
 * Button
 */
public class Button {
    BufferedImage image;
    Point pos;

    public Button(BufferedImage img, int x, int y) {
        image = img;
        pos = new Point(x, y);
    }

    public Button(BufferedImage img, Point p) {
        image = img;
        pos = p;
    }

    public void render(Graphics g) {
        //g.drawImage(image, pos.getX(), pos.getY(), null);
        g.drawImage(image, (int)pos.getX(), (int)pos.getY(), null);
    }


    
}