package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import ui.components.ScreenHeader
import ui.components.filepicker.FilePickerRow
import ui.nav.BottomNavBar

internal val inputFilePath = mutableStateOf("")

@Composable
fun SelectInputScreen() {
    fun isNextEnabled(): Boolean {
        return inputFilePath.value.isNotEmpty()
    }

    BottomNavBar(
        onClickNext = {  },
        nextEnabled = { isNextEnabled() },
    ) {
        Column() {
            ScreenHeader("Select Input File", 0)
            FilePickerRow(inputFilePath, "Input File (.xlsx)", fileExtensions = listOf("xlsx"))
        }
    }
}

