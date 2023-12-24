package io.github.llh4github.sw3convert.core.convert

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * Created At 2023/12/24 16:08
 * @author llh
 */
class ApiOperationToOperationTest : BaseTest(){
  init {
      testcase.put("""
public class IndexController {

    @ApiOperation("get now time")
    @GetMapping("now")
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
    """.trimIndent(),"""
import io.swagger.v3.oas.annotations.Operation;

public class IndexController {

    @GetMapping("now")
    @Operation(summary = "get now time")
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
    """.trimIndent())
      //
  }
    @Test
    fun test(){
        runConvertTest { ApiOperationToOperation(it).convert() }
    }
}