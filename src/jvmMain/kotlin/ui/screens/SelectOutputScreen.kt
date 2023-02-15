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

data class SelectOutputScreen(
    val expenseLogPath1: String,
    val expenseLogPath2: String,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val outputDirPath = remember { mutableStateOf("") }
        fun isNextEnabled(): Boolean {
            return outputDirPath.value.isNotEmpty()
        }

        BottomNavBar(
            onClickNext = {
                navigator.push(
                    ConfirmChoicesScreen(
                        expenseLogPath1,
                        expenseLogPath2,
                        outputDirPath.value,
                    ),
                )
            },
            onClickBack = { navigator.pop() },
            nextEnabled = { isNextEnabled() },
        ) {
            Column() {
                ScreenHeader("Select Output Directory", 1)
                FilePickerRow(outputDirPath, "Output Directory", isDirPicker = true)
            }
        }
    }
}
