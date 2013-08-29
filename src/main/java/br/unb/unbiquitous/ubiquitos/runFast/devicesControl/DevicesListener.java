package br.unb.unbiquitous.ubiquitos.runFast.devicesControl;

/**
 * Interface used to listen to the devices events (getting in and or out of the game).
 *
 */
public interface DevicesListener {
	
	//Called when a new device enter the game
	public void deviceEntered(DevicesEvent e);
	//Called when a device gets out of the game
	public void deviceGotOut(DevicesEvent e);
	
}
