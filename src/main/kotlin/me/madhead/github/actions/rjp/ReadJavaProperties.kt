package me.madhead.github.actions.rjp

import java.io.FileInputStream
import java.lang.System.getenv
import java.lang.System.lineSeparator
import java.util.Properties
import kotlin.io.path.Path
import kotlin.io.path.appendText

fun main(args: Array<String>) {
    val file = args[0]
    val property = args[1]
    val all = args[2]
    val default = args[3]
    val githubOutput = Path(getenv("GITHUB_OUTPUT"))

    try {
        FileInputStream(file).use { fis ->
            val properties = Properties()

            properties.load(fis)

            if ("true".equals(all, ignoreCase = true)) {
                properties.entries.forEach { (key, value) ->
                    githubOutput.appendText("$key=$value${lineSeparator()}")
                }
            } else {
                properties.getProperty(property)?.let { value ->
                    githubOutput.appendText("value=$value${lineSeparator()}")
                } ?: run {
                    githubOutput.appendText("value=${default.takeIf { it.isNotEmpty() } ?: throw IllegalArgumentException("$property (No such property)")}${lineSeparator()}")
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()

        if ("true".equals(all, ignoreCase = true)) {
            throw e
        } else {
            githubOutput.appendText("value=${default.takeIf { it.isNotEmpty() } ?: throw e}${lineSeparator()}")
        }
    }

}
