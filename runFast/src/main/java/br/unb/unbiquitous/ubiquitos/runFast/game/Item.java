package br.unb.unbiquitous.ubiquitos.runFast.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Item extends GameObject{

	private static final String GENERAL_PATH = "../images/items/";
	
	private static final String BOX_PATH = GENERAL_PATH+"box.png";
	private static final String BONUS_PATH = GENERAL_PATH+"bonus.png";
	private static final String BREAK_PATH = GENERAL_PATH+"break.png";
	private static final String UNEQUIP_PATH = GENERAL_PATH+"unequip.png";
	
	private static final int NUMBER_OF_ITEM_TYPES = 4;
	private static final int ITEM_TYPE_TRAP = 0;
	private static final int ITEM_TYPE_BONUS = 1;
	private static final int ITEM_TYPE_BREAK = 2;
	private static final int ITEM_TYPE_UNEQUIP = 3;
	
	private Image boxImage, itemImage;
	
	private int itemType;
	
	public Item(int x, int y) {
		super(x, y, 0, 0);
		
		ImageIcon ii = new ImageIcon(getClass().getResource(BOX_PATH));
		boxImage = ii.getImage();
		box.width = boxImage.getWidth(null);
		box.height = boxImage.getHeight(null);
		
		initItem();
	}
	
	private void initItem() {
		
		Random gerador = new Random();
		 
        itemType = Math.abs((gerador.nextInt()%NUMBER_OF_ITEM_TYPES));
        System.out.print(itemType);
		ImageIcon ii = null;
		switch(itemType) {
			case ITEM_TYPE_TRAP:
				ii = new ImageIcon(getClass().getResource(Trap.OIL_PATH));
				break;
			case ITEM_TYPE_BONUS:
				ii = new ImageIcon(getClass().getResource(BONUS_PATH));
				break;
			case ITEM_TYPE_BREAK:
				ii = new ImageIcon(getClass().getResource(BREAK_PATH));
				break;
			case ITEM_TYPE_UNEQUIP:
				ii = new ImageIcon(getClass().getResource(UNEQUIP_PATH));
				break;
			default:
				//ii = new ImageIcon(getClass().getResource(OIL_PATH));
				break;
		}
		itemImage = ii.getImage();
	}

	@Override
	public int update(int dt) {
		return 0;
	}

	@Override
	public void render(Graphics2D g, int cameraX, int cameraY, JPanel panel) {
		Graphics2D gItem = (Graphics2D) g.create();
		gItem.drawImage(boxImage, getX()+cameraX, getY()+cameraY, panel);
		gItem.dispose();
	}
	
	public boolean activate(Car car) {
		boolean needSelection = false;
		
		switch (itemType) {
			case ITEM_TYPE_TRAP:
				Map.getInstance().addTrap(new Trap(car.getX(),car.getY(),Trap.TRAP_TYPE_OIL));
				break;
			case ITEM_TYPE_BONUS:
				break;
			case ITEM_TYPE_BREAK:
				needSelection = true;
				break;
			case ITEM_TYPE_UNEQUIP:
				needSelection = true;
				break;

			default:
				break;
		}
		
		return needSelection;
	}
	
	public void reactivate(int option) {
		
		switch (itemType) {
			case ITEM_TYPE_BONUS:
				break;
			case ITEM_TYPE_BREAK:
				break;
			case ITEM_TYPE_UNEQUIP:
				Map.getInstance().getTeam(option).getCar().unEquipAll();
				break;

			default:
				break;
		}
		
	}
	
	/**
	 * @return the itemImage
	 */
	public Image getItemImage() {
		return itemImage;
	}

}
