package com.github.jbladejs.iot

interface Driver {
    fun getLightIntensity() : Double
    fun getEnergyUsage() : Double
    fun isLightOn() : Boolean
    fun turnOnTheLight()
    fun turnOffTheLight()
}