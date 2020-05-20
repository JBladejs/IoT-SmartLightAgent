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

    private void sendMessages(int number, int interval) {
        System.out.println("Direct method initiated # Sending messages: " + number);
    }

    @Override
    public DeviceMethodData call(String methodName, Object methodData, Object context)
    {
        DeviceMethodData deviceMethodData;
        String payload = new String((byte[])methodData);
        System.out.println("test");
        if ("SendMessages".equals(methodName)) {
            try {
                var data = (JSONObject) new JSONParser().parse(payload);
                sendMessages(((Long) data.get("number")).intValue(), ((Long) data.get("interval")).intValue());
                deviceMethodData = new DeviceMethodData(METHOD_SUCCESS, "Executed direct method " + methodName);
//                } catch (NumberFormatException | ParseException | Exception e) {
            } catch (Exception e) {
                System.err.println("Direct method initiated with an invalid parameter!");
                e.printStackTrace();
                deviceMethodData = new DeviceMethodData(INVALID_PARAMETER, "Invalid parameter " + payload);
            }
        } else {
            System.err.println("Undefined direct method called!");
            deviceMethodData = new DeviceMethodData(METHOD_NOT_DEFINED, "Not defined direct method " + methodName);
        }
        return deviceMethodData;
    }
}
