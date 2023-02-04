package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Header for each screen
 * @param pageTitle Title text to display
 * @param currentStep Determines how filled the bar is, first step is 0
 */
@Composable
fun ScreenHeader(
    pageTitle: String,
    currentStep: Int
) {
    Row(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(pageTitle, fontSize = 16.sp)
            StepProgressBar(
                numberOfSteps = 3,
                currentStep = currentStep,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}