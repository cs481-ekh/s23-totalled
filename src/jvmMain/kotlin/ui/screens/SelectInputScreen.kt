package ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.components.TotalledInput
import ui.components.WizardScreen
import ui.components.filepicker.FilePickerRow

data class SelectInputScreen(var input: TotalledInput) : WizardScreen() {
    override val title = "Select Expense Log(s)"
    override val step = 0
    override var nextEnabled by mutableStateOf(false)
    override val backEnabled = false
    override fun onClickNext(navigator: Navigator) {
        navigator.push(SelectOutputScreen(input))
    }

    @Composable
    override fun Content() {
        fun maybeEnableNext() {
            nextEnabled = input.expenseLogPath1.isNotEmpty()
        }
        var secondFilePickerVisible by rememberSaveable { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            maybeEnableNext()
        }

        Column() {
            FilePickerRow(
                pathString = input.expenseLogPath1,
                onValueChange = { input.expenseLogPath1 = it ; maybeEnableNext() },
                label = "Expense Log (.xlsx)",
                fileExtensions = listOf("xlsx"),
            )
            // Button to add second FilePickerRow
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Language bug, need to use the fully qualified name here
                androidx.compose.animation.AnimatedVisibility(
                    visible = !secondFilePickerVisible,
                    exit = fadeOut(animationSpec = tween(150)),
                ) {
                    OutlinedButton(
                        onClick = { secondFilePickerVisible = true },
                        modifier = Modifier.padding(24.dp),
                    ) {
                        Icon(Icons.Outlined.AddCircle, "Add", tint = MaterialTheme.colorScheme.primary)
                        Text(" Add an expense log for a second fiscal year")
                    }
                }
                FilePickerRow(
                    pathString = input.expenseLogPath2,
                    onValueChange = { input.expenseLogPath2 = it },
                    label = "Second Expense Log - Optional (.xlsx)",
                    fileExtensions = listOf("xlsx"),
                    visible = secondFilePickerVisible,
                )
            }
        }
    }
}
