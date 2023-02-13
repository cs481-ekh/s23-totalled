package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.BottomNavBar
import ui.components.ScreenHeader
import kotlin.system.exitProcess

data class ErrorScreen(val e: Exception) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BottomNavBar(
            onClickNext = { exitProcess(0) },
            onClickBack = { navigator.popAll() },
            nextButtonText = "Exit >",
            backButtonText = "< Restart",
        ) {
            Column() {
                ScreenHeader("Error", 2)
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                ) {
                    Text("Failed to generate the output!\n")
                    Text(e.toString(), color = darkColors().error)
                }
            }
        }
    }
}
