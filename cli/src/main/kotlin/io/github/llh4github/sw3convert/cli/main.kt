package io.github.llh4github.sw3convert.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import io.github.llh4github.sw3convert.core.dto.ConvertParams
import io.github.llh4github.sw3convert.core.fileoperate.JavaFileIterAndConvert
import kotlin.io.path.absolutePathString

/**
 *
 *
 * Created At 2023/12/25 10:17
 * @author llh
 */
class Sw3 : CliktCommand() {
    private val source by argument("原工程目录").path()
    override fun run() {
        JavaFileIterAndConvert.convertJavaFile(ConvertParams(source.absolutePathString()))
    }
}

fun main(args: Array<String>) {
    Sw3().main(args)
}
