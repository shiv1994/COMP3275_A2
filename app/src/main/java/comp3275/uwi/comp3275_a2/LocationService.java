package comp3275.uwi.comp3275_a2;

import android.Manifest;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import comp3275.uwi.comp3275_a2.models.LocationContract;

/**
 * Created by Shiva on 4/1/2016.
 */
public class LocationService extends Service implements LocationListener{
    private LocationManager locationManager;
    private Intent intent = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        this.intent = intent;
        setUpLocationManager();
        return START_STICKY;
    }

    public void setUpLocationManager(){
        //Setting up location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(getBaseContext(), "You do not have the necessary permissions for the service to be run!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this,null);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Open db and write location
        SQLiteOpenHelper helper = new DBHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LocationContract.LocationEntry.LAT, location.getLatitude());
        cv.put(LocationContract.LocationEntry.LONG, location.getLongitude());
        cv.put(LocationContract.LocationEntry.ALTITUDE, location.getAltitude());
        db.insert(LocationContract.LocationEntry.TABLE_NAME, null, cv);
        //Stop service after location obtained!
        this.stopService(this.intent);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Please turn on GPS.",
                Toast.LENGTH_SHORT).show();
        this.stopService(this.intent);
    }

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

}
