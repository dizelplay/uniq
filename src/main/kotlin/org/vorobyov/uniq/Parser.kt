package org.vorobyov.uniq

import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.File

class Parser {
    @Argument(usage = "Имя входного файла. Если не указано, ввод осуществляется с консоли")
    var file: File? = null

    @Option(name = "-o", usage = "Имя выходного файла. Если не указано, вывод осуществляется на консоль")
    var output: File? = null

    @Option(name = "-i", usage = "При сравнении строк следует не учитывать регистр символов")
    var ignoreCase: Boolean = false

    @Option(name = "-s", usage = "При сравнении строк следует игнорировать первые N символов каждой строки")
    var skipChars: Int = 0

    @Option(name = "-u", usage = "Выводить в качестве результата только уникальные строки")
    var unique: Boolean = false

    @Option(name = "-c", usage = "Перед каждой строкой вывода следует вывести количество строк, которые были заменены данной")
    var count: Boolean = false

    fun parse(args: Array<String>) {
        val parser = CmdLineParser(this)
        parser.parseArgument(args.toList())
    }

}