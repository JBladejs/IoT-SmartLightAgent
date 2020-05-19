package com.github.jbladejs.iot.hub

import com.github.jbladejs.iot.StreetLight
import com.github.jbladejs.iot.tools.LockObject
import com.github.jbladejs.iot.tools.TelemetryData
import com.microsoft.azure.sdk.iot.device.*
import com.microsoft.azure.sdk.iot.device.DeviceTwin.Property


internal class HubConnector(connectionString: String, device : StreetLight) {
    private var client = DeviceClient(connectionString, IotHubClientProtocol.MQTT)
    private val dataCollector = DataCollector(this)

    init{
        println("Starting device...")
        try {
            client.open()
            client.subscribeToDeviceMethod(DirectMethodCallback(device), null, DirectMethodStatusCallback, null)
            client.startDeviceTwin(DeviceTwinStatusCallback(), null, dataCollector, null)
        } catch (ex: Exception) {
            error("Error when starting device!")
            ex.printStackTrace()
        }
        println("Device successfully started!")
    }

    fun sendMessage(data: TelemetryData, interval: Long) {
        val message = data.serialize()
        val eventMessage = Message(message)
        println("Sending message: $message")
        eventMessage.setProperty("LightOn", if (data.lightIntensity > 100) "true" else "false")
        val lock = LockObject()
        client.sendEventAsync(eventMessage, EventCallback, lock)
        lock.await()
        Thread.sleep(interval)
    }

    fun closeConnection() {
        dataCollector.clean()
        client.closeNow()
    }

    fun changeProperty(propertyName: String, value: Any) {
        dataCollector.setReportedProp(Property(propertyName, value))
        client.sendReportedProperties(dataCollector.reportedProp)
    }

    private object EventCallback : IotHubEventCallback {
        override fun execute(status: IotHubStatusCode, context: Any) {
            println("IoT Hub responded to message with status: " + status.name)
            val lock = context as LockObject
            lock.signal()
        }
    }

    private object DirectMethodStatusCallback : IotHubEventCallback {
        override fun execute(status: IotHubStatusCode, context: Any) {
            println("Direct method # IoT Hub responded to device method acknowledgement with status: " + status.name)
        }
    }
}