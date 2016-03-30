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

public class ProximitySensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager mySensorManager;
    private Sensor proxSensor;
    private TextView distView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor);
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
        proxSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        distView = (TextView)findViewById(R.id.distView);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        // proximity sensor returns 1 value, the distance from an object in cm (or binary values representing near and far)
        float distance = event.values[0];

        // update UI to reflect new sensor reading
        distView.setText(String.valueOf(distance));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // Do something here if sensor accuracy changes.
    }

    protected void onPause(){
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        mySensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
