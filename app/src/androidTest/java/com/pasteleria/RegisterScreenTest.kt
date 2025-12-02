package com.pasteleria

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pasteleria.register.BakeryRegisterScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_muestra_elementos() {
        composeTestRule.setContent {
            BakeryRegisterScreen(navController = rememberNavController())
        }

        composeTestRule.onNodeWithText("Crea tu Cuenta").assertExists()
        composeTestRule.onNodeWithText("Registrar").assertExists()
    }
}
