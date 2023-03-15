package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import ui.components.TotalledInputData
import ui.components.WizardScreen
import ui.components.filepicker.FilePickerRow

data class SelectOutputScreen(
    val inputData: TotalledInputData,
) : WizardScreen() {
    override val title = "Select Output Directory"
    override val step = 1
    override var nextEnabled by mutableStateOf(false)
    override fun onClickNext(navigator: Navigator) {
        navigator.push(ConfirmChoicesScreen(inputData))
    }

    @Composable
    override fun Content() {
        fun maybeEnableNext() {
            nextEnabled = inputData.outputDirPath.isNotEmpty()
        }
        Column() {
            FilePickerRow(
                pathString = inputData.outputDirPath,
                onValueChange = { inputData.outputDirPath = it ; maybeEnableNext() },
                label = "Output Directory",
                isDirPicker = true,
            )
        }
    }
}
