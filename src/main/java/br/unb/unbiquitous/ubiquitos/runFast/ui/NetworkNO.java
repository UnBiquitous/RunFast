package br.unb.unbiquitous.ubiquitos.runFast.ui;

import org.unbiquitous.uos.core.network.exceptions.NetworkException;
import org.unbiquitous.uos.core.network.model.NetworkDevice;
import org.unbiquitous.uos.network.socket.EthernetDevice;
import org.unbiquitous.uos.network.socket.connectionManager.EthernetTCPConnectionManager;

/**
 * This class is used to avoid the computer virtual machines in Mac OS.
 * Case this isn't needed, the ubiquitous.properties file must have the
 * ubiquitos.connectionManager value changed from:
 * ubiquitos.connectionManager=br.unb.unbiquitous.ubiquitos.runFast.ui.NetworkNO
 * to
 * ubiquitos.connectionManager= org.unbiquitous.uos.network.socket.connectionManager.EthernetTCPConnectionManager
 * 
 * @author rafaelsimao
 *
 */
public class NetworkNO extends EthernetTCPConnectionManager{

	private EthernetDevice serverDevice;
	
	public NetworkNO() throws NetworkException {
		super();
	}
	
	public NetworkDevice getNetworkDevice() {
		if(serverDevice == null){
			//The IP of the computer running the main game must be in the following string:
			//return new EthernetDevice("COMPUTER IP!", 14984, EthernetConnectionType.TCP);
		 	return new EthernetDevice("192.168.1.4", 14984, EthernetConnectionType.TCP);
		}
		return serverDevice;
	}

}
