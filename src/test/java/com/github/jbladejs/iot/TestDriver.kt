package com.github.jbladejs.iot

import kotlin.random.Random

class TestDriver : Driver {
    private var increasing = true
    private var breakdownFactor = 0
    private var time = 5
    private var light: Boolean = true
    override val lightIntensity: Double
            get() {
                if (increasing) {
                    time += 5
                    if (time >= 180) increasing = false
                } else {
                    time -= 5
                    if (time <= 10) increasing = true
                }
                return Random.nextDouble(time.toDouble(), time.toDouble() + 20.0)
            }
    override val energyUsage: Double
            get() {
                breakdownFactor++
                return if (light)
                    if (Random.nextInt(0,breakdownFactor) <= 100) Random.nextDouble(50.0, 60.0)
                    else {
                        breakdownFactor = 0
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

