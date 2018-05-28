package com.lpoo1718_t1g3.mariokart;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;

import com.lpoo1718_t1g3.mariokart.networking.Connector;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mk_android.R;

import java.util.Timer;
import java.util.TimerTask;


public class ControlActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    private Boolean upPressed = false;
    private Boolean leftPressed = false;
    private Boolean rightPressed = false;

    private CheckBox accData;

    private float accValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        accData = (CheckBox) findViewById(R.id.accData);

        View.OnTouchListener o = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN || event.getAction() == MotionEvent.ACTION_DOWN) {
                    switch (v.getId()) {
                        case R.id.upBtn:
                            upPressed = true;
                            break;
                        case R.id.leftBtn:
                            leftPressed = true;
                            break;
                        case R.id.rightBtn:
                            rightPressed = true;
                            break;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_POINTER_UP || event.getAction() == MotionEvent.ACTION_UP){
                    switch (v.getId()) {
                        case R.id.upBtn:
                            upPressed = false;
                            break;
                        case R.id.leftBtn:
                            leftPressed = false;
                            break;
                        case R.id.rightBtn:
                            rightPressed = false;
                            break;
                    }
                }
                return true;
            }
        };


        findViewById(R.id.upBtn).setOnTouchListener(o);
        findViewById(R.id.leftBtn).setOnTouchListener(o);
        findViewById(R.id.rightBtn).setOnTouchListener(o);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }


    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, 50000);

    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        accValue = event.values[1];
        Connector.getInstance().write(getControllerData());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    Message getControllerData(){
        Message m = new Message(Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY, Message.SENDER.CLIENT);
        m.addOption("upPressed", upPressed);
        if (accData.isChecked()){
            boolean left = false, right = false;

            if (accValue < -2) left = true;
            else if (accValue > 2) right = true;

            m.addOption("leftPressed", left);
            m.addOption("rightPressed", right);

        } else {
            m.addOption("leftPressed", leftPressed);
            m.addOption("rightPressed", rightPressed);
        }


        return m;
    }

}
