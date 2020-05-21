package com.github.jbladejs.iot

import kotlin.random.Random

class TestDriver : Driver {
    private var criticalStatus = false
    private var breakdownFactor = 0
    private var light: Boolean = true
    override val lightIntensity: Double
            get() = Random.nextDouble(0.0,200.0)
    override val energyUsage: Double
            get() {
                breakdownFactor++
                return if (light)
                    if (!criticalStatus && Random.nextInt(0,breakdownFactor) <= 100) Random.nextDouble(50.0, 60.0)
                    else {
                        criticalStatus = true
                        Random.nextDouble(80.0, 100.0)
                    }
                else
                    Random.nextDouble(0.0, 1.0)
            }
    override fun isLightOn(): Boolean = light
    override fun turnOnTheLight() {
        light = true
    }
    override fun turnOffTheLight() {
        light = false
    }
}

