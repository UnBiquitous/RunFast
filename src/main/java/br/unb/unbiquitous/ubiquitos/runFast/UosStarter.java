package br.unb.unbiquitous.ubiquitos.runFast;

import org.unbiquitous.uos.core.ContextException;
import org.unbiquitous.uos.core.UOSApplicationContext;

/**
 * main
 *
 */
public class UosStarter {
	/*public static void main (String args[]){
		StateManager stateManager = new StateManager();
		stateManager.begin();
	}*/
	public static void main(String[] args) throws ContextException {
		UOSApplicationContext uosApplicationContext = new UOSApplicationContext();
		uosApplicationContext.init();
	}
}
