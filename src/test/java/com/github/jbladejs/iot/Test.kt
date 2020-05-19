package com.github.jbladejs.iot

import com.github.jbladejs.iot.tools.FileHandler

fun main() {
    val light = StreetLight(FileHandler.getLinesArrayFromFile("devices_connection_strings.txt")[0], TestDriver())
    readLine()
    //light.sendMessages(5, 1000L)
    light.closeHubConnection()
}