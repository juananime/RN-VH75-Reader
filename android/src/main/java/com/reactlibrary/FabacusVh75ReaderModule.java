
package com.reactlibrary;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.BaseAdapter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.vanch.vhxdemo.VH73Device;
import com.vanch.vhxdemo.helper.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.greenrobot.event.EventBus;


public class FabacusVh75ReaderModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    BluetoothAdapter mBluetoothAdapter;
    boolean hasRequetBt = false;
    public static final int REQUEST_ENABLE_BT = 1;
    private String lastDeviceMac = "";
    private String connectToDevice;

    public static final String action_scan = "scan_click";
    public static final String action_disconnect = "disconnect_click";
    protected static final int CONNECTING = 1;
    protected static final int CONNECTING_OK = 2;
    protected static final int CONNECTING_FAILE = 3;
    protected static final int DISCONNECT = 4;

    public VH73Device vh73Device;
    public static class FreshList {

    }

    /**
     * device found event
     *
     * @author liugang
     */
    public static class BTDeviceFoundEvent {
        BluetoothDevice device;

        public BTDeviceFoundEvent(BluetoothDevice device) {
            this.device = device;
        }

        public BluetoothDevice getDevice() {
            return device;
        }

        public void setDevice(BluetoothDevice device) {
            this.device = device;
        }

    }


    BaseAdapter adapter;
    //	Set<BluetoothDevice> pairedDevices;
    //Set<BluetoothDevice> foundDevices;
    List<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    //	static BluetoothDevice currentDevice;
    static VH73Device currentDevice;

    public FabacusVh75ReaderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        initBluetooth();


    }

    /**
     * init bluetooth adaptor
     */
    private void initBluetooth() {
        //check support
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            //bluetoothNotSupport();
            return;
        }

        //check enable
        if (!mBluetoothAdapter.isEnabled() && !hasRequetBt) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            hasRequetBt = true;
            return;
        }
    }
    /**
     * Event sender from React Native
     * @param reactContext
     * @param eventName
     * @param params
     */
    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }



    /**
     * RN bridge mnethod to connect to selected device
     * @param deviceName
     */
    @ReactMethod
    public void connectToDevice(String deviceName) {
        Log.e("CDCDC", "connectToDevice :; attemp to connect to "+deviceName);
        connectToDevice = deviceName;
        queryPairedDevices();

    }

    /**
     * RN bridge method to search devices already paired via Bluetooth
     */
    @ReactMethod
    public void searchDevices() {
        foundDevices.clear();
        //Gets a collection of paired remote Bluetooth devices
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.e("OGTAGDEBUG::", "onClick: " + devices.size());
        if (devices.size() > 0) {
            for (Iterator<BluetoothDevice> it = devices.iterator(); it.hasNext(); ) {
                BluetoothDevice device2 = (BluetoothDevice) it.next();
                //Prints the physical address of the remote Bluetooth device
                System.out.println("Connected Bluetooth device:" + device2.getAddress());

                WritableMap map = Arguments.createMap();


                map.putString("DeviceName",device2.getName());

                sendEvent(reactContext, "FabacusOnDeviceDetection",map);


                // unpairDevice(device2);
            }
        } else {
            System.out.println("There is no paired remote Bluetooth device yet！");
        }


        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.startDiscovery();
        }
    }


    //Querying paired devices
    private void queryPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
              //  Log.i(TAG, "found paired device " + device.getName() + " "+device.toString());
                Log.e("OGTAGDEBUG::", "found paired device " + device.getName() + " "+device.toString());
                foundDevices.add(device);
                EventBus.getDefault().post(new BTDeviceFoundEvent(device));

              //  Log.d(TAG, "last device " + lastDeviceMac);
                Log.e("OGTAGDEBUG::", "last device " + lastDeviceMac);
                //last connected device
                if(device.getName().equals(connectToDevice) && currentDevice==null) {
                  //  Log.d(TAG, "will connect to last device " + lastDeviceMac);
                    Log.e("OGTAGDEBUG::", "will connect to last device " + connectToDevice);
                    currentDevice = new VH73Device(getCurrentActivity(), device);
                    new Thread() {
                        public void run() {
                            if (currentDevice.connect()) {
                                lastDeviceMac = currentDevice.getAddress();

                               // ConfigUI.setConfigLastConnect(getActivity(), currentDevice.getAddress());
                            } else {
                               // Utility.showTostInNonUIThread(getActivity(), Strings.getString(R.string.msg_fail_to_connect) + currentDevice.getAddress());
                                currentDevice = null;
                            }
                            EventBus.getDefault().post(new FreshList());
                        }
                    }.start();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "FabacusVh75Reader";
    }
}