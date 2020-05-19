package com.github.jbladejs.iot.tools

import java.io.File

internal object FileHandler {
    fun getLinesArrayFromFile(fileName: String) : List<String> = File(fileName).useLines { it.toList() }
}