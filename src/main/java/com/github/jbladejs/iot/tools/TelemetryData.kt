package com.github.jbladejs.iot.tools

import com.google.gson.Gson

internal data class TelemetryData(val energyUsage: Double, val lightIntensity: Double, val isLightOn: Boolean) {
    fun serialize() : String = Gson().toJson(this)
}