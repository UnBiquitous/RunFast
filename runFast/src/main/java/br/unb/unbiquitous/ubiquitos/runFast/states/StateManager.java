package br.unb.unbiquitous.ubiquitos.runFast.states;

import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;

import br.unb.unbiquitous.ubiquitos.runFast.devicesControl.DevicesController;
import br.unb.unbiquitous.ubiquitos.runFast.ui.Window;

public class StateManager implements Runnable{

	private Gateway gateway;
	
	public static final int SAME_STATE      = 0;
	public static final int STATE_SELECTION = 1;
	public static final int STATE_MENU      = 2;
	public static final int STATE_GAME      = 3;
	public static final int STATE_WIN       = 4;
	public static final int STATE_LOSE      = 5;
	public static final int STATE_QUIT      = 6;
	
	private State estadoAtual;
	private	Stack stack;
	private Thread mainThread;
	
	private DevicesController devicesController;
	
	private final int DELAY = 33;
	
	public StateManager(Gateway gateway){
		this.gateway = gateway;
		//devicesController = new DevicesController(gateway);
		devicesController = new DevicesController(gateway,true);
		
		estadoAtual = new StateMenu();
		estadoAtual.load(devicesController);
		Window.GetInstance().troca(estadoAtual);

		stack = new Stack();
	}
	
	public void begin(){
		mainThread = new Thread(this);
		mainThread.start();
	}
	
	public void run(){

		int frameStart, dt, sleep;
		boolean quit = false;
		dt= DELAY;
		
        while (!quit) {

        	/*Frame Start*/
        	frameStart = (int)System.currentTimeMillis();

        	/*RUN*/
        	//InputManager::getInstance()->Update();
        	//InputManager.GetInstance().registerDriver(gateway);
        	devicesController.update(dt);

    	    switch(estadoAtual.update(dt))
    	    {
    	    	case SAME_STATE:
    	    		break;
    	    	case STATE_SELECTION:
    	    		changeState(new StateSelection());
    	    		break;
    	    	case STATE_MENU:
    	    		changeState(new StateMenu());
    	    		break;
    	    	case STATE_GAME:
    	    		changeState(new StateGame());
    	    		break;
    	    	case STATE_QUIT:
    	    		quit = true;
    	    		break;
    	    	default:
    	    		break;
    	    }

    	    /* Todos os comandos de Renderizacao */
    	    estadoAtual.render();

        	
        	/*Delay*/
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
        System.exit(0);
	}
	
	private void changeState(State newState) {
		stack = estadoAtual.unload();
		estadoAtual = newState;
		estadoAtual.load(devicesController, stack);
		Window.GetInstance().troca(estadoAtual);
	}
	
}
