package com.github.jbladejs.iot.hub;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.Device;

public class DataCollector extends Device {
    HubConnector connector;

    public DataCollector(HubConnector connector) {
        this.connector = connector;
    }

    @Override
    public void PropertyCall(String propertyKey, Object propertyValue, Object context) {
        System.out.println(propertyKey + " changed to " + propertyValue);
        connector.changeProperty(propertyKey, propertyValue);
    }
}