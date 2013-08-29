package br.unb.unbiquitous.ubiquitos.runFast.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * Abstracts the game objects, providing abstract methods to make
 * the update and rendering of the object and also bounds treatment. 
 *
 */
public abstract class GameObject {
	
	protected Rectangle box;
	
	public GameObject(int x, int y, int w, int h) {
		box = new Rectangle(x,y,w,h);
	}
    
	//Abstract methods
	public abstract int update(int dt);
    public abstract void render(Graphics2D g,int cameraX,int cameraY,JPanel panel);
    
    /**
     * Verifies if collides with other GameObject
     * @param other
     * @return true if collides with the other GameObject
     * and false otherwise.
     */
    public boolean collidesWith(GameObject other) {
    	return box.intersects(other.getBounds());
    }
    
    /**
     * @return the x
     */
    public int getX() {
    	return box.x;
    }
    /**
     * @return the y
     */
    public int getY() {
    	return box.y;
    }
    /**
     * @return the width
     */
    public int getWidth() {
    	return box.width;
    }
    /**
     * @return the height
     */
    public int getHeight() {
    	return box.height;
    }
    /**
     * @return the GameObject rectangle bounds
     */
    public Rectangle getBounds() {
        return box;
    }
	
}
