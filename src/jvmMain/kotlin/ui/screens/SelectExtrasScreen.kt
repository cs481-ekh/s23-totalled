package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.components.TotalledInput
import ui.components.WizardScreen
import ui.components.filepicker.FilePickerRow

data class SelectExtrasScreen(val input: TotalledInput) : WizardScreen() {
    override val title = "Select Optional Extras"
    override val step = 2
    override var nextEnabled = true
    override fun onClickNext(navigator: Navigator) {
        navigator.push(
            ConfirmChoicesScreen(input),
        )
    }

    @Composable
    override fun Content() {
        Column() {
            FilePickerRow(
                pathString = input.projectBookPath,
                onValueChange = { input.projectBookPath = it },
                label = "Project Book (.xlsx)",
                fileExtensions = listOf("xlsx"),
            )
            FilePickerRow(
                pathString = input.columnNamesPath,
                onValueChange = { input.columnNamesPath = it },
                label = "Column Names Text File (plaintext)",
                fileExtensions = listOf("txt", ""),
            )
        }
    }
}
