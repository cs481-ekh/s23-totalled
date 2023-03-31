package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import processing.generateOutput
import ui.components.TotalledInput
import ui.components.WizardScreen

data class LoadingScreen(val inputData: TotalledInput) : WizardScreen() {
    override val title = "Generating Output"
    override val step = 3
    override val nextEnabled = false
    override val backEnabled = false

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                try {
                    generateOutput(
                        inputData.expenseLogPath1,
                        inputData.expenseLogPath2,
                        inputData.projectBookPath,
                        inputData.outputDirPath,
                        inputData.columnNamesPath,
                    )
                    navigator.push(SuccessScreen(inputData))
                } catch (e: Exception) {
                    e.printStackTrace()
                    navigator.push(ErrorScreen(e))
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp).fillMaxWidth().fillMaxHeight(.5f),
        ) {
            CircularProgressIndicator()
        }
    }
}
