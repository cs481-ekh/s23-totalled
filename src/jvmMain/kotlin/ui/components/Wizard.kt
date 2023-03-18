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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition

/**
 * Screens accessed from a Wizard should extend WizardScreen
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Wizard(startScreen: WizardScreen, numberOfSteps: Int = 0) {
    Navigator(startScreen) { navigator ->
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
                            ScreenHeader(currentScreen.title, currentScreen.step, numberOfSteps)
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
