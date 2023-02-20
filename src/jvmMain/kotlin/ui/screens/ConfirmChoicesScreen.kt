package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.BottomNavBar
import ui.components.ScreenHeader
import ui.components.ScrollBox

data class ConfirmChoicesScreen(
    val expenseLogPath1: String,
    val expenseLogPath2: String,
    val outputDirPath: String,
) : Screen {
    @Composable
    private fun PathRow(label: String, path: String, icon: ImageVector = Icons.Filled.InsertDriveFile) {
        Row {
            Icon(
                icon,
                contentDescription = "File Icon",
            )
            Column(
                modifier = Modifier.padding(start = 8.dp).fillMaxWidth(),
            ) {
                Row {
                    Text(label, style = MaterialTheme.typography.titleSmall)
                }
                Text(
                    path,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.sizeIn(
                        // Set minimum height to be 2 lines
                        minHeight = with(LocalDensity.current) {
                            (MaterialTheme.typography.bodyMedium.lineHeight * 2).toDp()
                        },
                    ),
                )
            }
        }
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BottomNavBar(
            onClickNext = {
                navigator.push(
                    LoadingScreen(
                        expenseLogPath1,
                        expenseLogPath2,
                        outputDirPath,
                    ),
                )
            },
            onClickBack = { navigator.pop() },
        ) {
            Column() {
                ScreenHeader("Confirm Choices", 2)
                // Scrollable so that long paths don't get cut off
                ScrollBox(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 12.dp,
                        end = 24.dp,
                        bottom = 64.dp + 12.dp, // 64 because the bottom bar is 64 dp tall
                    ).fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                        val space = 12.dp
                        PathRow("Expense Log:", expenseLogPath1)
                        Spacer(modifier = Modifier.height(space))
                        if (expenseLogPath2 != "") {
                            PathRow("Second Expense Log:", expenseLogPath2)
                            Spacer(modifier = Modifier.height(space))
                        }
                        PathRow("Output will be saved to:", outputDirPath, Icons.Filled.Folder)
                    }
                }
            }
        }
    }
}
