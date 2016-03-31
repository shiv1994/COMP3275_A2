package comp3275.uwi.comp3275_a2.models;

import android.provider.BaseColumns;

/**
 * Created by Shiva on 3/30/2016.
 */
public class LocationContract {
    public static final String INT_TYPE = " INTEGER";
    public static final String TEXT_TYPE = " TEXT";

    public static final String CREATE_TABLE = "CREATE TABLE " +
            LocationEntry.TABLE_NAME + " (" +
            LocationEntry._ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
            LocationEntry.LAT + TEXT_TYPE + " "+
            LocationEntry.LONG + TEXT_TYPE +
            LocationEntry.TIME + " DATE DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public static abstract class LocationEntry implements BaseColumns {
        //defines the fields of table, and its name.
        public static final String TABLE_NAME = "locations";
        public static final String LAT = "latitude";
        public static final String LONG = "longtitude";
        public static final String TIME = "timecreated";
    }
}
