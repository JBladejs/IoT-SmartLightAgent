package com.github.jbladejs.iot

import com.github.jbladejs.iot.tools.Data
import kotlin.random.Random


class StreetLight(connectionString: String, private val driver: Driver) {
    private val connector = HubConnector(connectionString)

    fun sendMessages(number: Int, interval: Long) {
        println("Sending messages...")
        for (i in 1..number) {
            connector.sendMessage(Data(driver.getEnergyUsage(), driver.getEnergyUsage()), interval)
        }
        println("Messages successfully sent!")
    }

    fun closeHubConnection() {
        connector.closeConnection()
    }

}