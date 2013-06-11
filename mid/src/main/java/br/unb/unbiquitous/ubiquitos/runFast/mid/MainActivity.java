package br.unb.unbiquitous.ubiquitos.runFast.mid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.unb.unbiquitous.ubiquitos.runFast.mid.R.id;

public class MainActivity extends Activity {

    private static String TAG = "mid";

    private Button startMid;
    
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
        setContentView(R.layout.main);
		
		initUI();
    }
    
    private void initUI(){
		startMid = (Button) findViewById(id.startMid);
		startMid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ControllerActivity.class);
				startActivity(intent);
			}
		});
	}

}

