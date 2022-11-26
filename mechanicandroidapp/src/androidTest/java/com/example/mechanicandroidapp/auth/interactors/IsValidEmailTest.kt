package com.example.mechanicandroidapp.auth.interactors

import com.example.automobile.auth.interactors.IsValidEmail
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IsValidEmailTest {

    private var isValidEmail: IsValidEmail = IsValidEmail()

    @Before
    fun setUp(){
        isValidEmail = IsValidEmail()
    }

    @Test
    fun correctEmailFormat_returnsTrue(){
        Assert.assertEquals(true, isValidEmail("correct@email.com"))
    }

    @Test
    fun emailWithoutDot_returnsFalse(){
        Assert.assertEquals(false, isValidEmail("correct@emailcom"))
    }

    @Test
    fun emailWithoutAt_returnsFalse(){
        Assert.assertEquals(false, isValidEmail("correctemail.com"))
    }

    @Test
    fun emailOver254Characters_returnsFalse(){
        //255 character email
        val longEmail = """cool-english-alphabet-loverer-abcdefghijklmnopqrstuvwxyz@try-to.send-
            |me-an-email-if-you-can-possibly-begin-to-remember-this-coz.this-is-the-longest-email-
            |address-known-to-man-but-to-bee-honest.this-is-such-a-stupidly-long-sub-domain-it
            |-forever.pacraig.com""".trimMargin()
        Assert.assertEquals(false, isValidEmail(longEmail))
    }

    @Test
    fun emailWithoutUsername_returnsFalse(){
        Assert.assertEquals(false, isValidEmail("@email.com"))
    }

    @Test
    fun emailWithoutDomainName_returnsFalse(){
        Assert.assertEquals(false, isValidEmail("correct@.com"))
    }

    @Test
    fun emailWithoutDomainRegister_returnsFalse(){
        Assert.assertEquals(false, isValidEmail("correct@email."))
    }


    @Test
    fun emptyEmail_returnsFalse(){
        Assert.assertEquals(false, isValidEmail(""))
    }
}