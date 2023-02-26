package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.components.ScrollBox
import ui.components.WizardScreen

data class ConfirmChoicesScreen(
    val expenseLogPath1: String,
    val expenseLogPath2: String,
    val outputDirPath: String,
) : WizardScreen() {
    override val title = "Confirm Choices"
    override val step = 2
    override fun onClickNext(navigator: Navigator) {
        navigator.push(
            LoadingScreen(
                expenseLogPath1,
                expenseLogPath2,
                outputDirPath,
            ),
        )
    }

    @Composable
    private fun PathRow(label: String, path: String, iconResourcePath: String = "insert_drive_file_black_24dp.svg") {
        Row {
            Icon(
                painter = painterResource(iconResourcePath),
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
        Column() {
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
                    PathRow("Output will be saved to:", outputDirPath, "folder_black_24dp.svg")
                }
            }
        }
    }
}
