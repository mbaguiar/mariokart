package com.lpoo1718_t1g3.mk_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;
import android.widget.TextView;


public class ControlActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView xText;
    private TextView yText;
    private TextView zText;
    private ImageView image;

    private float accValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_control);
        image = (ImageView) findViewById(R.id.imgView);
        image.setRotation(-90);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        accValue = event.values[1];

        if (accValue < -10) accValue = -10;
        else if(accValue > 10) accValue = 10;

        image.setRotation(9f * accValue - 90f);


        /*ArrayList<Float> accValues = new ArrayList<>(Arrays.asList(event.values[0], event.values[1],
                event.values[2]));*/

        /*
        try {
            MainActivity.w.writeObject(accValues);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
