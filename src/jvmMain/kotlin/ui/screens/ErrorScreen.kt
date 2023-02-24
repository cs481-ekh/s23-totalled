package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.components.WizardScreen
import kotlin.system.exitProcess

data class ErrorScreen(val e: Exception) : WizardScreen() {
    override val title = "Error"
    override val step = 2
    override val nextButtonText = "Exit >"
    override val backButtonText = "< Restart"
    override fun onClickNext(navigator: Navigator) {
        exitProcess(0)
    }
    override fun onClickBack(navigator: Navigator) {
        navigator.popAll()
    }

    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
        ) {
            Text("Failed to generate the output!\n")
            Text(e.toString(), color = MaterialTheme.colorScheme.error)
        }
    }
}
