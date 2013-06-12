package br.unb.unbiquitous.ubiquitos.runFast.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;

import br.unb.unbiquitous.ubiquitos.runFast.inputs.InputEvent;
import br.unb.unbiquitous.ubiquitos.runFast.inputs.InputListener;
import br.unb.unbiquitous.ubiquitos.runFast.states.StateManager;

public class Team implements InputListener{

	private static int teamNumber = 0;
	
	private Car car;
	private CarTemplate carType;
	private int thisTeamNumber;

	private UpDevice pilot;
	private UpDevice copilot;
	private List<UpDevice> assistants;
	
	public Team(UpDevice pilotDevice) {
		car = null;
		carType = null;
		thisTeamNumber = teamNumber;
		++teamNumber;
		pilot = pilotDevice;
		copilot = null;
		setAssistants(new ArrayList<UpDevice>());
	}
	
	/*public Team(CarTemplate carType, UpDevice pilotDevice, UpDevice copilotDevice) {
		this.carType = carType;
		++teamNumber;
		initTeamCar(carType);
		pilot = pilotDevice;
		copilot = copilotDevice;
		setAssistants(new ArrayList<UpDevice>());
	}*/
	
	public void initTeamCar(CarTemplate carType){
		car = new Car(430,470+thisTeamNumber*25,carType);
	}
	
	public void render(Graphics2D g, int cameraX, int cameraY, JPanel panel) {
		if(car != null)
			car.render(g, cameraX, cameraY, panel);
	}
	
	public int update(int dt) {
		if(car != null)
			return car.update(dt);
		return StateManager.SAME_STATE;
	}
	
	/**
	 * Make a move based in the InputEvent
	 */
	public void inputPerformed(InputEvent e) {
		if(car!=null) {
			if(e.getDevice()==pilot)
				pilotInputPerformed(e);
			else if(e.getDevice()==copilot)
				copilotInputPerformed(e);
		}
	}
	
	public void inputReleased(InputEvent e) {
		if(car!=null) {
			if(e.getDevice()==pilot)
				pilotInputReleased(e);
			else if(e.getDevice()==copilot)
				copilotInputReleased(e);
		}
	}
	
	
	/**
	 * pilotInputs
	 * @param InputEvent e
	 */
	private void pilotInputPerformed(InputEvent e){
		switch(e.getInputCode()){
			case InputEvent.IC_UP:
				car.startRotation(Car.ROTATE_UP);
				break;
			case InputEvent.IC_DOWN:
				car.startRotation(Car.ROTATE_DOWN);
				break;
			case InputEvent.IC_LEFT:
				car.startRotation(Car.ROTATE_LEFT);
				break;
			case InputEvent.IC_RIGHT:
				car.startRotation(Car.ROTATE_RIGHT);
				break;
			case InputEvent.IC_ACTION:
				car.startAcceleration();
				break;
			case InputEvent.IC_PLUS_LEFT:
				car.changeOptionToLeft();
				break;
			case InputEvent.IC_PLUS_RIGHT:
				car.changeOptionToRight();
				break;
			case InputEvent.IC_PLUS_SELECT:
				car.activateItem();
				break;
			default:
				break;
		}
	}

	private void pilotInputReleased(InputEvent e){
		switch(e.getInputCode()){
			case InputEvent.IC_UP:
				car.stopRotation(Car.ROTATE_UP);
				break;
			case InputEvent.IC_DOWN:
				car.stopRotation(Car.ROTATE_DOWN);
				break;
			case InputEvent.IC_LEFT:
				car.stopRotation(Car.ROTATE_LEFT);
				break;
			case InputEvent.IC_RIGHT:
				car.stopRotation(Car.ROTATE_RIGHT);
				break;
			case InputEvent.IC_ACTION:
				car.stopAcceleration();
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * Copilot Inputs
	 * @param InputEvent e
	 */
	private void copilotInputPerformed(InputEvent e){
		switch(e.getInputCode()){
			case InputEvent.IC_UP:
				car.startSightRotation(Car.ROTATE_UP);
				break;
			case InputEvent.IC_DOWN:
				car.startSightRotation(Car.ROTATE_DOWN);
				break;
			case InputEvent.IC_LEFT:
				car.startSightRotation(Car.ROTATE_LEFT);
				break;
			case InputEvent.IC_RIGHT:
				car.startSightRotation(Car.ROTATE_RIGHT);
				break;
			case InputEvent.IC_ACTION:
				car.shoot();
				break;
			case InputEvent.IC_ACTION2:
				car.leaveBomb();
				break;
			default:
				break;
		}
	}
	
	
	private void copilotInputReleased(InputEvent e){
		switch(e.getInputCode()){
			case InputEvent.IC_UP:
				car.stopSightRotation(Car.ROTATE_UP);
				break;
			case InputEvent.IC_DOWN:
				car.stopSightRotation(Car.ROTATE_DOWN);
				break;
			case InputEvent.IC_LEFT:
				car.stopSightRotation(Car.ROTATE_LEFT);
				break;
			case InputEvent.IC_RIGHT:
				car.stopSightRotation(Car.ROTATE_RIGHT);
				break;
			case InputEvent.IC_ACTION:
				break;
			default:
				break;
		}
	}
	
	
	/* Getters and Setters*/
	/**
	 * Gets the team Car.
	 * @return Car
	 */
	public Car getCar() {
		return car;
	}
	
	/**
	 * @return the carType
	 */
	public CarTemplate getCarType() {
		return carType;
	}

	/**
	 * @return the thisTeamNumber
	 */
	public int getThisTeamNumber() {
		return thisTeamNumber;
	}

	/**
	 * Pilot
	 * @return int
	 */
	public UpDevice getPilot() {
		return pilot;
	}

	public void setPilot(UpDevice pilot) {
		this.pilot = pilot;
	}

	/**
	 * Copilot
	 * @return int
	 */
	public UpDevice getCopilot() {
		return copilot;
	}

	public void setCopilot(UpDevice copilot) {
		this.copilot = copilot;
	}

	/**
	 * Assistants
	 * @return List<UpDevices>
	 */
	public List<UpDevice> getAssistants() {
		return assistants;
	}

	public void setAssistants(List<UpDevice> assistants) {
		this.assistants = assistants;
	}
	
	public void addAssistant(UpDevice assistant) {
		assistants.add(assistant);
	}
	
}
