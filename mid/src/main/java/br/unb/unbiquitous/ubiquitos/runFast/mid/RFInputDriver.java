package br.unb.unbiquitous.ubiquitos.runFast.mid;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jpcap.NetworkInterface;

import org.unbiquitous.uos.core.Logger;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.adaptabitilyEngine.NotifyException;
import org.unbiquitous.uos.core.applicationManager.UOSMessageContext;
import org.unbiquitous.uos.core.driverManager.UosEventDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDevice;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;
import org.unbiquitous.uos.core.messageEngine.dataType.UpNetworkInterface;
import org.unbiquitous.uos.core.messageEngine.dataType.UpService.ParameterType;
import org.unbiquitous.uos.core.messageEngine.messages.Notify;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceCall;
import org.unbiquitous.uos.core.messageEngine.messages.ServiceResponse;
import org.unbiquitous.uos.core.network.model.NetworkDevice;

public class RFInputDriver implements UosEventDriver{

	private static Logger logger = Logger.getLogger(RFInputDriver.class);
	
	public static final String RFINPUT_DRIVER = "br.unb.unbiquitous.ubiquitos.runFast.mid.RFInputDriver";
    public static final String RFINPUT_EVENT = "RFInputEvent";
    public static final String RFINPUT_DRIVER_PERFORMED_PARAM = "isPerformed";
    public static final String RFINPUT_DRIVER_IC_PARAM = "inputCode";
    public static final String RFINPUT_DRIVER_DEVNAME_PARAM = "deviceName";

	private Gateway gateway;
	private String instanceId;
	
	private int numberOfListeners = 0;

    private Vector<UpNetworkInterface> listenerDevices = new Vector<UpNetworkInterface>();
	
	@Override
	public void destroy() {}

	@Override
	public UpDriver getDriver() {
		UpDriver driver = new UpDriver("br.unb.unbiquitous.ubiquitos.runFast.mid.RFInputDriver");
		
		driver.addService("inputPerformed")
			.addParameter("inputCode", ParameterType.MANDATORY);
		driver.addService("inputReleased")
			.addParameter("inputCode", ParameterType.MANDATORY);
		
		return driver;
	}

	@Override
	public List<UpDriver> getParent() {
		return new ArrayList<UpDriver>();
	}

	@Override
	public void init(Gateway gateway, String instanceId) {
		this.gateway = gateway;
		this.instanceId = instanceId;
	}

	/**
	 * Register new listeners.
	 */
	@Override
	public void registerListener(ServiceCall serviceCall,
			ServiceResponse serviceResponse, UOSMessageContext messageContext) {
		NetworkDevice networkDevice = messageContext.getCallerDevice();
	       
        UpNetworkInterface uni = new UpNetworkInterface(
                networkDevice.getNetworkDeviceType(), networkDevice.getNetworkDeviceName());
       
        if (!listenerDevices.contains(uni)){
            listenerDevices.addElement(uni);
        }
        
        logger.info("Device "+networkDevice.getNetworkDeviceName()+" registered as a listenner");

        numberOfListeners++;
        //kbDriverGUI.changeNumberOfListeners(numberOfListeners);
	}

	/**
	 * Unregister some listener.
	 */
	@Override
	public void unregisterListener(ServiceCall serviceCall,
			ServiceResponse serviceResponse, UOSMessageContext messageContext) {
		NetworkDevice networkDevice = messageContext.getCallerDevice();
	       
        UpNetworkInterface uni = new UpNetworkInterface(
                networkDevice.getNetworkDeviceType(), networkDevice.getNetworkDeviceName());
        listenerDevices.removeElement(uni);
        
        logger.info("Device "+networkDevice.getNetworkDeviceName()+" unregistered from the listenners list");

        numberOfListeners--;
        //kbDriverGUI.changeNumberOfListeners(numberOfListeners);
	}

	//TEORICAL SERVICES
	public void inputPerformed(ServiceCall serviceCall, 
			ServiceResponse serviceResponse, UOSMessageContext messageContext) {
		System.out.println("PARAMETER: "+serviceCall.getParameter("inputCode"));
		notifyInput(serviceCall.getParameterString("inputCode"), true);
	}
	
	public void inputReleased(ServiceCall serviceCall, 
			ServiceResponse serviceResponse, UOSMessageContext messageContext) {
		System.out.println("PARAMETER: "+serviceCall.getParameter("inputCode"));
		notifyInput(serviceCall.getParameterString("inputCode"), false);
	}
	
	private void notifyInput(String messageToSend, boolean performed) {
		Notify notify = new Notify(RFINPUT_EVENT);

		notify.addParameter(RFINPUT_DRIVER_PERFORMED_PARAM, ""+performed);
        notify.addParameter(RFINPUT_DRIVER_IC_PARAM, messageToSend);
        notify.addParameter(RFINPUT_DRIVER_DEVNAME_PARAM, gateway.getCurrentDevice().getName());
        notify.setDriver(RFINPUT_DRIVER);
        notify.setInstanceId(instanceId);
        
        logger.info("Sending message: "+messageToSend+" - performed:"+performed+" - device: "+gateway.getCurrentDevice().getName());

        for (int i = 0 ; i < listenerDevices.size(); i++){
            UpNetworkInterface uni = (UpNetworkInterface) listenerDevices.elementAt(i);
            UpDevice device = new UpDevice("Anonymous");//gateway.getCurrentDevice();
            device.addNetworkInterface(uni.getNetworkAddress(), uni.getNetType());
            
            try {
                this.gateway.sendEventNotify(notify, device);
            } catch (NotifyException e) {
            	logger.error(e.getMessage());
            }
        }
	}

	/**
	 * @return the numberOfListeners
	 */
	public int getNumberOfListeners() {
		return numberOfListeners;
	}
	
	/*public void notifyKeyboardEvent(String messageType, String messageToSend) {
        Notify notify = new Notify(RFINPUT_EVENT);

        notify.addParameter(K_E_PARAM_MESSAGE_TYPE, messageType);
        notify.addParameter(K_E_PARAM_MESSAGE, messageToSend);
        notify.setDriver(RFINPUT_DRIVER);
        notify.setInstanceId(instanceId);
        
        logger.info("Sending message: "+messageToSend);

        for (int i = 0 ; i < listenerDevices.size(); i++){
            UpNetworkInterface uni = (UpNetworkInterface) listenerDevices.elementAt(i);
            UpDevice device = new UpDevice("Anonymous");           
            device.addNetworkInterface(uni.getNetworkAddress(), uni.getNetType());

            try {
                this.gateway.sendEventNotify(notify, device);
            } catch (NotifyException e) {
            	logger.error(e.getMessage());
            }
        }
    }*/
	
}
