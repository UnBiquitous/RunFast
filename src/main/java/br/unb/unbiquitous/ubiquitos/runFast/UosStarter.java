package br.unb.unbiquitous.ubiquitos.runFast;

import org.unbiquitous.uos.core.ContextException;
import org.unbiquitous.uos.core.UOS;

/**
 * Starts the uOS. The uOS initiates using the ubiquitous.properties file.
 *
 */
public class UosStarter {
	/*public static void main (String args[]){
		StateManager stateManager = new StateManager();
		stateManager.begin();
	}*/
	public static void main(String[] args) throws ContextException {
		UOS uosApplicationContext = new UOS();
		uosApplicationContext.init();
	}
}
