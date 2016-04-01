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

import comp3275.uwi.comp3275_a2.models.DBHelper;
import comp3275.uwi.comp3275_a2.models.LocationContract;

/**
 * Created by Shiva on 4/1/2016.
 */
public class LocationService extends Service implements LocationListener{
    private LocationManager locationManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //OPEN UP DB AND WRITE THE LOCATION!
//        String msg = "New Latitude: " + location.getLatitude()
//                + "New Longitude: " + location.getLongitude();
//
//        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        SQLiteOpenHelper helper = new DBHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LocationContract.LocationEntry.LAT, location.getLatitude());
        cv.put(LocationContract.LocationEntry.LONG, location.getLongitude());
        db.insert(LocationContract.LocationEntry.TABLE_NAME, null, cv);
        //Stop service after first location obtained!
        this.stopSelf();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Please turn on GPS.",
                Toast.LENGTH_SHORT).show();
        this.stopSelf();
    }

    @Override
    public void onProviderEnabled(String provider) {
//        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
