package br.unb.unbiquitous.ubiquitos.runFast.states;

import javax.swing.JPanel;

import br.unb.unbiquitous.ubiquitos.runFast.devicesControl.DevicesController;
import br.unb.unbiquitous.ubiquitos.runFast.devicesControl.DevicesListener;

/**
 * Abstract class used by the game to implement each state and their actions.
 *
 */
public abstract class State extends JPanel implements DevicesListener{

	/**
	 * serialVersionUID = -257465401526364067L;
	 */
	private static final long serialVersionUID = -257465401526364067L;
	
	protected DevicesController devicesController;
	
	/**
	 * Loads the state contents.
	 * @param devController
	 */
	public void load(DevicesController devController) {
		devicesController = devController;
		devController.addDevicesListener(this);
	}
	
	/**
	 * Loads the state contents.
	 * @param devController
	 * @param stack
	 */
	public void load(DevicesController devController, Stack stack) {
		load(devController);
	}
	
	/**
	 * Unloads the state contents.
	 * @return
	 */
	public Stack unload() {
		devicesController.removeDevicesListener(this);
		return null;
	}
	
	/**
	 * Updates the state, must be called every frame.
	 * @param dt
	 * @return
	 */
	public abstract int update(int dt);
	
	/**
	 * Renders the state frame.
	 */
	public abstract void render();
	
	/**
	 * Finishes the game.
	 */
	public void endGame(){
		devicesController.endGame();
	}
}
