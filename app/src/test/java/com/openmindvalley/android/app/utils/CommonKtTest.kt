package com.openmindvalley.android.app.utils

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CommonKtTest {
    @Test
    fun testIsNotNullOrEmpty_withNull() {
        val input: CharSequence? = null
        assertFalse(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withEmpty() {
        val input: CharSequence? = ""
        assertFalse(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withNonEmptyString() {
        val input: CharSequence? = "Hello, World!"
        assertTrue(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withWhitespace() {
        val input: CharSequence? = "   "
        assertTrue(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withNullCollection() {
        val input: Collection<Int>? = null
        assertFalse(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withEmptyCollection() {
        val input: Collection<Int>? = emptyList()
        assertFalse(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withNonEmptyCollection() {
        val input: Collection<Int>? = listOf(1, 2, 3)
        assertTrue(input.isNotNullOrEmpty())
    }

    @Test
    fun testIsNotNullOrEmpty_withSingleElementCollection() {
        val input: Collection<Int>? = listOf(42)
        assertTrue(input.isNotNullOrEmpty())
    }
}