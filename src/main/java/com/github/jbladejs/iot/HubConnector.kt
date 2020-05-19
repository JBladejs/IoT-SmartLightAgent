package com.github.jbladejs.iot

import com.github.jbladejs.iot.tools.TelemetryData
import com.github.jbladejs.iot.tools.LockObject
import com.google.gson.Gson
import com.microsoft.azure.sdk.iot.device.*

internal class HubConnector(connectionString: String) {
    private var client = DeviceClient(connectionString, IotHubClientProtocol.MQTT)

    init{
        println("Starting device...")
        try {
            client.open()
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

    fun closeConnection() = client.closeNow()

    private object EventCallback : IotHubEventCallback {
        override fun execute(status: IotHubStatusCode, context: Any) {
            println("IoT Hub responded to message with status: " + status.name)
            val lock = context as LockObject
            lock.signal()
        }
    }
}