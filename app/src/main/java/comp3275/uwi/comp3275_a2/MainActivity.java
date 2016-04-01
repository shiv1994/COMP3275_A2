package comp3275.uwi.comp3275_a2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import comp3275.uwi.comp3275_a2.models.DBHelper;
import comp3275.uwi.comp3275_a2.models.LocationContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        SQLiteOpenHelper helper = new DBHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        String[] fields = {LocationContract.LocationEntry.LAT, LocationContract.LocationEntry.LONG};

 /* get the results. */
        Cursor res = db.query(LocationContract.LocationEntry.TABLE_NAME, fields, null, null, null, null, null);


 /* traverse results. */
        while(res.moveToNext()){

            String lat = res.getString(res.getColumnIndex(LocationContract.LocationEntry.LAT));

            Toast.makeText(getBaseContext(),"LAT IS:"+lat , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchAccelerometerActivity(View view){
        startActivity(new Intent(this, AccelerometerSensor.class));
    }

    public void launchGyroscopeActivity(View view){
        startActivity(new Intent(this, GyroscopeSensor.class));
    }

    public void launchProximityActivity(View view){
        startActivity(new Intent(this, ProximitySensor.class));
    }

    public void launchLocationActivity(View view){
        startActivity(new Intent(this, LocationActivity.class));
    }
}

