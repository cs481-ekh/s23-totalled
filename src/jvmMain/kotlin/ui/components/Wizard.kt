package ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import ui.screens.SelectInputScreen

/**
 * Screens accessed from a Wizard should extend WizardScreen
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Wizard() {
    val wizardData = rememberSaveable { TotalledInputData() }
    Navigator(SelectInputScreen(wizardData)) { navigator ->
        val currentScreen = navigator.lastItem as WizardScreen
        Scaffold(
            content = {
                Column {
                    AnimatedContent(
                        targetState = currentScreen.step,
                        transitionSpec = {
                            fadeIn(spring(stiffness = Spring.StiffnessMediumLow)) with
                                fadeOut(spring(stiffness = Spring.StiffnessMediumLow))
                        },

                    ) { currentStep ->
                        // Hide the header if the screen's current step is negative
                        if (currentStep >= 0) {
                            ScreenHeader(currentScreen.title, currentScreen.step)
                        }
                    }
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

open class WizardData

class TotalledInputData : WizardData() {
    var expenseLogPath1 by mutableStateOf("")
    var expenseLogPath2 by mutableStateOf("")
    var projectBookPath by mutableStateOf("")
    var outputDirPath by mutableStateOf("")
}
