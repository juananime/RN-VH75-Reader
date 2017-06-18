'use strict';
import React, {Component, PropTypes} from 'react';
var {
    Platform,
    DeviceEventEmitter,
    requireNativeComponent
} = require('react-native');


import { NativeModules } from 'react-native';
var RNReactNativeRfid = NativeModules.FabacusVh75Reader;


class RFID {

    /**
     * Initial method to star search of paired devices,
     * @param res , callback receiber of array of paired devices
     */
    static getDevices(res) {
        RNReactNativeRfid.searchDevices();
        DeviceEventEmitter.addListener('FabacusOnDeviceDetection', (e) => {res(e)});
    }

    /**
     * Receive tag data from device
     * @param res callback method receiving Tags payloads
     */
    static onTagReceived(res){
        DeviceEventEmitter.addListener('FabacusOnTagReceived', (e) => {res(e)});
    }

    /**
     * Handheld trigger event
     * @param res
     */
    static FabacusOnHandheldTriggerEvent(res){
        DeviceEventEmitter.addListener('FabacusOnHandheldTriggerEvent', (e) => {res(e)});
    }

    /**
     * Connect , engage  BT device
     * @param device
     */
    static connect(device) {
        console.log('rect side attemp to connect to '+device)
        RNReactNativeRfid.connectToDevice(device)
    }

    /**
     * Device connection status update events
     * @param res
     */
    static onDeviceConnectionStatusChanged(res){
        DeviceEventEmitter.addListener('FabacusOnDeviceConnectionSuccess', (e) => {res(e)})
        DeviceEventEmitter.addListener('FabacusOnDeviceConnectionFail', (e) => {res(e)})

    }

    /**
     * RSSI power signal RFID tag value changed handle event
     * @param res
     */
    static onRSSIValuechanged(res){
        DeviceEventEmitter.addListener('FabacusOnRSSINewValue', (e) => {res(e)})
    }

    /**
     * Reset the inventory hash array.
     */
    static resetInventory(){
        RNReactNativeRfid.resetInventory();
    }

    /**
     * Start/Stop RDIF tags scan
     * @param active bool
     */
    static activateScan(active){
        RNReactNativeRfid.activateScan(active)
    }

}




module.exports = RFID;