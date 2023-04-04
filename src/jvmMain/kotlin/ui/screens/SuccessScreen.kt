package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.components.TotalledInput
import ui.components.WizardScreen
import java.awt.Desktop
import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess

data class SuccessScreen(val inputData: TotalledInput) : WizardScreen() {
    override val title = "Success"
    override val step = 4
    override val nextButtonText = "Exit >"
    override val backButtonText = "< Restart"
    override fun onClickNext(navigator: Navigator) {
        exitProcess(0)
    }
    override fun onClickBack(navigator: Navigator) {
        navigator.popAll()
        navigator.replace(SelectInputScreen(TotalledInput()))
    }

    @Composable
    override fun Content() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
        ) {
            Text("The output was successfully generated!")
            Button(
                onClick = {
                    try {
                        val dir = File(inputData.outputDirPath)
                        Desktop.getDesktop().open(dir)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                modifier = Modifier.padding(24.dp),
            ) {
                Text("Open Output Directory")
            }
        }
    }
}
