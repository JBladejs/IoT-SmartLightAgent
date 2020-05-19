package com.github.jbladejs.iot

import com.google.gson.Gson
import com.microsoft.azure.sdk.iot.device.DeviceClient
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback
import com.microsoft.azure.sdk.iot.device.Message
import java.lang.Exception
import kotlin.random.Random
import kotlinx.coroutines.*
import kotlin.system.*



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

    fun sendMessages(number: Int, delay: Long) {
        println("Sending messages...")
        runBlocking {
            for (i in 1..number) {
                sendData(Data(Random.nextInt(50,60), Random.nextInt(0, 200)), delay)
            }
        }
        println("Messages successfully sent!")
    }

    suspend fun sendData(data: Data, delay: Long) {
        val message = Gson().toJson(data)
        val eventMessage = Message(message)
        println("Sending message: $message")
        eventMessage.setProperty("LightIntensityAlert", if (data.lightIntensity > 100) "true" else "false")
        client.sendEventAsync(eventMessage, null, null)

        delay(delay)
    }

    fun closeConnection() = client.closeNow()
}