package br.unb.unbiquitous.ubiquitos.runFast.game;

import java.util.Random;

import javax.sound.sampled.Clip;

import br.unb.unbiquitous.ubiquitos.runFast.states.StateManager;

/**
 * Manipulates all the background musics played through the states.
 *
 */
public class SoundBackground {
	
	int state; //keeps the previous state
	Random random = null;
	
	public SoundBackground() {
		SoundEffect.init();
		state = StateManager.SAME_STATE;
		random = new Random();
	}
	
	/**
	 * Updates the state and the change the background music accordingly.
	 * @param state
	 */
	public void changeState(int state){
		if(this.state!= state){
			stopPreviousState(state);
			switch (state) {
			case StateManager.STATE_MENU:
				if(!SoundEffect.MENU.isPlaying())
					SoundEffect.MENU.play(Clip.LOOP_CONTINUOUSLY);
				break;
			case StateManager.STATE_SELECTION:
				if(!SoundEffect.MENU.isPlaying())
					SoundEffect.MENU.play(Clip.LOOP_CONTINUOUSLY);
				break;
			case StateManager.STATE_GAME:
				if(random.nextBoolean())
					SoundEffect.GAME1.play(Clip.LOOP_CONTINUOUSLY);
				else
					SoundEffect.GAME2.play(Clip.LOOP_CONTINUOUSLY);
				break;
			case StateManager.STATE_WIN:
				SoundEffect.WIN.play(Clip.LOOP_CONTINUOUSLY);
				break;

			default:
				break;
			}
		}
		
		this.state = state;
	}
	
	/**
	 * Stops some previous state music.
	 * @param state
	 */
	private void stopPreviousState(int state){
		switch (this.state) {
		case StateManager.STATE_MENU:
			if(state != StateManager.STATE_SELECTION)
				SoundEffect.MENU.stop();
			break;
		case StateManager.STATE_SELECTION:
			if(state != StateManager.STATE_MENU)
				SoundEffect.MENU.stop();
			break;
		case StateManager.STATE_GAME:
			SoundEffect.GAME1.stop();
			SoundEffect.GAME2.stop();
			break;
		case StateManager.STATE_WIN:
			SoundEffect.WIN.stop();
			break;

		default:
			break;
		}
	}
	
}
