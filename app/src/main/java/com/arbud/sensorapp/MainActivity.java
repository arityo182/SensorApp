package com.arbud.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sensorText, sensorAccelermeterText, sensorPromityText;
    private SensorManager sensorManager;
    private Sensor sensorAccelerMeter, sensorProximy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorText = findViewById(R.id.sensor_list);
        sensorAccelermeterText = findViewById(R.id.sensor_accelermeter);
        sensorPromityText = findViewById(R.id.sensor_proxymili);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorTx = new StringBuilder();
        for (Sensor sensor:sensorList) {
            sensorTx.append(sensor.getName()+" "+sensor.getVendor()+"\n");
        }
        sensorText.setText(sensorTx.toString());

        sensorAccelerMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccelerMeter==null) {
            Toast.makeText(this,"No Accelermeter Sensor",Toast.LENGTH_SHORT).show();
        }
        sensorProximy = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (sensorProximy==null) {
            Toast.makeText(this,"No Proximity Sensor",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorAccelerMeter!=null) {
            sensorManager.registerListener(this,sensorAccelerMeter, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorAccelerMeter!=null) {
            sensorManager.registerListener(this,sensorProximy, sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float value = event.values[0];
        if (sensorType==Sensor.TYPE_ACCELEROMETER) {
            sensorAccelermeterText.setText(String.format("Accelermeter Sensor : %1$,2f",value));
        }
        if (sensorType==Sensor.TYPE_PROXIMITY) {
            sensorPromityText.setText(String.format("Accelermeter Sensor : %1$,2f",value));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}