package io.github.llh4github.sw3convert.core.convert

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * Created At 2023/12/24 17:13
 * @author llh
 */
class ApiResponsesConvertTest : BaseTest(){
    init {
        testcase.put("""
public class Demo{
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "没权限"),
    })
    public void a(){}
}
    """.trimIndent(),"""
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public class Demo {

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "401", description = "没权限") })
    public void a() {
    }
}

    """.trimIndent())
        //
        testcase.put("""
public class Demo{
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "没权限"),
    })
    public void a(){}
}
    """.trimIndent(),"""
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public class Demo {

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "401", description = "没权限") })
    public void a() {
    }
}

    """.trimIndent())
        //
    }

    @Test
    fun test(){
      runConvertTest { ApiResponsesConvert(it).convert() }
    }
}