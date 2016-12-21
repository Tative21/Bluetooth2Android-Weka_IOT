package com.ibm.iot.android.iotstarter.activities;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import com.ibm.iot.android.iotstarter.R;
import com.ibm.iot.android.iotstarter.fragments.BluetoothActivityFragment;
import com.ibm.iot.android.iotstarter.utils.WekaUtils;
import com.ibm.iot.android.iotstarter.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;


public class BluetoothActivity extends Activity {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private BluetoothActivityFragment bluetoothActivityFragment;

    private ConnectThread mConnectThread;
    private Handler bluetoothIn;
    private StringBuilder recDataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        recDataString = new StringBuilder();

        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init() {
        bluetoothActivityFragment = new BluetoothActivityFragment();

        bluetoothActivityFragment = (BluetoothActivityFragment)getFragmentManager().findFragmentById(R.id.fragment);

        bluetoothActivityFragment.setText1("Connecting");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mDevice = device;
            }
        }
        mConnectThread = new ConnectThread(mDevice);
        mConnectThread.start();
            connect();
        }
    }

    private void connect() {
        bluetoothIn = new Handler() {

            public void handleMessage(android.os.Message msg) {
                if (msg.what == Constants.handlerState) {
                    //if message is what we want
                    String readMessage = (String) msg.obj;                              // msg.arg1 = bytes from connect thread

                    recDataString.append(readMessage);                                  //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line

                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                       // String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //  System.out.print("Data Received = " + dataInPrint);

                        if (recDataString.charAt(0) == '#')                             //if it starts with # we know it is what we are looking for
                        {

                            String[] data = recDataString.toString().split(",");       //Split data in array

                            bluetoothActivityFragment.setText2(data[1]);
                            bluetoothActivityFragment.setText3(data[2]);
                            bluetoothActivityFragment.setText4(data[3]);
                            bluetoothActivityFragment.setText5(data[4]);
                            bluetoothActivityFragment.setText6(data[5]);
                            bluetoothActivityFragment.setText7(data[6]);

                            try {
                                bluetoothActivityFragment.CreateArray(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                    }
                }
            }
        };
    }

    private class ConnectThread extends Thread {
        private ConnectedThread mConnectedThread;
        private BluetoothDevice mmDevice;
        private BluetoothSocket mmSocket;


        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(Constants.MY_UUID);
            } catch (IOException e) {
                System.out.print(e);
            }
            mmSocket = tmp;
        }

        public void run() {
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();

            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }
            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                System.out.print(e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;

            runOnUiThread(new Runnable() {
                public void run() {
                    bluetoothActivityFragment.setText1("Connected");
                }
            });
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes = 0;
            while (true) {
                try {
                    mConnectThread.mConnectedThread.write("*".getBytes());

                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(Constants.handlerState, bytes, -1, readMessage).sendToTarget();

                } catch (IOException e) {
                    break;
                }
            }
        }
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                System.out.print(e);
            }
        }
    }
}