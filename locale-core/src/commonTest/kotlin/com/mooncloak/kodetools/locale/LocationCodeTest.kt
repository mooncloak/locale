package com.mooncloak.kodetools.locale

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocationCodeTest {

    @Test
    fun `empty value throws illegal argument exception`() {
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke("")
        }
    }

    @Test
    fun `blank value throws illegal argument exception`() {
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke(" ")
        }
    }

    @Test
    fun `one character throws illegal argument exception`() {
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke("a")
        }
    }

    @Test
    fun `three character country code throws illegal argument exception`() {
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke("aaa")
        }
    }

    @Test
    fun `two character country code with numbers throws illegal argument exception`() {
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke("11")
        }
    }

    @Test
    fun `region code value must start with two character country code`(){
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke("USA-NY")
        }
    }

    @Test
    fun `region code value must contain only characters for the country code part`(){
        assertFailsWith(IllegalArgumentException::class) {
            LocationCode.invoke("11-NY")
        }
    }

    @Test
    fun `valid country code value is converted to upper case`() {
        val code = LocationCode.invoke("us")

        assertEquals(expected = "US", actual = code.value)
    }

    @Test
    fun `valid region code value is converted to upper case`() {
        val code = LocationCode.invoke("us-ny")

        assertEquals(expected = "US-NY", actual = code.value)
    }
}
