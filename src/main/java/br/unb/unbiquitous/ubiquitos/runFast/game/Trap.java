package br.unb.unbiquitous.ubiquitos.runFast.game;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Traps released by the teams car.
 *
 */
public class Trap extends GameObject{

	//Traps paths
	private static final String GENERAL_PATH = "images/items/";
	public static final String GENERAL_TRAPS_PATH = GENERAL_PATH+"traps/";
	
	public static final String BOMB_PATH = GENERAL_TRAPS_PATH+"bomb.png";
	public static final String OIL_PATH = GENERAL_TRAPS_PATH+"oil.png";
	
	//Traps identifiers
	public static final int TRAP_TYPE_BOMB = 0;
	public static final int TRAP_TYPE_OIL = 1;
	
	
	private Image trapImage;
	
	private int trapType;
	
	
	private int available = 30;
	
	public Trap(int x, int y,int trapType) {
		super(x, y, 0, 0);
		
		this.trapType = trapType;
		initTrap();
		box.width = trapImage.getWidth(null);
		box.height = trapImage.getHeight(null);
	}
	
	/**
	 * Initiates based in the trap type.
	 */
	private void initTrap() {
		ImageIcon ii = new ImageIcon(getClass().getClassLoader().getResource(OIL_PATH));
		switch (trapType) {
			case TRAP_TYPE_OIL:
				ii = new ImageIcon(getClass().getClassLoader().getResource(OIL_PATH));
				break;
			case TRAP_TYPE_BOMB:
				ii = new ImageIcon(getClass().getClassLoader().getResource(BOMB_PATH));
				break;

			default:
				//ii = new ImageIcon(getClass().getClassLoader().getResource(BOMB_PATH));
				break;
		}
		trapImage = ii.getImage();
	}

	@Override
	public int update(int dt) {
		if(available>0)
			available--;
		return 0;
	}

	/**
	 * Renders the trap.
	 */
	@Override
	public void render(Graphics2D g, int cameraX, int cameraY, JPanel panel) {
		Graphics2D gTrap = (Graphics2D) g.create();
		gTrap.drawImage(trapImage, getX()+cameraX, getY()+cameraY, panel);
		gTrap.dispose();
	}
	
	/**
	 * @return if it is available.
	 */
	public boolean isAvailable(){
		if(available==0)
			return true;
		return false;
	}

}
