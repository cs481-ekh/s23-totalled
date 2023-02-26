package ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import ui.screens.SelectInputScreen

/**
 * Screens accessed from a Wizard should extend WizardScreen
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Wizard() {
    Navigator(SelectInputScreen()) { navigator ->
        val currentScreen = navigator.lastItem as WizardScreen
        Scaffold(
            content = {
                Column {
                    ScreenHeader(currentScreen.title, currentScreen.step)
                    FadeTransition(navigator)
                }
            },
            bottomBar = {
                BottomNavBar(
                    onClickNext = { currentScreen.onClickNext(navigator) },
                    onClickBack = { currentScreen.onClickBack(navigator) },
                    nextEnabled = { currentScreen.nextEnabled },
                    backEnabled = { currentScreen.backEnabled },
                    nextButtonText = currentScreen.nextButtonText,
                    backButtonText = currentScreen.backButtonText,
                )
            },
        )
    }
}
