package com.github.jbladejs.iot

import com.github.jbladejs.iot.hub.HubConnector
import com.github.jbladejs.iot.tools.TelemetryData
import java.util.concurrent.Executors

class StreetLight(connectionString: String, private val driver: Driver) {
    private val connector = HubConnector(connectionString, this)
    private var automaticMode = true
    private val executor = Executors.newFixedThreadPool(1)
    private val runner = SoftwareRunner(this, 1000L)

    init {
        executor.execute(runner)
    }

    private class SoftwareRunner(device: StreetLight, val interval: Long) : Runnable {
        val driver = device.driver
        val connector = device.connector
        var shuttingDown = false

        override fun run() {
            while(true){
                try {
                    connector.sendMessage(TelemetryData(driver.energyUsage, driver.lightIntensity), interval)
                }
                catch (e: InterruptedException) {
                    if (shuttingDown) {
                        println("Shutdown correctly.")
                        break
                    } else error("Critical error has ben encountered!")

                }
            }
        }
    }

    fun closeHubConnection() {
        runner.shuttingDown = true
        executor.shutdownNow()
        connector.closeConnection()
    }
}