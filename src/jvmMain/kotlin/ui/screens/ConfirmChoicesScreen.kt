package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.BottomNavBar
import ui.components.ScreenHeader

data class ConfirmChoicesScreen(val inputFilePath: String, val outputDirPath: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BottomNavBar(
            onClickNext = { },
            onClickBack = { navigator.pop() },
        ) {
            Column() {
                ScreenHeader("Confirm Choices", 2)
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Expense Log to be used as input:")
                    Text("$inputFilePath\n", fontWeight = FontWeight.Light)
                    Text("Output will be saved to:")
                    Text(outputDirPath, fontWeight = FontWeight.Light)
                }
            }
        }
    }
}
