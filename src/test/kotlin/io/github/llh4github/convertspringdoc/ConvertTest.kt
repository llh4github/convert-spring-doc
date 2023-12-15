package io.github.llh4github.convertspringdoc

import com.github.javaparser.JavaParser
import com.github.javaparser.printer.YamlPrinter
import org.junit.jupiter.api.Test

/**
 *
 *
 * Created At 2023/12/15 11:09
 * @author llh
 */
class ConvertTest {

    val demo: String = """
@Api("首页模块")
@Api(tags = "首页模块")
@RestController
public class IndexController {

    @ApiImplicitParam(name = "name",value = "姓名",required = true)
    @ApiOperation(value = "向客人问好")
    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@RequestParam(value = "name")String name){
        return ResponseEntity.ok("Hi:"+name);
    }
}
    """

    @Test
    fun a() {
        val rs = JavaParser().parse(demo)
        val a = rs.result.get()
        val annos = a.types.flatMap { it.annotations }
            .toList()
        annos.forEach {

        }
        rs.result.ifPresent { YamlPrinter.print(it) }
    }
}