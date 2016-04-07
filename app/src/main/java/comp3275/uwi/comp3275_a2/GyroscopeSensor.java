package comp3275.uwi.comp3275_a2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GyroscopeSensor extends AppCompatActivity implements SensorEventListener{

    private SensorManager mySensorManager;
    private Sensor gyroSensor;
    private TextView xRotView, yRotView, zRotView;
    private boolean gyroSensorPresent=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_sensor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        xRotView = (TextView)findViewById(R.id.gyroTxtView);
        yRotView = (TextView)findViewById(R.id.gyroTxtView2);
        zRotView = (TextView)findViewById(R.id.gyroTxtView3);

        if(gyroSensor == null){
            // device does not possess a gyroscope sensor
            // notify user of this
            Toast.makeText(GyroscopeSensor.this, "Sorry, your device does not possess a gyroscope sensor", Toast.LENGTH_LONG).show();

            gyroSensorPresent = false;

            xRotView.setText("N/A");
            yRotView.setText("N/A");
            zRotView.setText("N/A");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        // gyroscope sensor returns 3 values, one for each axis

        // rate of rotation around the x-axis
        float x = event.values[0];
        // rate of rotation around the y-axis
        float y = event.values[1];
        // rate of rotation around the z-axis
        float z = event.values[2];


        // update UI to reflect new sensor readings
        xRotView.setText(String.valueOf(x));
        yRotView.setText(String.valueOf(y));
        zRotView.setText(String.valueOf(z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // Do something here if sensor accuracy changes.
    }

    protected void onPause(){
        super.onPause();
        if(gyroSensorPresent){
            mySensorManager.unregisterListener(this);
        }
    }

    protected void onResume(){
        super.onResume();
        if(gyroSensorPresent){
            mySensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}