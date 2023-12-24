package io.github.llh4github.sw3convert.core.convert

import org.junit.jupiter.api.Test

/**
 *
 * Created At 2023/12/24 16:48
 * @author llh
 */
class ApiImplicitParamToParameterTest : BaseTest() {
    init {
        testcase.put(
            """
public class Demo {
    
    @ApiImplicitParam(name = "id", value = "角色id")
    public void get(int id){}
}
    """.trimIndent(), """
import io.swagger.v3.oas.annotations.Parameter;

public class Demo {

    @Parameter(name = "id", description = "角色id")
    public void get(int id) {
    }
}
    """.trimIndent()
        )
    }

    @Test
    fun test() {
        runConvertTest { ApiImplicitParamToParameter(it).convert() }
    }
}