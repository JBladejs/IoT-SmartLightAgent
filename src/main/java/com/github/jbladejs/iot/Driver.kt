package com.github.jbladejs.iot

interface Driver {
    val lightIntensity : Double
    val energyUsage : Double
    fun isLightOn() : Boolean
    fun turnOnTheLight()
    fun turnOffTheLight()
}