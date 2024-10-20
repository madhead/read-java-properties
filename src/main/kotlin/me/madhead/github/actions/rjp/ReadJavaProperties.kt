package me.madhead.github.actions.rjp

import java.io.FileInputStream
import java.lang.System.getenv
import java.util.Properties
import kotlin.io.path.Path
import kotlin.io.path.appendText
import kotlin.io.path.useLines

private val newLine = System.lineSeparator()

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
                properties.every { key, value ->
                    githubOutput.appendText(packKeyValue(key, value))
                }
            } else {
                properties.getProperty(property)?.let { value ->
                    githubOutput.appendText(packKeyValue("value", value))
                } ?: run {
                    githubOutput.appendText(
                        packKeyValue(
                            "value",
                            default.takeIf { it.isNotEmpty() } ?: throw IllegalArgumentException("$property (No such property)")
                        )
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()

        if ("true".equals(all, ignoreCase = true)) {
            throw e
        } else {
            githubOutput.appendText(packKeyValue("value", default.takeIf { it.isNotEmpty() } ?: throw e))
        }
    }
}

private fun Properties.every(action: (String, String) -> Unit) {
    for (element in this) action(element.key.toString(), element.value.toString())
}

private fun packKeyValue(key: String, value: String): String {
    if (value.isMultiline()) {
        var delimiter = "read-java-properties-delimiter"
        while (value.contains(delimiter)) {
            delimiter += "-x"
        }
        return "$key<<$delimiter$newLine$value$newLine$delimiter$newLine"
    } else {
        return "$key=$value$newLine"
    }
}

private fun String.isMultiline(): Boolean = this.split(Regex("^(.*)$", RegexOption.MULTILINE)).isNotEmpty()
