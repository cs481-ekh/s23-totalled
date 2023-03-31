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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.components.ScrollBox
import ui.components.TotalledInput
import ui.components.WizardScreen

data class ConfirmChoicesScreen(val input: TotalledInput) : WizardScreen() {
    override val title = "Confirm Choices"
    override val step = 2
    override fun onClickNext(navigator: Navigator) {
        navigator.push(LoadingScreen(input))
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
                    top = 16.dp,
                    end = 24.dp,
                    bottom = 64.dp, // 64 because the bottom bar is 64 dp tall
                ).fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(start = 0.dp, end = 0.dp)) {
                    var space by remember { mutableStateOf(24.dp) }
                    // Squish things down to fit everything if all 4 are visible
                    space = if (input.expenseLogPath2 != "" && input.projectBookPath != "") {
                        0.dp
                    } else {
                        18.dp
                    }
                    PathRow("Expense Log:", input.expenseLogPath1)
                    Spacer(modifier = Modifier.height(space))
                    if (input.expenseLogPath2 != "") {
                        PathRow("Second Expense Log:", input.expenseLogPath2)
                        Spacer(modifier = Modifier.height(space))
                    }
                    if (input.projectBookPath != "") {
                        PathRow("Project Book:", input.projectBookPath)
                        Spacer(modifier = Modifier.height(space))
                    }
                    if (input.columnNamesPath != "") {
                        PathRow("Column Names Text File:", input.projectBookPath)
                        Spacer(modifier = Modifier.height(space))
                    }
                    PathRow("Output will be saved to:", input.outputDirPath, "folder_black_24dp.svg")
                }
            }
        }
    }
}
