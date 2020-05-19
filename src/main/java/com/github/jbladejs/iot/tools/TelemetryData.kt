package com.github.jbladejs.iot.tools

import com.google.gson.Gson

internal data class TelemetryData(val energyUsage: Int, val lightIntensity: Int) {
    fun serialize() : String = Gson().toJson(this)
}