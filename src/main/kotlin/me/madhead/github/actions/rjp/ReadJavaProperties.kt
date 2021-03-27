package me.madhead.github.actions.rjp

import java.io.FileInputStream
import java.util.Properties

fun main(args: Array<String>) {
    val file = args[0]
    val property = args[1]
    val all = args[2]
    val default = args[3]

    try {
        FileInputStream(file).use { fis ->
            val properties = Properties()

            properties.load(fis)

            if ("true".equals(all, ignoreCase = true)) {
                properties.entries.forEach { (key, value) ->
                    println("::set-output name=$key::$value")
                }
            } else {
                properties.getProperty(property)?.let { value ->
                    println("::set-output name=value::$value")
                } ?: run {
                    println("::set-output name=value::${default.takeIf { it.isNotEmpty() } ?: throw IllegalArgumentException("$property (No such property)")}")
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()

        if ("true".equals(all, ignoreCase = true)) {
            throw e
        } else {
            println("::set-output name=value::${default.takeIf { it.isNotEmpty() } ?: throw e}")
        }
    }
}
