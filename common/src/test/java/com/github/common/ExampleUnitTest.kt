package com.github.common

import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testBoolean() {
        val otherwiseResult = false.yes {
            1
        }.otherwise {
            2
        }
        Assert.assertEquals(otherwiseResult, 2)
        val yesResult = true.yes {
            1
        }.otherwise {
            2
        }
        Assert.assertEquals(yesResult, 1)
    }
}