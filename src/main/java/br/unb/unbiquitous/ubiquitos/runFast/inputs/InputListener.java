package br.unb.unbiquitous.ubiquitos.runFast.inputs;

/**
 * Interface used to listen to the input events.
 *
 */
public interface InputListener {

	/**
	 * Called when some input is performed.
	 * @param e
	 */
	public void inputPerformed(InputEvent e);
	/**
	 * Called when some input is released.
	 * @param e
	 */
	public void inputReleased(InputEvent e);
	
}
