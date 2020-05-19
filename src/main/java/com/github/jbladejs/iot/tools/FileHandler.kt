package com.github.jbladejs.iot.tools

import java.io.File

object FileHandler {
    fun getLinesArrayFromFile(fileName: String) : List<String> = File(fileName).useLines { it.toList() }
}