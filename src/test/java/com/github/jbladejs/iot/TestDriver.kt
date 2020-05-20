package com.github.jbladejs.iot

import kotlin.random.Random

class TestDriver : Driver {
    private var light: Boolean = false
    override val lightIntensity: Double
            get() = Random.nextDouble(0.0,200.0)
    override val energyUsage: Double
            get() = if (light) Random.nextDouble(50.0,60.0) else Random.nextDouble(0.0,1.0)
    override fun isLightOn(): Boolean = light
    override fun turnOnTheLight() {
        light = true
    }
    override fun turnOffTheLight() {
        light = false
    }
}

