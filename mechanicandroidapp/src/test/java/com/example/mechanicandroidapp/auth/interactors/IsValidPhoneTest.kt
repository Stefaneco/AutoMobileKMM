package com.example.mechanicandroidapp.auth.interactors

import com.example.automobile.auth.interactors.IsValidPhone
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IsValidPhoneTest {

    private lateinit var isValidPhone : IsValidPhone

    @Before
    fun setUp(){
        isValidPhone = IsValidPhone()
    }

    @Test
    fun phoneBelow6Characters_returnsFalse(){
        assertEquals(false, isValidPhone("12345"))
    }

    @Test
    fun phoneBetween6To17CharactersWithOnlyDigits_returnsTrue(){
        assertEquals(true, isValidPhone("12345678"))
    }

    @Test
    fun phoneWithSpaces_returnsFalse(){
        assertEquals(false, isValidPhone("12 3456 888"))
    }

    @Test
    fun phoneWithNoneDigitCharacters_returnsFalse(){
        assertEquals(false, isValidPhone("123456I888"))
    }
}