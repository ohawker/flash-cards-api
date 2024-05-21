package com.hawklogica.flashcardsapi

object Util {

    fun readFile(filePath: String) =
        javaClass.getResource(filePath)!!.readText(Charsets.UTF_8)
}
