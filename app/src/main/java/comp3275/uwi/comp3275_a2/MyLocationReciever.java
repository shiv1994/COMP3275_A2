package comp3275.uwi.comp3275_a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Shiva on 3/30/2016.
 */
public class MyLocationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
//        Intent main = new Intent(context, LocationActivity.class);
//        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(main);
          context.startService(new Intent(context, LocationService.class));

    }
}
