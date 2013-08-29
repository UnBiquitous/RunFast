package br.unb.unbiquitous.ubiquitos.runFast.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.NotifyException;
import org.unbiquitous.uos.core.adaptabitilyEngine.UosEventListener;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;
import org.unbiquitous.uos.core.messageEngine.messages.Notify;

import br.unb.unbiquitous.ubiquitos.runFast.ui.Window;

/**
 * Class which receives the inputs, then treats them and passes the corresponding
 * game input event to the game listeners.
 *
 */
public class InputManager implements UosEventListener, KeyListener{

	//Used to use the keyboard
	public static final UpDevice DEVICE_1 = new UpDevice("1");
	public static final UpDevice DEVICE_2 = new UpDevice("2");
	public static final UpDevice DEVICE_3 = new UpDevice("3");
	//private static final int DEVICE_4 = 4;
	
	//Mobile InputDriver constants
	private static final String RF_DRIVER = "br.unb.unbiquitous.ubiquitos.runFast.mid.RFInputDriver";
	private static final String RF_INPUT_EVENT = "RFInputEvent";
	
	//Notifiers constants
	private static final String NOTIFY_PERFORMED = "isPerformed";
	private static final String NOTIFY_INPUT_CODE = "inputCode";
	private static final String NOTIFY_DEVNAME = "deviceName";
	private static final String NOTIFY_INTENSITY = "intensity";
	
	private Gateway gateway;
	
	private int inputCode = InputEvent.IC_ENTER;
	private double intensity = 0.0;
	
	private static InputManager instance = null;
	
	//List of listeners
	private List<InputListener> listeners = new ArrayList<InputListener>();
	
	public static InputManager GetInstance() {
		if (instance == null) {
			instance = new InputManager();
			//NEDEED BECAUSE OF THE KEYLISTENER!!!!
			Window.GetInstance().addKeyListener(instance);
		}
		return instance;
	}
	
	private InputManager(){
		gateway = null;
	}

	/**
	 * Adds an input listener.
	 * @param listener
	 */
	public synchronized void addInputListener(InputListener listener)  {
		listeners.add(listener);
	}
	/**
	 * Removes an input listener.
	 * @param listener
	 */
	public synchronized void removeInputListener(InputListener listener)   {
		listeners.remove(listener);
	}
	 
	/**
	 * Notifies the InputListeners that an input was performed.
	 * @param device
	 */
	private synchronized void fireInputPerformed(UpDevice device) {
		InputEvent event = new InputEvent(this, inputCode, device, intensity);
		Iterator<InputListener> i = listeners.iterator();
		while(i.hasNext())  {
			((InputListener) i.next()).inputPerformed(event);
		}
	}

	/**
	 * Notifies the InputListeners that an input was released.
	 * @param device
	 */
	private synchronized void fireInputReleased(UpDevice device) {
		InputEvent event = new InputEvent(this, inputCode, device, intensity);
		Iterator<InputListener> i = listeners.iterator();
		while(i.hasNext())  {
			((InputListener) i.next()).inputReleased(event);
		}
	}
	
