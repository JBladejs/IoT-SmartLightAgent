package com.github.jbladejs.iot.hub;

import com.microsoft.azure.sdk.iot.device.DeviceTwin.Device;

class DataCollector extends Device {
    HubConnector connector;

    public DataCollector(HubConnector connector) {
        this.connector = connector;
    }

    @Override
    public void PropertyCall(String propertyKey, Object propertyValue, Object context) {
        System.out.println(propertyKey + " changed to " + propertyValue);
        switch(propertyKey) {
            case "mode":
                boolean value = switch((String) propertyValue) {
                    case "auto", "automatic" -> true;
                    case "manual" -> false;
                    default -> throw new IllegalArgumentException("Unexpected value from DeviceTwin: " + propertyValue + " for property " + propertyKey);
                };
                connector.getDevice().setAutomaticMode(value);
                break;
            case "lightTopLimit":
                try {
                    connector.getDevice().setLightTopLimit((double) propertyValue);
                } catch (NumberFormatException e) {
                    System.err.println("Błedny argument dla " + propertyKey + " przekazany przez DeviceTwin!");
                }
                break;
            case "lightBottomLimit":
                try {
                    connector.getDevice().setLightBottomLimit((double) propertyValue);
                } catch (NumberFormatException e) {
                    System.err.println("Błedny argument dla " + propertyKey + " przekazany przez DeviceTwin!");
                }
                break;
            default:
                System.err.println("Undefined property changed!");
        }
    }
}