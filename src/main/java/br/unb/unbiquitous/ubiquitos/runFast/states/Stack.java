package br.unb.unbiquitous.ubiquitos.runFast.states;

import java.util.List;

import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;

import br.unb.unbiquitous.ubiquitos.runFast.game.CarTemplate;

/**
 * Class used to pass information from one state to another.
 *
 */
public class Stack {

	public static final boolean STACK_EMPTY = true;

	private boolean empty;
	
	private List<UpDevice> devices;
	private int length;
	private CarTemplate cars[];
	
	/**
	 * Initiates an empty stack.
	 */
	public Stack() {
		empty = STACK_EMPTY;
	}

	/**
	 * Receives the pilots devices, chosen carTemplates and the number pilots.
	 * @param devices
	 * @param cars
	 * @param length
	 */
	public Stack(List<UpDevice> devices, CarTemplate cars[], int length) {
		empty = !STACK_EMPTY;
		this.devices = devices;
		this.cars = cars;
		this.length = length;
	}

	/**
	 * @return true if it is empty, false otherwise
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * @return the devices
	 */
	public List<UpDevice> getDevices() {
		return devices;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the cars
	 */
	public CarTemplate[] getCars() {
		return cars;
	}
	
	
}
