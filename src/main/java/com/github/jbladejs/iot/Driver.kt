package com.github.jbladejs.iot

interface Driver {
    fun getLightIntensity() : Int
    fun getEnergyUsage() : Int
    fun turnOnTheLight()
    fun turnOffTheLight()
    fun isLightOn() : Boolean
}