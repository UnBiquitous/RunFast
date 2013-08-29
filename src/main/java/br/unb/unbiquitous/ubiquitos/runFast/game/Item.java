package br.unb.unbiquitous.ubiquitos.runFast.game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;

import br.unb.unbiquitous.ubiquitos.runFast.devicesControl.DevicesController;

/**
 * Items that appears randomly in the map and can be collected.
 *
 */
public class Item extends GameObject{

	//Items paths
	private static final String GENERAL_PATH = "images/items/";

	private static final String BOX_PATH = GENERAL_PATH+"box.png";
	private static final String BONUS_PATH = GENERAL_PATH+"bonus.png";
	private static final String BREAK_PATH = GENERAL_PATH+"break.png";
	private static final String UNEQUIP_PATH = GENERAL_PATH+"unequip.png";
	private static final String TRAP_PATH = GENERAL_PATH+"oil.png";
	
	//Types identifiers
	private static final int NUMBER_OF_ITEM_TYPES = 4;
	private static final int ITEM_TYPE_TRAP = 0;
	private static final int ITEM_TYPE_BONUS = 1;
	private static final int ITEM_TYPE_BREAK = 2;
	private static final int ITEM_TYPE_UNEQUIP = 3;
	
	private Image boxImage, itemImage;
	
	private int itemType;
	
	public Item(int x, int y) {
		super(x, y, 0, 0);
		
		ImageIcon ii = new ImageIcon(getClass().getClassLoader().getResource(BOX_PATH));
		boxImage = ii.getImage();
		box.width = boxImage.getWidth(null);
		box.height = boxImage.getHeight(null);
		
		initItem();
	}
	
	/**
	 * Generates an item type and initiates it based in this type.
	 */
	private void initItem() {
		
		Random gerador = new Random();
		 
        itemType = Math.abs((gerador.nextInt()%NUMBER_OF_ITEM_TYPES));
        System.out.print(itemType);
		ImageIcon ii = null;
		//itemType = 2;
		switch(itemType) {
			case ITEM_TYPE_TRAP:
				ii = new ImageIcon(getClass().getClassLoader().getResource(TRAP_PATH));
				break;
			case ITEM_TYPE_BONUS:
				ii = new ImageIcon(getClass().getClassLoader().getResource(BONUS_PATH));
				break;
			case ITEM_TYPE_BREAK:
				ii = new ImageIcon(getClass().getClassLoader().getResource(BREAK_PATH));
				break;
			case ITEM_TYPE_UNEQUIP:
				ii = new ImageIcon(getClass().getClassLoader().getResource(UNEQUIP_PATH));
				break;
			default:
				//ii = new ImageIcon(getClass().getClassLoader().getResource(OIL_PATH));
				break;
		}
		itemImage = ii.getImage();
	}

	@Override
	public int update(int dt) {
		return 0;
	}

	/**
	 * Renders the item.
	 */
	@Override
	public void render(Graphics2D g, int cameraX, int cameraY, JPanel panel) {
		Graphics2D gItem = (Graphics2D) g.create();
		gItem.drawImage(boxImage, getX()+cameraX, getY()+cameraY, panel);
		gItem.dispose();
	}
	
