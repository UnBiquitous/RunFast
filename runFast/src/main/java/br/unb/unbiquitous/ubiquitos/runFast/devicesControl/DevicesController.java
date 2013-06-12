package br.unb.unbiquitous.ubiquitos.runFast.devicesControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.NotifyException;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;
import org.unbiquitous.uos.core.adaptabitilyEngine.UosEventListener;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;
import org.unbiquitous.uos.core.messageEngine.messages.Notify;

import br.unb.unbiquitous.ubiquitos.runFast.game.Team;
import br.unb.unbiquitous.ubiquitos.runFast.inputs.InputManager;

public class DevicesController implements UosEventListener{

	private static final String RF_DRIVER = "br.unb.unbiquitous.ubiquitos.runFast.mid.RFInputDriver";
	
	private static final String ENTER_EVENT = "RFEnterEvent";
	
	private Gateway gateway;
	
	private List<Team> teams;
	private List<UpDevice> gameDevices, allDevices, waitDevices;
	
	/**
	 * Cheat Constructor: gets the gateway
	 * and starts teams and devices with the keysListeners.
	 * */
	public DevicesController(Gateway gateway, boolean cheats) {
		this.gateway = gateway;

		teams = new ArrayList<Team>();
		gameDevices = new ArrayList<UpDevice>();
		allDevices = new ArrayList<UpDevice>();
		waitDevices = new ArrayList<UpDevice>();
		
		gameDevices.add(InputManager.DEVICE_1);
		gameDevices.add(InputManager.DEVICE_2);
		gameDevices.add(InputManager.DEVICE_3);
		
		teams.add(new Team(InputManager.DEVICE_1));
		teams.get(0).setCopilot(InputManager.DEVICE_2);
		teams.add(new Team(InputManager.DEVICE_3));
	}
	
	/**
	 * Constructor: gets the gateway and starts teams and devices.
	 * */
	public DevicesController(Gateway gateway) {
		this.gateway = gateway;
		
		teams = new ArrayList<Team>();
		gameDevices = new ArrayList<UpDevice>();
	}
	
	private List<DevicesListener> listeners = new ArrayList<DevicesListener>();
	
	public synchronized void addInputListener(DevicesListener listener)  {
		listeners.add(listener);
	}
	public synchronized void removeInputListener(DevicesListener listener)   {
		listeners.remove(listener);
	}
	 
	//Call this method to notify some inputListeners of the particular event
	private synchronized void fireDeviceEntered(UpDevice device) {
		DevicesEvent event = new DevicesEvent(this, device);
		Iterator<DevicesListener> i = listeners.iterator();
		while(i.hasNext())  {
			((DevicesListener) i.next()).deviceEntered(event);
		}
	}

	//Call this method to notify some inputListeners of the particular event
	private synchronized void fireDeviceGotOut(UpDevice device) {
		DevicesEvent event = new DevicesEvent(this, device);
		Iterator<DevicesListener> i = listeners.iterator();
		while(i.hasNext())  {
			((DevicesListener) i.next()).deviceGotOut(event);
		}
	}
	
	public void update(int dt){
		//List<UpDevice> newDevices = verifyNewDevices();
		//inviteNewDevices(newDevices);
		
		List<DriverData> devices = gateway.listDrivers(RF_DRIVER);
		
		if(devices==null)
			return;
			
		if(devices.size() != gameDevices.size()) {
			System.out.println("DevicesController devices.size() "+devices.size());
			boolean isNewDevice = false;
			for(int i = 0; i<devices.size(); ++i){
				isNewDevice = true;
				for(int j = 0; j<gameDevices.size(); ++j){
					if(devices.get(i).getDevice().getName().equals(gameDevices.get(j).getName()))
						isNewDevice = false;
				}
				if(isNewDevice){
					InputManager.GetInstance().registerDriver(gateway,devices.get(i).getDevice());
					
					gameDevices.add(devices.get(i).getDevice());
					teams.add(new Team(devices.get(i).getDevice()));
					
					fireDeviceEntered(devices.get(i).getDevice());
				}
			}
		}
	}
	
	private List<UpDevice> verifyNewDevices(){

		List<UpDevice> newDevices = new ArrayList<UpDevice>();
		List<DriverData> devices = gateway.listDrivers(RF_DRIVER);
		
		if(devices==null)
			return newDevices;
			
		if(devices.size() > allDevices.size()) {
			System.out.println("DevicesController devices.size() "+devices.size());
			boolean isNewDevice = false;
			for(int i = 0; i<devices.size(); ++i){
				isNewDevice = true;
				for(int j = 0; j<allDevices.size(); ++j){
					if(devices.get(i).getDevice().getName().equals(allDevices.get(j).getName()))
						isNewDevice = false;
				}
				if(isNewDevice){
					allDevices.add(devices.get(i).getDevice());
					newDevices.add(devices.get(i).getDevice());
				}
			}
		}
		
		return newDevices;
	}
	
	private void inviteNewDevices(List<UpDevice> devices){
		if(devices.size()==0)
			return;
		
		for(int i = 0; i<devices.size(); ++i){
			registerDriver(devices.get(i));
			waitDevices.add(devices.get(i));
			
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("inviteOrSolicitation", ""+false);
				map.put("deviceName", gateway.getCurrentDevice().getName());
				gateway.callService(gateway.getCurrentDevice(), "gameInviteSolicitation",RF_DRIVER, 
						null, null, map);
			} catch (ServiceCallException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initNewGameDevice(UpDevice device){
		InputManager.GetInstance().registerDriver(gateway,device);
		
		gameDevices.add(device);
		teams.add(new Team(device));
		
		fireDeviceEntered(device);
	}
	
	private void registerDriver(UpDevice device){
		try {
			System.out.println("DevicesController registerDriver "+gateway.listDrivers(RF_DRIVER).size());
			gateway.registerForEvent(this, device, RF_DRIVER, null, ENTER_EVENT);
		} catch (NotifyException e) {
			e.printStackTrace();
		}
	}
	
	public void handleEvent(Notify event) {
		if(event.getEventKey() != ENTER_EVENT)
			return;
		
		
	}
	
	
	/**
	 * @return the gateway
	 */
	public Gateway getGateway() {
		return gateway;
	}
	/**
	 * @param gateway the gateway to set
	 */
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	/**
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}
	/**
	 * @return the devices
	 */
	public List<UpDevice> getDevices() {
		return gameDevices;
	}

}
