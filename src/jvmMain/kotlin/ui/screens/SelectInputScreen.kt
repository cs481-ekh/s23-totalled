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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.components.WizardScreen
import ui.components.filepicker.FilePickerRow

class SelectInputScreen : WizardScreen() {
    private var expenseLogPath1 by mutableStateOf("")
    private var expenseLogPath2 by mutableStateOf("")
    override val title = "Select Expense Log(s)"
    override val step = 0
    override var nextEnabled by mutableStateOf(false)
    override val backEnabled = false
    override fun onClickNext(navigator: Navigator) {
        navigator.push(
            SelectOutputScreen(
                expenseLogPath1,
                expenseLogPath2,
            ),
        )
    }

    @Composable
    override fun Content() {
        fun maybeEnableNext() {
            nextEnabled = expenseLogPath1.isNotEmpty()
        }
        var secondFilePickerVisible by remember { mutableStateOf(false) }

        Column() {
            FilePickerRow(
                pathString = expenseLogPath1,
                onValueChange = { expenseLogPath1 = it ; maybeEnableNext() },
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
                    pathString = expenseLogPath2,
                    onValueChange = { expenseLogPath2 = it },
                    label = "Second Expense Log - Optional (.xlsx)",
                    fileExtensions = listOf("xlsx"),
                    visible = secondFilePickerVisible,
                )
            }
        }
    }
}
