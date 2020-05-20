package com.github.jbladejs.iot

import com.github.jbladejs.iot.hub.HubConnector
import com.github.jbladejs.iot.tools.TelemetryData
import java.util.concurrent.Executors

class StreetLight(connectionString: String, private val driver: Driver) {
    private val connector : HubConnector
    private val executor = Executors.newFixedThreadPool(1)
    private val runner = SoftwareRunner(this, 1000L)
    var automaticMode = true
    var lightTopLimit = 110.0
    var lightBottomLimit = 100.0

    init {
        println("Starting device...")
        connector = HubConnector(connectionString, this)
        println("Device successfully started!")
        executor.execute(runner)
    }

    private class SoftwareRunner(val device: StreetLight, val interval: Long) : Runnable {
        val driver = device.driver
        val connector = device.connector
        var shuttingDown = false

        override fun run() {
            while(true){
                try {
                    val lightIntensity = driver.lightIntensity
                    if (device.automaticMode) {
                        if (driver.isLightOn() && lightIntensity > device.lightTopLimit) driver.turnOffTheLight()
                        else if (!driver.isLightOn() && lightIntensity < device.lightBottomLimit) driver.turnOnTheLight()
                    }
                    connector.sendMessage(TelemetryData(driver.energyUsage, lightIntensity, driver.isLightOn()), interval)
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

    fun turnOn() {
        automaticMode = false
        driver.turnOnTheLight()
    }

    fun turnOff() {
        automaticMode = false
        driver.turnOffTheLight()
    }

    fun closeHubConnection() {
        runner.shuttingDown = true
        executor.shutdownNow()
        connector.closeConnection()
    }
}