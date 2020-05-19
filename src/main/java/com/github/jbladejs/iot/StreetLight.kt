package com.github.jbladejs.iot

import com.github.jbladejs.iot.hub.HubConnector
import com.github.jbladejs.iot.tools.TelemetryData

class StreetLight(connectionString: String, private val driver: Driver) {
    private val connector = HubConnector(connectionString, this)

    fun sendMessages(number: Int, interval: Long) {
        println("Sending messages...")
        for (i in 1..number) {
            connector.sendMessage(TelemetryData(driver.getEnergyUsage(), driver.getEnergyUsage()), interval)
        }
        println("Messages successfully sent!")
    }

    fun closeHubConnection() {
        connector.closeConnection()
    }

}