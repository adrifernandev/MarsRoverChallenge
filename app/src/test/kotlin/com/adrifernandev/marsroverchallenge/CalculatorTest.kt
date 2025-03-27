package com.adrifernandev.marsroverchallenge

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {
    @Test
    fun testAdd() {
        val calculator = Calculator()
        val result = calculator.add(2, 3)
        assertEquals(5, result)
    }
}