	/*
	 * Specific to the keyEvents!
	 * Takes an keyEvent and turns it into some useful inputEvent
	 * */
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()){
			case KeyEvent.VK_ENTER:
				inputCode = InputEvent.IC_ENTER;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_UP:
				inputCode = InputEvent.IC_UP;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_DOWN:
				inputCode = InputEvent.IC_DOWN;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_LEFT:
				inputCode = InputEvent.IC_LEFT;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_RIGHT:
				inputCode = InputEvent.IC_RIGHT;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_Q:
				inputCode = InputEvent.IC_PLUS_LEFT;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_E:
				inputCode = InputEvent.IC_PLUS_RIGHT;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_F:
				inputCode = InputEvent.IC_PLUS_SELECT;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_Z:
				inputCode = InputEvent.IC_ACTION;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_V:
				inputCode = InputEvent.IC_ACTION2;
				fireInputPerformed(DEVICE_1);
				break;
			case KeyEvent.VK_W:
				inputCode = InputEvent.IC_UP;
				fireInputPerformed(DEVICE_2);
				break;
			case KeyEvent.VK_S:
				inputCode = InputEvent.IC_DOWN;
				fireInputPerformed(DEVICE_2);
				break;
			case KeyEvent.VK_A:
				inputCode = InputEvent.IC_LEFT;
				fireInputPerformed(DEVICE_2);
				break;
			case KeyEvent.VK_D:
				inputCode = InputEvent.IC_RIGHT;
				fireInputPerformed(DEVICE_2);
				break;
			case KeyEvent.VK_X:
				inputCode = InputEvent.IC_ACTION;
				fireInputPerformed(DEVICE_2);
				break;
			case KeyEvent.VK_C:
				inputCode = InputEvent.IC_ACTION2;
				fireInputPerformed(DEVICE_2);
				break;
			case KeyEvent.VK_U:
				inputCode = InputEvent.IC_UP;
				fireInputPerformed(DEVICE_3);
				break;
			case KeyEvent.VK_J:
				inputCode = InputEvent.IC_DOWN;
				fireInputPerformed(DEVICE_3);
				break;
			case KeyEvent.VK_H:
				inputCode = InputEvent.IC_LEFT;
				fireInputPerformed(DEVICE_3);
				break;
			case KeyEvent.VK_K:
				inputCode = InputEvent.IC_RIGHT;
				fireInputPerformed(DEVICE_3);
				break;
			case KeyEvent.VK_B:
				inputCode = InputEvent.IC_ACTION;
				fireInputPerformed(DEVICE_3);
				break;
			default:
				break;
		}
		//Toolkit.getDefaultToolkit().beep();
	}

	
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_ENTER:
				inputCode = InputEvent.IC_ENTER;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_UP:
				inputCode = InputEvent.IC_UP;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_DOWN:
				inputCode = InputEvent.IC_DOWN;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_LEFT:
				inputCode = InputEvent.IC_LEFT;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_RIGHT:
				inputCode = InputEvent.IC_RIGHT;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_Q:
				inputCode = InputEvent.IC_PLUS_LEFT;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_E:
				inputCode = InputEvent.IC_PLUS_RIGHT;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_F:
				inputCode = InputEvent.IC_PLUS_SELECT;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_Z:
				inputCode = InputEvent.IC_ACTION;
				fireInputReleased(DEVICE_1);
				break;
			case KeyEvent.VK_W:
				inputCode = InputEvent.IC_UP;
				fireInputReleased(DEVICE_2);
				break;
			case KeyEvent.VK_S:
				inputCode = InputEvent.IC_DOWN;
				fireInputReleased(DEVICE_2);
				break;
			case KeyEvent.VK_A:
				inputCode = InputEvent.IC_LEFT;
				fireInputReleased(DEVICE_2);
				break;
			case KeyEvent.VK_D:
				inputCode = InputEvent.IC_RIGHT;
				fireInputReleased(DEVICE_2);
				break;
			case KeyEvent.VK_X:
				inputCode = InputEvent.IC_ACTION;
				fireInputReleased(DEVICE_2);
				break;
			case KeyEvent.VK_U:
				inputCode = InputEvent.IC_UP;
				fireInputReleased(DEVICE_3);
				break;
			case KeyEvent.VK_J:
				inputCode = InputEvent.IC_DOWN;
				fireInputReleased(DEVICE_3);
				break;
			case KeyEvent.VK_H:
				inputCode = InputEvent.IC_LEFT;
				fireInputReleased(DEVICE_3);
				break;
			case KeyEvent.VK_K:
				inputCode = InputEvent.IC_RIGHT;
				fireInputReleased(DEVICE_3);
				break;
			case KeyEvent.VK_B:
				inputCode = InputEvent.IC_ACTION;
				fireInputReleased(DEVICE_3);
				break;
			default:
				break;
		}
	}

	public void keyTyped(KeyEvent e) {}

	/**
	 * Registers itself in a device InputDriver to receive its events.
	 * @param gateway
	 * @param device
	 */
	public void registerDriver(Gateway gateway,UpDevice device){
		if(this.gateway == null)
			this.gateway = gateway;
		
		try {
			System.out.println("InputManager registerDriver - listDrivers.size() "+gateway.listDrivers(RF_DRIVER).size());
			gateway.registerForEvent(this, device, RF_DRIVER, null, RF_INPUT_EVENT);
		} catch (NotifyException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the events received by the InputDrivers registered in.
	 * Adapting the needed ones and notifying it to the InputListeners.
	 */
	public void handleEvent(Notify notify) {//ADDED CASTERS
		//System.out.println("RUNFAST!" +
		//		"\n handleEvent: NOTIFY_INPUT_CODE  "+notify.getParameter(NOTIFY_INPUT_CODE)+
		//		"\n handleEvent: NOTIFY_DEVNAME  "+notify.getParameter(NOTIFY_DEVNAME)+
		//		"\n handleEvent: NOTIFY_DEVNAME  "+notify.getParameter(NOTIFY_PERFORMED));
		
		inputCode = Integer.parseInt((String) notify.getParameter(NOTIFY_INPUT_CODE));
		if((inputCode&InputEvent.IC_WITH_INTENSITY)!=0)
			intensity = Double.parseDouble((String) notify.getParameter(NOTIFY_INTENSITY));
		else
			intensity = 0.0;
		String deviceName = (String) notify.getParameter(NOTIFY_DEVNAME);
		
		UpDevice device = null;
		for(int i = 0; i<gateway.listDrivers(RF_DRIVER).size() ;++i){
			if(gateway.listDrivers(RF_DRIVER).get(i).getDevice().getName().equals(deviceName))
				device = gateway.listDrivers(RF_DRIVER).get(i).getDevice();
		}
		
		if(device != null){
			if(Boolean.parseBoolean((String) notify.getParameter(NOTIFY_PERFORMED)))
				fireInputPerformed(device);
			else
				fireInputReleased(device);
		}else{
			System.out.println("handleEvent: device == null");
		}
	}
}
