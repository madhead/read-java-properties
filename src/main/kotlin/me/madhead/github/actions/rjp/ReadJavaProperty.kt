package me.madhead.github.actions.rjp

import java.io.FileInputStream
import java.util.Properties

fun main(args: Array<String>) {
    val file = args[0]
    val property = args[1]
    val default = args[2]

    val value =
        try {
            FileInputStream(file).use {
                val properties = Properties()

                properties.load(it)
                properties.getProperty(property) ?: throw IllegalArgumentException("$property (No such property)")
            }
        } catch (e: Exception) {
            default.takeIf { it.isNotEmpty() } ?: throw e
        }

    println("::set-output name=value::$value")
}
