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
        val expenseLogPath1 = remember { mutableStateOf("") }
        val expenseLogPath2 = remember { mutableStateOf("") }
        fun isNextEnabled(): Boolean {
            return expenseLogPath1.value.isNotEmpty()
        }
        var secondFilePickerVisible by remember { mutableStateOf(false) }

        BottomNavBar(
            onClickNext = {
                navigator.push(
                    SelectOutputScreen(
                        expenseLogPath1.value,
                        expenseLogPath2.value,
                    ),
                )
            },
            nextEnabled = { isNextEnabled() },
        ) {
            Column() {
                ScreenHeader("Select Expense Log(s)", 0)
                FilePickerRow(expenseLogPath1, "Expense Log (.xlsx)", fileExtensions = listOf("xlsx"))

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
                        expenseLogPath2,
                        "Second Expense Log - Optional (.xlsx)",
                        fileExtensions = listOf("xlsx"),
                        visible = secondFilePickerVisible,
                    )
                }
            }
        }
    }
}
