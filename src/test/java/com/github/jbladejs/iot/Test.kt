package com.github.jbladejs.iot

import com.github.jbladejs.iot.tools.FileHandler

fun main() {
    val light = StreetLight(FileHandler.getLinesArrayFromFile("devices_connection_strings.txt")[0], TestDriver())
    println("Press enter to shutdown operation.")
    readLine()
    light.closeHubConnection()
}