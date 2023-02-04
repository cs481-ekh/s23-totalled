package ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
 * https://stackoverflow.com/questions/71057137/step-progress-bar-with-android-compose
 * Slightly modified version of the top answer to this question
 */

@Composable
fun StepProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Step(
            modifier = Modifier.weight(1F, false),
            isCompete = 0 < currentStep,
            isCurrent = 0 == currentStep,
            isFirst = true
        )
        for (step in 1 until numberOfSteps) {
            Step(
                modifier = Modifier.weight(1F),
                isCompete = step < currentStep,
                isCurrent = step == currentStep,
                isFirst = false
            )
        }
    }
}

@Composable
private fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean, isFirst: Boolean) {
    val color = if (isCompete || isCurrent) darkColors().primary else Color(0xff4f4f4f)
    val innerCircleColor = if (isCompete) darkColors().primary else darkColors().background

    Box(modifier = modifier) {

        if (!isFirst) {
            //Line
            Divider(
                modifier = Modifier.align(Alignment.CenterStart),
                color = color,
                thickness = 2.dp
            )
        }

        //Circle
        Canvas(modifier = Modifier
            .size(15.dp)
            .align(Alignment.CenterEnd)
            .border(
                shape = CircleShape,
                width = 2.dp,
                color = color
            ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}
