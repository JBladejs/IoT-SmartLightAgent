package com.github.jbladejs.iot.hub;

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;

class DeviceTwinStatusCallback implements IotHubEventCallback {
    @Override
    public void execute(IotHubStatusCode status, Object context) {
        System.out.println("IoT Hub responded to device twin operation with status " + status.name());
    }
}
