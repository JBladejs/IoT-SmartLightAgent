package com.github.jbladejs.iot.hub;

import com.github.jbladejs.iot.StreetLight;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodCallback;
import com.microsoft.azure.sdk.iot.device.DeviceTwin.DeviceMethodData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class DirectMethodCallback implements DeviceMethodCallback
{
    private static final int METHOD_SUCCESS = 200;
    private static final int METHOD_NOT_DEFINED = 404;
    private static final int INVALID_PARAMETER = 400;

    private StreetLight device;

    public DirectMethodCallback(StreetLight device){
        this.device = device;
    }

    private void turnOnLight() {
        device.turnOn();
    }

    private void turnOffLight() {
        device.turnOff();
    }

    @Override
    public DeviceMethodData call(String methodName, Object methodData, Object context)
    {
        DeviceMethodData deviceMethodData;
        //String payload = new String((byte[])methodData);
        System.out.println("test");
        switch (methodName) {
            case "TurnOn":
                    turnOnLight();
                    deviceMethodData = new DeviceMethodData(METHOD_SUCCESS, "Executed direct method " + methodName);
                break;
            case "TurnOff":
                turnOffLight();
                deviceMethodData = new DeviceMethodData(METHOD_SUCCESS, "Executed direct method " + methodName);
                break;
            default:
                System.err.println("Undefined direct method called!");
                deviceMethodData = new DeviceMethodData(METHOD_NOT_DEFINED, "Not defined direct method " + methodName);
                break;
        }
        return deviceMethodData;
    }
}
