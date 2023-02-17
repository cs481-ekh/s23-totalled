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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import processing.generateOutput
import ui.components.BottomNavBar
import ui.components.ScreenHeader

data class LoadingScreen(
    val expenseLogPath1: String,
    val expenseLogPath2: String,
    val outputDirPath: String,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                try {
                    generateOutput(expenseLogPath1, expenseLogPath2, outputDirPath)
                    navigator.push(SuccessScreen(outputDirPath))
                } catch (e: Exception) {
                    e.printStackTrace()
                    navigator.push(ErrorScreen(e))
                }
            }
        }
        BottomNavBar(
            onClickNext = { },
            nextEnabled = { false },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            ) {
                ScreenHeader("Generating Output", 2)
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp).fillMaxHeight(.5f),
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
