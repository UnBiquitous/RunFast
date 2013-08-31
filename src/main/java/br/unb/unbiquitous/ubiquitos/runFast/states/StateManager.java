package br.unb.unbiquitous.ubiquitos.runFast.states;

import java.util.HashMap;
import java.util.Map;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.ServiceCallException;

import br.unb.unbiquitous.ubiquitos.runFast.devicesControl.DevicesController;
import br.unb.unbiquitous.ubiquitos.runFast.devicesControl.RFDevicesDriver;
import br.unb.unbiquitous.ubiquitos.runFast.game.SoundBackground;
import br.unb.unbiquitous.ubiquitos.runFast.ui.Window;

/**
 * Class responsible to manage run the main game loop and to manage
 * the game states.
 *
 */
public class StateManager implements Runnable{

	//States constants identifiers 
	public static final int SAME_STATE      = 0;
	public static final int STATE_SELECTION = 1;
	public static final int STATE_MENU      = 2;
	public static final int STATE_GAME      = 3;
	public static final int STATE_WIN       = 4;
	public static final int STATE_LOSE      = 5;
	public static final int STATE_QUIT      = 6;
	
	//Frame rate
	private static final int DELAY = 33;
	
	//Current state
	private State estadoAtual;
	private	Stack stack;
	
	//Thread where the game runs
	private Thread mainThread;
	
	//Class responsible to play the sounds
	private SoundBackground soundBack;
	
	//Game devices controller
	private DevicesController devicesController;
	
	//Gateway, used to communicate with the uOS
	private Gateway gateway;
	
	/**
	 * Initiates the StateManager and its components.
	 * @param gateway
	 */
	public StateManager(Gateway gateway){
		this.gateway = gateway;
		//devicesController = new DevicesController(gateway);
		devicesController = new DevicesController(gateway,true);
		devicesController.startDevicesController();

		estadoAtual = new StateMenu();
		estadoAtual.load(devicesController);
		Window.GetInstance().change(estadoAtual);

		stack = new Stack();
		
		soundBack = new SoundBackground();
	}
	
	/**
	 * Starts the main game loop.
	 */
	public void begin(){
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	/**
	 * Execute the main game loop.
	 */
	public void run(){
		//Starts the sound background to be suits with the StateMenu
		soundBack.changeState(STATE_MENU);

		//Variables used in the main game loop
		int frameStart, dt, sleep;
		boolean quit = false;
		dt= DELAY;
		
		//while the game is running, and the players do not choose to exit
        while (!quit) {

        	/*Frame Start*/
        	frameStart = (int)System.currentTimeMillis();

        	/*RUN*/
        	//InputManager::getInstance()->Update();
        	//InputManager.GetInstance().registerDriver(gateway);
        	devicesController.update(dt);

        	//updates the current state and if it returns another state id it changes the current state
    	    switch(estadoAtual.update(dt))
    	    {
    	    	case SAME_STATE:
    	    		break;
    	    	case STATE_SELECTION:
    	    		changeState(new StateSelection(), STATE_SELECTION);
    	    		break;
    	    	case STATE_MENU:
    	    		changeState(new StateMenu(), STATE_MENU);
    	    		break;
    	    	case STATE_GAME:
    	    		changeState(new StateGame(), STATE_GAME);
    	    		break;
    	    	case STATE_WIN:
    	    		changeState(new StateWin(), STATE_WIN);
    	    		break;
    	    	case STATE_QUIT:
    	    		quit = true;
    	    		break;
    	    	default:
    	    		break;
    	    }
    	    
    	    /* Render */
    	    estadoAtual.render();

        	
        	/*Delay*/
    	    //Calculate the frame rate
            dt = (int)System.currentTimeMillis() - frameStart;
            sleep = DELAY - dt;

            if (sleep < 0)
                sleep = 2;
            if(dt<DELAY){
            	try {
            		Thread.sleep(sleep);
            	} catch (InterruptedException e) {
            		System.out.println("interrupted");
            	}
            	dt = DELAY;
            }
        }
        
        //game end procedure
        devicesController.endGame();
        System.exit(0);
	}
	
	/**
	 * Receives the new state and its id, then make transition between one state and another.
	 * @param newState
	 * @param state
	 */
	private void changeState(State newState, int state) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("state", state);
			gateway.callService(gateway.getCurrentDevice(), "updateStateInfo",
					RFDevicesDriver.RFDEVICES_DRIVER, null, null, map);
		} catch (ServiceCallException e) {
			e.printStackTrace();
		}
		
		stack = estadoAtual.unload();
		estadoAtual = newState;
		estadoAtual.load(devicesController, stack);
		Window.GetInstance().change(estadoAtual);
		
		soundBack.changeState(state);
	}
	
}
