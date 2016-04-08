package comp3275.uwi.comp3275_a2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private static final int MY_PERMISSION_REQUEST_LOCATION=1;
    private TextView latView, longtView, altView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
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
        progress = ProgressDialog.show(this, "Please Wait",
                "Fetching Location", true);
        setUpTextViews();
        //setUpLocationManager();
    }

    public void setUpTextViews(){
        latView = (TextView) findViewById(R.id.latView);
        longtView = (TextView) findViewById(R.id.longtView);
        altView = (TextView) findViewById(R.id.altView);
    }

    public void setUpLocationManager(){
        //Setting up location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_LOCATION);
        }
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
                this, null);
    }

    @Override
    public void onLocationChanged(Location location) {
        progress.dismiss();
        latView.setText(Double.toString(location.getLatitude()));
        longtView.setText(Double.toString(location.getLongitude()));
        altView.setText(Double.toString(location.getAltitude()));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onResume(){
        super.onResume();
        setUpLocationManager();
    }

    public void onPause(){
        super.onPause();
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_LOCATION);
        }
        locationManager.removeUpdates(LocationActivity.this);


    }

}
