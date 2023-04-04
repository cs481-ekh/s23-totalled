package ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Header for each screen
 * @param pageTitle Title text to display
 * @param currentStep Determines how filled the bar is, first step is 0
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenHeader(
    pageTitle: String,
    currentStep: Int,
    numberOfSteps: Int,
) {
    Row(modifier = Modifier.padding(top = 12.dp, bottom = 24.dp, start = 48.dp, end = 48.dp).fillMaxWidth()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AnimatedContent(
                targetState = pageTitle,
                transitionSpec = {
                    fadeIn(spring(stiffness = Spring.StiffnessMediumLow)) with
                        fadeOut(spring(stiffness = Spring.StiffnessMediumLow))
                },

            ) { title ->
                Text(title, style = MaterialTheme.typography.titleMedium)
            }
            StepProgressBar(
                numberOfSteps = numberOfSteps,
                currentStep = currentStep,
                modifier = Modifier.padding(top = 24.dp),
            )
        }
    }
}
