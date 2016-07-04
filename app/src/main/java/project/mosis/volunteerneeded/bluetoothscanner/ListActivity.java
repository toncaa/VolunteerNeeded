package project.mosis.volunteerneeded.bluetoothscanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.bluetoothscanner.Connecting.*;

public class ListActivity extends ActionBarActivity implements DeviceListFragment.OnFragmentInteractionListener, ListenerFragment.OnFragmentInteractionListener  {


    private DeviceListFragment mDeviceListFragment;
    private ListenerFragment listenerFragment;
    private BluetoothAdapter BTAdapter;

    private ServerConnectThread serverBTDevice;
    private ManageConnectThread manageConnectThread;
    private ConnectThread clientBTDevice;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    public static final int REQUEST_BLUETOOTH = 1;
    public static final int REQUEST_CONNECT_DEVICE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        Boolean value = false;
        if (extras != null) {
             value = extras.getBoolean("Listener");
            //The key argument here must match that used in the other activity
        }


        //TODO Check if bluetooth is enabled

        BTAdapter = BluetoothAdapter.getDefaultAdapter();


        BTAdapter.getRemoteDevice( BTAdapter.getAddress());


        // Phone does not support Bluetooth so let the user know and exit.
        if (BTAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }


        FragmentManager fragmentManager = getSupportFragmentManager();

        mDeviceListFragment = DeviceListFragment.newInstance(BTAdapter);


        if(value)
        {
            listenerFragment = ListenerFragment.newInstance("param1", "param2");
            fragmentManager.beginTransaction().replace(R.id.container, listenerFragment).commit();
        }
        else
            fragmentManager.beginTransaction().replace(R.id.container, mDeviceListFragment).commit();

        serverBTDevice = new ServerConnectThread();
        clientBTDevice = new ConnectThread();
        manageConnectThread = new ManageConnectThread();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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

    @Override
    public void onFragmentInteraction(DeviceItem device) throws IOException {
        DeviceItem item = device;

        //sending friend request to selected device
     //   serverBTDevice.acceptConnect(BTAdapter,applicationUUID);
        BluetoothDevice bt_device = BTAdapter.getRemoteDevice(device.getAddress());

        clientBTDevice.connect(bt_device, applicationUUID);

        BluetoothSocket bSocket = clientBTDevice.getSocket();
        manageConnectThread.sendData(bSocket,3);
//
//        int result = manageConnectThread.receiveData(bSocket);
//        Toast.makeText(this,String.valueOf(manageConnectThread.receiveData(bSocket)),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        Toast.makeText(this, "Hellooo!", Toast.LENGTH_LONG).show();
    }
}
