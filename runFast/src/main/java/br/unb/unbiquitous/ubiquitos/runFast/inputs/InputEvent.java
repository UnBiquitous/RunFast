package br.unb.unbiquitous.ubiquitos.runFast.inputs;

import java.util.EventObject;

import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;

public class InputEvent extends EventObject {

	/**
	 * private static final long serialVersionUID = 3864120084930711205L;
	 */
	private static final long serialVersionUID = 3864120084930711205L;
	
	public static final int IC_ENTER       = 0x00;
	public static final int IC_UP          = 0x01;
	public static final int IC_DOWN        = 0x02;
	public static final int IC_LEFT        = 0x03;
	public static final int IC_RIGHT       = 0x04;
	public static final int IC_ACTION      = 0x05;
	public static final int IC_ACTION2     = 0x06;
	public static final int IC_PLUS_SELECT = 0x10;
	public static final int IC_PLUS_LEFT   = 0x11;
	public static final int IC_PLUS_RIGHT  = 0x12;
	
	private int inputCode;
	private UpDevice device;
	
	//here's the constructor
    public InputEvent(Object source, int inputCode, UpDevice device) {
        super(source);
        this.setInputCode(inputCode);
        this.setDevice(device);
    }

	public int getInputCode() {
		return inputCode;
	}

	public void setInputCode(int inputCode) {
		this.inputCode = inputCode;
	}

	public UpDevice getDevice() {
		return device;
	}

	public void setDevice(UpDevice device) {
		this.device = device;
	}

}
