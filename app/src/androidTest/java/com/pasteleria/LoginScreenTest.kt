package com.pasteleria

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pasteleria.login.BakeryLoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_muestra_elementos() {
        composeTestRule.setContent {
            BakeryLoginScreen(navController = rememberNavController())
        }

        composeTestRule.onNodeWithText("¡Dulce Bienvenida!").assertExists()
        composeTestRule.onNodeWithText("Ingresar").assertExists()
    }
}
