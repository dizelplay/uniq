package org.vorobyov.uniq

import java.io.BufferedReader
import java.io.BufferedWriter

class Uniq(
    private val input: BufferedReader,
    private val output: BufferedWriter,
    private val printOnlyUnique: Boolean = false,
    private val ignoreCase: Boolean = false,
    private val skipChars: Int = 0,
    private val printCount: Boolean = false
) {

    // предыдущая считанная строка
    private var buffer: String = ""

    // количество повторений буфера
    private var bufferCount = 0


    fun process() {
        input.forEachLine { line ->
            if (bufferCount == 0) {
                buffer = line
                bufferCount = 1
            } else {
                if (buffer.equalsWithRules(line)) {
                    bufferCount++
                } else {
                    if (printOnlyUnique && bufferCount == 1 || !printOnlyUnique) writeWithRules()

                    buffer = line
                    bufferCount = 1
                }
            }
        }

        if (printOnlyUnique && bufferCount == 1 || !printOnlyUnique && bufferCount > 0) writeWithRules()
        output.flush()
    }

    private fun writeWithRules() {
        if (printCount) output.append("$bufferCount ")
        output.appendLine(buffer)
    }

    private fun String.equalsWithRules(other: String): Boolean =
        if (skipChars != 0) {
            when {
                (this.length <= skipChars && other.length <= skipChars) -> true
                this.length <= skipChars || other.length <= skipChars -> false
                else -> this.substring(skipChars).equals(other.substring(skipChars), ignoreCase)
            }
        } else this.equals(other, ignoreCase)
}