	/**
	 * Activates the item.
	 * @param car
	 * @return
	 */
	public boolean activate(Car car) {
		boolean needSelection = false;
		
		switch (itemType) {
			case ITEM_TYPE_TRAP:
				Map.getInstance().addTrap(new Trap(car.getX(),car.getY(),Trap.TRAP_TYPE_OIL));
				break;
			case ITEM_TYPE_BONUS:
				Gateway gateway = Map.getInstance().getDevicesController().getGateway();
				List<Team> teams = Map.getInstance().getDevicesController().getTeams();
				boolean found =false;
				int option = 0;
				for(int i=0; (i<teams.size())&&(!found); ++i){
					if(teams.get(i).getCar()==car){
						found = true;
						option = i;
					}
				}
				
				try {
					java.util.Map<String, Object> map = new HashMap<String, Object>();
					map.put("deviceName", gateway.getCurrentDevice().getName());
					Map.getInstance().getDevicesController();
					
					//Filters the devices that will receive the minigame, once it changes based in the team composition
					List<UpDevice> devices = new ArrayList<UpDevice>();
					if(Map.getInstance().getTeam(option).getNumberOfPlayers()>2){
						if(Map.getInstance().getTeam(option).getAssistants().size()>0)
							devices = Map.getInstance().getTeam(option).getAssistants();
					}else if(Map.getInstance().getTeam(option).getNumberOfPlayers()>1){
						if(Map.getInstance().getTeam(option).getAssistants().size()>0)
							devices = Map.getInstance().getTeam(option).getAssistants();
						if(Map.getInstance().getTeam(option).getCopilot()!=null)
							devices.add(Map.getInstance().getTeam(option).getCopilot());
					}else{
						if(Map.getInstance().getTeam(option).getPilot()!=null)
							devices.add(Map.getInstance().getTeam(option).getPilot());
					}
					//Starts the bonus minigame in all the filtered devices.
					for(int k=0; k<devices.size(); ++k)
						gateway.callService(devices.get(k), "beginMGBonus",
								DevicesController.RF_INPUT_DRIVER, null, null, map);
				} catch (ServiceCallException e) {
					e.printStackTrace();
				}
				//needSelection = true;
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
	
	/**
	 * If it is an item which needs a target it is reactivated.
	 * @param option
	 */
	public void reactivate(int option) {
		Gateway gateway = Map.getInstance().getDevicesController().getGateway();
		
		switch (itemType) {
			/*case ITEM_TYPE_BONUS:
				///*
				try {
					java.util.Map<String, Object> map = new HashMap<String, Object>();
					map.put("deviceName", gateway.getCurrentDevice().getName());
					Map.getInstance().getDevicesController();
					gateway.callService(Map.getInstance().getTeam(option).getAssistants().get(0),
							"beginMGBonus", DevicesController.RF_INPUT_DRIVER, null, null, map);
				} catch (ServiceCallException e) {
					e.printStackTrace();
				}
				///*
				try {
					File fileJar = new File(getClass().getClassLoader().getResource("minis/bonusminigame.jar").toURI());
					ClassToolbox box = new ClassToolbox();
                    ClassLoader loader = box.load(new FileInputStream(fileJar));
                   
                    Class<?> clazz = loader.loadClass("br.unbiquitous.ubiquitos.runFast.minigames.bonusmg.ItemAgent");
                    Serializable agent = (Serializable)clazz.newInstance();

                    File file;
					file = new File(getClass().getClassLoader().getResource("minis/bonusminigame.apk").toURI());
					System.out.println("minis/bonusminigame.apk is "+file.exists());
                    //ItemAgent agent = new ItemAgent();
					//agent.init(Map.getInstance().getDevicesController().getGateway());
					AgentUtil.getInstance().move(agent, file, Map.getInstance().getTeam(option).getAssistants().get(0),
							Map.getInstance().getDevicesController().getGateway());
					System.out.println("ENVIOUUU!!!!!!!!!!!!!");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;*/
			case ITEM_TYPE_BREAK:
				try {
					java.util.Map<String, Object> map = new HashMap<String, Object>();
					map.put("deviceName", gateway.getCurrentDevice().getName());
					map.put("helpNumber", ""+Map.getInstance().getTeam(option).getNumberOfPlayers());
					
					//Filters the devices that will receive the minigame, once it changes based in the team composition
					List<UpDevice> devices = new ArrayList<UpDevice>();
					
					Team team = Map.getInstance().getTeam(option);
					if(Map.getInstance().getTeam(option).getNumberOfPlayers()>1){
						if(team.getAssistants().size()>0)
							devices = team.getAssistants();
						if(team.getCopilot()!=null)
							devices.add(team.getCopilot());
					}else if(team.getNumberOfPlayers()==1){
						if(team.getPilot()!=null)
							devices.add(team.getPilot());
						if(team.getCopilot()!=null)
							devices.add(team.getCopilot());
						if(team.getAssistants().size()>0)
							devices.add(team.getAssistants().get(0));
					}
					//Starts the break minigame in all the filtered devices
					for(int k=0; k<devices.size(); ++k)
						gateway.callService(devices.get(k), "beginMGBreak",
								DevicesController.RF_INPUT_DRIVER, null, null, map);
					
					Map.getInstance().getTeam(option).blockTeam(devices.size());
				} catch (ServiceCallException e) {
					e.printStackTrace();
				}
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
