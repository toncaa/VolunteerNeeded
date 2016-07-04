package project.mosis.volunteerneeded.bluetoothscanner.Connecting;

import android.bluetooth.BluetoothSocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by MilanToncic on 6/29/2016.
 */
public class ManageConnectThread extends Thread {

    public static void sendData(BluetoothSocket socket, int data) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream(4);
        output.write(data);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(output.toByteArray());
    }

    public static  int receiveData(BluetoothSocket socket) throws IOException {
        byte[] buffer = new byte[4];
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        InputStream inputStream = socket.getInputStream();
        inputStream.read(buffer);
        return input.read();

    }
}

