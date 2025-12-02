package com.pasteleria

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pasteleria.ui.boleta.BoletaScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BoletaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun boletaScreen_muestra_elementos() {
        composeTestRule.setContent {
            BoletaScreen(
                navController = rememberNavController(),
                boletaViewModel = viewModel()
            )
        }

        composeTestRule.onNodeWithText("Mi Carrito").assertExists()
    }
}
