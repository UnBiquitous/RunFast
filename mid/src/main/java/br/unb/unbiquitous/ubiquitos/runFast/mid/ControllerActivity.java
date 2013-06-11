package br.unb.unbiquitous.ubiquitos.runFast.mid;

import java.io.File;
import java.io.IOException;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import org.unbiquitous.uos.core.ClassLoaderUtils;
import org.unbiquitous.uos.core.ContextException;
import org.unbiquitous.uos.core.UOSApplicationContext;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.network.socket.connectionManager.EthernetTCPConnectionManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class ControllerActivity extends Activity{

	private static String TAG = "controller";
	
	private Button left,right,up,down, action1,action2, plusLeft,plusRight,plusSelect;
	
	private Gateway gateway = null;
	
	/**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.controller);
        
        initMiddleware();
        initUI();
        initListeners();
    }
	
	/**
	 * Starts the middleware.
	 */
	private void initMiddleware() {
		File writableDir = getApplicationContext().getDir("temp", Context.MODE_WORLD_WRITEABLE);
		File tempDir = null;
		try {
			tempDir = File.createTempFile("temp.owl", ""+System.nanoTime(),writableDir);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final File tempDir2 = tempDir;
		
		ResourceBundle prop = new ListResourceBundle() {
			protected Object[][] getContents() {
				return new Object[][] {
						/*
					{"ubiquitos.connectionManager", "br.unb.unbiquitous.ubiquitos.network.ethernet.connectionManager.EthernetTCPConnectionManager"},
					{"ubiquitos.eth.tcp.port", "14984"},
					{"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
					{"ubiquitos.eth.udp.port", "15001"},
					{"ubiquitos.eth.udp.passivePortRange", "15002-15017"},
					{"ubiquitos.uos.deviceName", "aUosDevice"},
					{"ubiquitos.driver.deploylist", 
                        "br.unb.unbiquitous.ubiquitos.uos.driver.DeviceDriverImpl;" +
                        //"org.unbiquitous.driver.execution.ExecutionDriver;"+
                        "br.unb.unbiquitous.ubiquitos.runFast.mid.RFInputDriver"},
                    //{"ubiquitos.driver.deploylist", 
					//	"br.unb.unbiquitous.ubiquitos.runFast.mid.RFInputDriver"},
					//{"ubiquitos.application.deploylist",
					//	ControllerConsole.class.getName()},//"banana.ControllerConsole"},
					{"ubiquitos.ontology.path",tempDir2.getPath()},
						 */
					{"ubiquitos.connectionManager", EthernetTCPConnectionManager.class.getName()},
					{"ubiquitos.eth.tcp.port", "14984"},
					{"ubiquitos.eth.tcp.passivePortRange", "14985-15000"},
					{"ubiquitos.eth.udp.port", "15001"},
					{"ubiquitos.eth.udp.passivePortRange", "15002-15017"},
					//{"ubiquitos.uos.deviceName", "aUosDevice"},
					{"ubiquitos.driver.deploylist", 
					//	"br.unb.unbiquitous.ubiquitos.uos.driver.DeviceDriverImpl;" +
						RFInputDriver.class.getName()},
					{"ubiquitos.ontology.path",tempDir2.getPath()},
		        };
			}
		};

		
		ClassLoaderUtils.builder = new ClassLoaderUtils.DefaultClassLoaderBuilder(){
		 	 public ClassLoader getParentClassLoader() {
		 		 return getClassLoader();
		 	 };
		};

		UOSApplicationContext uosApplicationContext = new UOSApplicationContext();
		try {
			uosApplicationContext.init(prop);
			gateway = uosApplicationContext.getGateway();
		} catch (ContextException e) {
			e.printStackTrace();
		}
	}
	
	
	private void initUI() {
		left = (Button) findViewById(R.id.controller_left);
		right = (Button) findViewById(R.id.controller_right);
		up = (Button) findViewById(R.id.controller_up);
		down = (Button) findViewById(R.id.controller_down);

		action1 = (Button) findViewById(R.id.controller_action1);
		action2 = (Button) findViewById(R.id.controller_action2);
		
		plusLeft = (Button) findViewById(R.id.controller_plusLeft);
		plusRight = (Button) findViewById(R.id.controller_plusRight);
		plusSelect = (Button) findViewById(R.id.controller_plusSelect);
	}
	
	private void initListeners() {
		ControllerListener listener = new ControllerListener(gateway);
		left.setOnTouchListener(listener);
		right.setOnTouchListener(listener);
		up.setOnTouchListener(listener);
		down.setOnTouchListener(listener);
		
		action1.setOnTouchListener(listener);
		action2.setOnTouchListener(listener);
		
		plusLeft.setOnTouchListener(listener);
		plusRight.setOnTouchListener(listener);
		plusSelect.setOnTouchListener(listener);
		
	}
}
