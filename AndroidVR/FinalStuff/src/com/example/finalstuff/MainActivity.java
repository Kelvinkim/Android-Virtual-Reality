package com.example.finalstuff;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;

	private float[] mAccelerometer = null;
	private float[] mGeomagnetic = null;
	RelativeLayout rel;
	private double roll_value = 0.0;
	private double azimuth_value = 0.0;
	String t = "";
	
	public void onAccuracyChanged(Sensor sensor, int accuracy){
		
	}

	@Override

	public void onSensorChanged(SensorEvent event) {
		
		//ImageView nImageView = (ImageView) findViewById(R.id.imageView1);
  	    //ImageView nImageView = (ImageView) findViewById(R.id.View1);
	    // onSensorChanged gets called for each sensor so we have to remember the values
	    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	        mAccelerometer = event.values;
	    }

	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
	        mGeomagnetic = event.values;
	    }

	    if (mAccelerometer != null && mGeomagnetic != null) {
	        float R[] = new float[9];
	        float I[] = new float[9];
	        boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometer, mGeomagnetic);

	        if (success) {
	            float orientation[] = new float[3];
	            SensorManager.getOrientation(R, orientation);
	            // orientation contains the azimuth(direction), pitch and roll values.
	              double azimuth = 180 * orientation[0] / Math.PI;
	              double pitch = 180 * orientation[1] / Math.PI;
	              double roll = 180 * orientation[2] / Math.PI;  
	              roll_value = roll;
	              azimuth_value = azimuth;
	              t = "Azimuth: " + azimuth_value + "\nPitch: " + pitch + "\nRoll: " + roll_value;
	              display(t);
	  	    	 /* azimuth_text.setText(Double.toString(azimuth));
	  	    	  pitch_text.setText(Double.toString(pitch));
	  	    	  roll_text.setText(Double.toString(roll));*/
	  	    	  
	              
			// TODO: udpate view based on these directions
	        }
	    }
	    if(roll_value < -120.0 || roll_value > 120.0){
	    	rel.setBackground(getResources().getDrawable(android.R.color.holo_red_dark));
	    }
	    else if(roll_value < 60 && roll_value > -60){
	    	rel.setBackground(getResources().getDrawable(android.R.color.darker_gray));
	    }
	    else{
		    if((azimuth_value >= 0  && azimuth_value < 45) || (azimuth_value < 0 && azimuth_value >= -45)){
		    	rel.setBackground(getResources().getDrawable(android.R.color.holo_blue_dark));
	    		//nImageView.setImageResource(R.drawable.fifth);
	    	  }
		    else if((azimuth_value >= 45  && azimuth_value < 135)){
	    		//nImageView.setImageResource(R.drawable.sixth);
		    	rel.setBackground(getResources().getDrawable(android.R.color.holo_green_dark));
		    }
		    
		    else if((azimuth_value >= 135) || (azimuth_value < -135 )){
		    		//nImageView.setImageResource(R.drawable.first);
		    		rel.setBackground(getResources().getDrawable(android.R.color.holo_orange_dark));
		    	}
		    else if((azimuth_value < -45  && azimuth_value >= -135)){
		    		//nImageView.setImageResource(R.drawable.second);
		    		rel.setBackground(getResources().getDrawable(android.R.color.holo_purple));
		    	}
	    }
	    
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		rel = (RelativeLayout) findViewById(R.id.frame);
		//ImageView nImageView = (ImageView) findViewById(R.id.imageView1);
		//nImageView.setImageResource(R.drawable.fifth);
				
	}

	@Override
	protected void onResume() {
	    super.onResume();

	    mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	    mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    mSensorManager.unregisterListener(this, accelerometer);
	    mSensorManager.unregisterListener(this, magnetometer);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void display(String d){
		TextView t = (TextView) findViewById(R.id.text);
		t.setText(d);
	}
	
	/*public void change(int i){
		if(i==1){
			rel.setBackground(getResources().getDrawable(android.R.color.holo_blue_dark));
		}
		else if(i==2){
			rel.setBackground(getResources().getDrawable(android.R.color.holo_green_dark));
		}
		else if(i==3){
			rel.setBackground(getResources().getDrawable(android.R.color.holo_orange_dark));
		}
		else if(i==4){
			rel.setBackground(getResources().getDrawable(android.R.color.holo_red_dark));
		}
	}*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
