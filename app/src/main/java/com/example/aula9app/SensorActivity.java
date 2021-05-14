package com.example.aula9app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xViewA, yViewA, zViewA;
    private TextView xViewO, yViewO, zViewO;
    private TextView xViewL, xViewP;
    LinearLayout layout;
    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        xViewA = findViewById(R.id.ax);
        yViewA = findViewById(R.id.ay);
        zViewA = findViewById(R.id.az);
        xViewO = findViewById(R.id.ox);
        yViewO = findViewById(R.id.oy);
        zViewO = findViewById(R.id.oz);
        xViewL = findViewById(R.id.lx);
        xViewP = findViewById(R.id.px);

        layout = findViewById(R.id.layout);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            xViewA.setText("Accelerometer X: " + event.values[0]);
            yViewA.setText("Accelerometer Y: " + event.values[1]);
            zViewA.setText("Accelerometer Z: " + event.values[2]);

        }
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY){
            xViewO.setText("Orientation X: " + event.values[0]);
            yViewO.setText("Orientation Y: " + event.values[1]);
            zViewO.setText("Orientation Z: " + event.values[2]);

            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];

            if(x <= 2 && x >= -2){
                if (y <= 2 && y >= -2){
                    if (z >= 0){
                        lyingUp();
                    } else{
                        lyingDown();
                    }

                } else if (z <= 2 && z >= -2){
                    portrait();
                }
            }
            else {
                landscape();
            }


        }
        if (event.sensor.getType() == Sensor.TYPE_LIGHT){
            xViewL.setText("Light: " + event.values[0]);
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            xViewP.setText("Proximity: " + event.values[0]);
        }
    }

    private void landscape() {
        layout.setBackgroundColor(getColor(R.color.green));
    }

    private void portrait() {
        layout.setBackgroundColor(getColor(R.color.blue));
    }

    private void lyingUp() {
        layout.setBackgroundColor(getColor(R.color.red));
    }

    private void lyingDown() {
        finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }
}