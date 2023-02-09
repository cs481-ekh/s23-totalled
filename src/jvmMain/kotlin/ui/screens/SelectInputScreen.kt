package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.BottomNavBar
import ui.components.ScreenHeader
import ui.components.filepicker.FilePickerRow

class SelectInputScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val inputFilePath = remember { mutableStateOf("") }
        fun isNextEnabled(): Boolean {
            return inputFilePath.value.isNotEmpty()
        }

        BottomNavBar(
            onClickNext = { navigator.push(SelectOutputScreen(inputFilePath.value)) },
            nextEnabled = { isNextEnabled() },
        ) {
            Column() {
                ScreenHeader("Select Input File", 0)
                FilePickerRow(inputFilePath, "Input File (.xlsx)", fileExtensions = listOf("xlsx"))
            }
        }
    }
}
