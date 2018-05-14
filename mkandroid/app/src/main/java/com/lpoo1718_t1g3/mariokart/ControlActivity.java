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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpoo1718_t1g3.mariokart.networking.Connector;
import com.lpoo1718_t1g3.mariokart.networking.Message;
import com.lpoo1718_t1g3.mk_android.R;


public class ControlActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView xText;
    private TextView yText;
    private TextView zText;
    private ImageView image;

    private Button up;
    private Button left;
    private Button right;

    private Boolean upPressed = false;
    private Boolean leftPressed = false;
    private Boolean rightPressed = false;

    private float accValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        up = (Button) findViewById(R.id.upBtn);
        left = (Button) findViewById(R.id.lftBtn);
        right = (Button) findViewById(R.id.rgtBtn);

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    upPressed = true;
                    System.out.println("Pressed down");
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    upPressed = false;
                    System.out.println("Released");
                }


                return false;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    leftPressed = true;
                    System.out.println("Pressed down");
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    leftPressed = false;
                    System.out.println("Released");
                }


                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    rightPressed = true;
                    System.out.println("Pressed down");
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    rightPressed = false;
                    System.out.println("Released");
                }


                return false;
            }
        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        Message m = new Message(Message.MESSAGE_TYPE.CONTROLLER_ACTIVITY, Message.SENDER.CLIENT);
        m.addOption("upPressed", upPressed);
        m.addOption("leftPressed", leftPressed);
        m.addOption("rightPressed", rightPressed);
        Connector.getInstance().write(m);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
