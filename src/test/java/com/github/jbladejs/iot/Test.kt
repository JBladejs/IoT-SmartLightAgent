package com.github.jbladejs.iot

fun main() {
    val light = StreetLight(FileHandler.getLinesArrayFromFile("devices_connection_strings.txt")[0])
}