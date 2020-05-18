package com.github.jbladejs.iot

import com.microsoft.azure.sdk.iot.device.DeviceClient
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol
import java.lang.Exception

class StreetLight(private val connectionString: String) {
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

    fun closeConnection() = client.closeNow()
}