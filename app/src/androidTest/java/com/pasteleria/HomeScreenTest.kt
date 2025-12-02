package com.pasteleria

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pasteleria.ui.home.HomeScreem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_muestra_elementos() {
        composeTestRule.setContent {
            HomeScreem(username = "Juan", navController = rememberNavController())
        }

        composeTestRule.onNodeWithText("¡Hola, Juan!").assertExists()
        composeTestRule.onNodeWithText("Nuestros Productos").assertExists()
    }
}
