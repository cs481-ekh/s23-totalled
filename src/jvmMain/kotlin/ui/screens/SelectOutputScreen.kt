package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import ui.components.WizardScreen
import ui.components.filepicker.FilePickerRow

data class SelectOutputScreen(
    val expenseLogPath1: String,
    val expenseLogPath2: String,
) : WizardScreen() {
    private var outputDirPath by mutableStateOf("")
    override val title = "Select Output Directory"
    override val step = 1
    override var nextEnabled by mutableStateOf(false)
    override fun onClickNext(navigator: Navigator) {
        navigator.push(
            ConfirmChoicesScreen(
                expenseLogPath1,
                expenseLogPath2,
                outputDirPath,
            ),
        )
    }

    @Composable
    override fun Content() {
        fun maybeEnableNext() {
            nextEnabled = expenseLogPath1.isNotEmpty()
        }
        Column() {
            FilePickerRow(
                pathString = outputDirPath,
                onValueChange = { outputDirPath = it ; maybeEnableNext() },
                label = "Output Directory",
                isDirPicker = true,
            )
        }
    }
}
