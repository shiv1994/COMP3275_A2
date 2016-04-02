package comp3275.uwi.comp3275_a2.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shiva on 3/30/2016.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LocationDB";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
