package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.components.TotalledInput
import ui.components.WizardScreen
import ui.components.filepicker.FilePickerRow

data class SelectProjectBookScreen(val input: TotalledInput) : WizardScreen() {
    override val title = "Select Project Book (Optional)"
    override val step = 1
    override var nextEnabled = true
    override fun onClickNext(navigator: Navigator) {
        navigator.push(
            SelectOutputScreen(input),
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
        }
    }
}
