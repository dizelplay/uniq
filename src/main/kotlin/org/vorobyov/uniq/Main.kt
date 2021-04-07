package org.vorobyov.uniq

fun main(args: Array<String>) {
    val parser = Parser().apply { this.parse(args) }

    if (parser.file?.isFile == false) {
        error("Входной файл с именем ${parser.file} не найден!")
    }

    val input = parser.file?.bufferedReader() ?: System.`in`.bufferedReader()
    val output = parser.output?.bufferedWriter() ?: System.out.bufferedWriter()

    val uniq = Uniq(
        input = input,
        output = output,
        printOnlyUnique = parser.unique,
        ignoreCase = parser.ignoreCase,
        skipChars = parser.skipChars,
        printCount = parser.count
    )

    uniq.process()

}