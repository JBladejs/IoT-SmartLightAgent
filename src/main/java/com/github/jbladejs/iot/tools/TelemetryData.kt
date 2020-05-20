package com.github.jbladejs.iot.tools

import com.google.gson.Gson

internal data class TelemetryData(val energyUsage: Double, val lightIntensity: Double) {
    fun serialize() : String = Gson().toJson(this)
}