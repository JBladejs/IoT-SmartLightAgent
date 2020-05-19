package com.github.jbladejs.iot

import kotlin.random.Random

class TestDriver : Driver {
    private var light: Boolean = false
    override fun getLightIntensity(): Int = Random.nextInt(50,60)
    override fun getEnergyUsage(): Int = Random.nextInt(0,200)
    override fun isLightOn(): Boolean = light
    override fun turnOnTheLight() {
        light = true
    }
    override fun turnOffTheLight() {
        light = false
    }
}