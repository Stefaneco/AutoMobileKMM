package com.example.mechanicandroidapp.auth

import android.content.res.Resources
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.automobile.network.MockApiEngine
import com.example.mechanicandroidapp.MainActivity
import com.example.mechanicandroidapp.ui.theme.AutoMobileTheme
import com.example.sharedandroid.auth.login.LoginScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.ktor.http.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.automobile.R
import com.example.sharedandroid.ui.Tags


@HiltAndroidTest
class LoginScreenTest {

    private val res: Resources = InstrumentationRegistry.getInstrumentation().targetContext.resources

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        MockApiEngine.givenSuccess()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            AutoMobileTheme {
                LoginScreen(navController = navController)
            }
        }
    }

    @Test
    fun openScreen_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.login)).assertExists()
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsNotEnabled()
    }

    @Test
    fun insertCorrectEmailAndPassword_loginButtonClickable(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsEnabled()
    }

    @Test
    fun insertCorrectEmailAndIncorrectPassword_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsNotEnabled()
    }

    @Test
    fun insertIncorrectEmailAndCorrectPassword_loginButtonNotClickable(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("12345678")
        composeRule.onNodeWithText(res.getString(R.string.login)).assertIsNotEnabled()
    }

    @Test
    fun insertIncorrectEmail_emailErrorDisplayed(){
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performClick()
        composeRule.onNodeWithText(res.getString(R.string.incorrect_email)).assertIsDisplayed()
    }

    @Test
    fun insertIncorrectPassword_passwordErrorDisplayed(){
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithText(res.getString(R.string.email)).performClick()
        composeRule.onNodeWithText(res.getString(R.string.incorrect_password)).assertIsDisplayed()
    }

    @Test
    fun insertPassword_insertedPasswordIsHidden(){
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456")
        composeRule.onNodeWithTag(Tags.TAG_PASSWORD_HIDDEN).assertExists()
    }

    @Test
    fun apiThrowsBadRequestException_errorMessageIsDisplayed(){
        MockApiEngine.givenFailure(HttpStatusCode.BadRequest)
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.login)).performClick()
        composeRule.onNodeWithText(res.getString(R.string.bad_request)).assertIsDisplayed()
    }

    @Test
    fun apiThrowsInternalServerException_errorMessageIsDisplayed(){
        MockApiEngine.givenFailure(HttpStatusCode.InternalServerError)
        composeRule.onNodeWithText(res.getString(R.string.email)).performTextInput("test@mail.com")
        composeRule.onNodeWithText(res.getString(R.string.password)).performTextInput("123456789")
        composeRule.onNodeWithText(res.getString(R.string.login)).performClick()
        composeRule.onNodeWithText(res.getString(R.string.internal_server_error)).assertIsDisplayed()
    }
}