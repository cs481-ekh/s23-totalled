package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import ui.components.ScreenHeader
import ui.components.filepicker.FilePickerRow
import ui.nav.BottomNavBar

var outputDirPath = mutableStateOf("")

@Composable
fun SelectOutputScreen() {
    fun isNextEnabled(): Boolean {
        return outputDirPath.value.isNotEmpty()
    }

    BottomNavBar(
        onClickNext = { },
        onClickBack = { },
        isNextEnabled = { isNextEnabled() },
    ) {
        Column() {
            ScreenHeader("Select Output Directory", 1)
            FilePickerRow(outputDirPath, "Output Directory", isDirPicker = true)
        }
    }
}