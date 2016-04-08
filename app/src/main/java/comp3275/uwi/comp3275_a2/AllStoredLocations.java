package comp3275.uwi.comp3275_a2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import comp3275.uwi.comp3275_a2.models.LocationContract;
import comp3275.uwi.comp3275_a2.models.LocationObject;

public class AllStoredLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stored_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadItems();
    }

    public void loadItems(){

        SQLiteOpenHelper helper = new DBHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

        //set up fields
        String[] fields = {LocationContract.LocationEntry.LAT, LocationContract.LocationEntry.LONG, LocationContract.LocationEntry.ALTITUDE , LocationContract.LocationEntry.TIME};

        /* get the results. */
        Cursor res = db.query(LocationContract.LocationEntry.TABLE_NAME, fields, null, null, null, null, null);

        ArrayList<LocationObject> locations = new ArrayList<>();
        locations.add(new LocationObject("TIME", "LAT", "LONG", "ALT"));
        /* traverse results. */
        while(res.moveToNext()){

            String latitude = res.getString(res.getColumnIndex(LocationContract.LocationEntry.LAT));
            String longtitude =  res.getString(res.getColumnIndex(LocationContract.LocationEntry.LONG));
            String altitude = res.getString(res.getColumnIndex(LocationContract.LocationEntry.ALTITUDE));
            String time = res.getString(res.getColumnIndex(LocationContract.LocationEntry.TIME));
            locations.add(new LocationObject(time,latitude,longtitude,altitude));
        }

        ListView items_list_view = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new CustomAdapter(AllStoredLocations.this, R.layout.locationobjectlayout ,locations);
        items_list_view.setAdapter(adapter);
    }

}
