package com.github.jbladejs.iot

import com.google.gson.Gson
import com.microsoft.azure.sdk.iot.device.*
import java.lang.IllegalArgumentException
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.random.Random


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

    fun sendMessages(number: Int, interval: Long) {
        println("Sending messages...")
        for (i in 1..number) {
            sendMessage(Data(Random.nextInt(50,60), Random.nextInt(0, 200)), interval)
        }
        println("Messages successfully sent!")
    }

    fun sendMessage(data: Data, interval: Long) {
        val message = Gson().toJson(data)
        val eventMessage = Message(message)
        println("Sending message: $message")
        eventMessage.setProperty("LightIntensityAlert", if (data.lightIntensity > 100) "true" else "false")
        val lock = ReentrantLock()
        val condition = lock.newCondition()
        client.sendEventAsync(eventMessage, EventCallback, lock)
        lock.withLock { condition.await() }
        Thread.sleep(interval)
    }

    fun closeConnection() = client.closeNow()

    private object EventCallback : IotHubEventCallback {
        override fun execute(status: IotHubStatusCode, context: Any) {
            println("IoT Hub responded to message with status: " + status.name)
            if (context !is ReentrantLock) throw IllegalArgumentException()
            val condition = context.newCondition()
            synchronized(context) { condition.signal() }
        }
    }

}