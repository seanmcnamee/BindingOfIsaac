package app.supportclasses;

//import java.awt.event.ActionEvent;
//import java.awt.event.KeyAdapter;
//import java.awt.event.MouseWheelEvent;
//import java.beans.PropertyChangeListener;


import javax.swing.event.MouseInputAdapter;
import java.awt.event.KeyListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Input extends MouseInputAdapter implements KeyListener, ComponentListener {

    // Sends the input to the current screen in use
    GameValues gameValues;

    public Input(GameValues gameValues) {
        this.gameValues = gameValues;
    }

    public void mouseClicked(MouseEvent e) {
        gameValues.currentScreen.mouseClicked(e);
    }

    public void mouseEntered(MouseEvent e) {
        gameValues.currentScreen.mouseEntered(e);
    }

    public void mouseMoved(MouseEvent e) {
        gameValues.currentScreen.mouseMoved(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        gameValues.currentScreen.mouseWheelMoved(e);
    }

    public void keyPressed(KeyEvent e) {
        gameValues.currentScreen.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        gameValues.currentScreen.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {
        gameValues.currentScreen.keyTyped(e);
    }

    /**
     * Just set the SCALE to the proper fucking number
     */
    public void componentResized(ComponentEvent e) {
        double scaleX = e.getComponent().getSize().getWidth()/gameValues.WIDTH_SCALE_1;
        double scaleY = e.getComponent().getSize().getHeight()/gameValues.HEIGHT_SCALE_1;
        //System.out.println("X Scale: " + scaleX + ", Y Scale: " + scaleY);
        gameValues.gameScale = Math.min(scaleX, scaleY);
        
    }

    
    public void componentMoved(ComponentEvent e) {

    }

    
    public void componentShown(ComponentEvent e) {

    }

    //TODO possibly add pause game feature
    public void componentHidden(ComponentEvent e) {

    }


}
