package comp3275.uwi.comp3275_a2;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BluetoothActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private static final int REQUEST_ENABLE_BLUE=1;
    private ArrayList<String> devices;
    private boolean isRegistered=false;
    private IntentFilter filter;                        //  to be used to register for ACTION_FOUND
    private IntentFilter filter2;                       // to be used to register for ACTION_DISCOVERY_FINISHED
    private IntentFilter filter3;                       // to be used to register for ACTION_DISCOVERY_STARTED

    private BroadcastReceiver receiver;
    private ProgressDialog scanProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
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

        devices = new ArrayList<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null){
            // device does not support Bluetooth
            // show message indicating such to user
            Toast.makeText(BluetoothActivity.this, "Sorry, your device does not support Bluetooth!", Toast.LENGTH_LONG).show();
        }

        else{
            // Bluetooth is supported on this device

            // check if it is currently enabled
            if(!bluetoothAdapter.isEnabled()){
                // bluetooth is currently disabled so attempt to turn on
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUE);
            }

            else{
                // bluetooth is already enabled
                startBluetoothScanning();
            }
        }
    }

    public void startBluetoothScanning(){

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                // when a device is discovered
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    // get the BluetoothDevice object from the intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    // add the name and MAC address of the discovered device to arraylist to later populate listview
                    devices.add(device.getName() + " - " + device.getAddress());
                }

                if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                    // Bluetooth adapter has started the remote device discovery process.

                    // display progress dialog to user indicating the scanning process is taking place
                    scanProgress = ProgressDialog.show(BluetoothActivity.this, "Please Wait", "Scanning for devices", true);
                }

                if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    // Bluetooth adapter has finished the device discovery process

                    // remove progress dialog
                    scanProgress.dismiss();

                    Toast.makeText(BluetoothActivity.this, "Scanning Complete", Toast.LENGTH_SHORT).show();

                    // get access to the list view showing the discovered devices
                    ListView listView = (ListView)findViewById(R.id.discovered_devices);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(BluetoothActivity.this, android.R.layout.simple_list_item_1, devices);

                    // Populate the listview created using an adapter
                    listView.setAdapter(adapter);
                }
            }
        };

        // Register the broadcast receiver
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter3 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);

        registerReceiver(receiver, filter);
        registerReceiver(receiver, filter2);
        registerReceiver(receiver, filter3);

        // boolean updated to indicate that the broadcast receiver was registered
        isRegistered = true;

        bluetoothAdapter.startDiscovery();
    }

    // this method is a callback for when the activity started by this activity to request enabling of bluetooth returns control to this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // to ensure which request we are dealing with
        if(requestCode == REQUEST_ENABLE_BLUE){
            // ensure the request was successful
            if(resultCode == RESULT_OK){
                // bluetooth is now enabled
                startBluetoothScanning();
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(BluetoothActivity.this, "Failed to enable Bluetooth!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy(){

        // check if broadcast receiver was ever registered
        if(isRegistered){
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}