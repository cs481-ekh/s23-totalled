package ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
 * https://stackoverflow.com/questions/71057137/step-progress-bar-with-android-compose
 * Slightly modified version of the top answer to this question
 */

/**
 * A step progress bar for tracking progress in a task
 * @param modifier Standard modifier
 * @param numberOfSteps Total number of steps to display
 * @param currentStep Determines how filled the bar is, first step is 0
 */
@Composable
fun StepProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Step(
            modifier = Modifier.weight(1F, false),
            isComplete = 0 < currentStep,
            isCurrent = 0 == currentStep,
            isFirst = true,
        )
        for (step in 1 until numberOfSteps) {
            Step(
                modifier = Modifier.weight(1F),
                isComplete = step < currentStep,
                isCurrent = step == currentStep,
                isFirst = false,
            )
        }
    }
}

@Composable
private fun Step(modifier: Modifier = Modifier, isComplete: Boolean, isCurrent: Boolean, isFirst: Boolean) {
    val transition = updateTransition(isComplete, label = "Step Progress Bar Transition")

    val color by transition.animateColor(
        transitionSpec = { spring(stiffness = Spring.StiffnessMediumLow) },
        label = "color",
    ) {
        if (isCurrent || isComplete) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outline
        }
    }

    val innerCircleColor by transition.animateColor(
        transitionSpec = { spring(stiffness = Spring.StiffnessMediumLow) },
        label = "innerCircleColor",
    ) {
        if (isComplete) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.background
        }
    }

    Box(modifier = modifier) {
        if (!isFirst) {
            // Line
            Divider(
                modifier = Modifier.align(Alignment.CenterStart),
                color = color,
                thickness = 2.dp,
            )
        }

        // Circle
        Canvas(
            modifier = Modifier
                .size(15.dp)
                .align(Alignment.CenterEnd)
                .border(
                    shape = CircleShape,
                    width = 2.dp,
                    color = color,
                ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            },
        )
    }
}
