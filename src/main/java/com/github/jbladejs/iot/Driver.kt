package com.github.jbladejs.iot

interface Driver {
    fun getLightIntensity() : Int
    fun getEnergyUsage() : Int
    fun isLightOn() : Boolean
    fun turnOnTheLight()
    fun turnOffTheLight()
}