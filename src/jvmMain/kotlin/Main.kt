import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.components.TotalledInput
import ui.components.WindowTitleBar
import ui.components.Wizard
import ui.screens.SelectInputScreen
import ui.theme.AppTheme

// Also should be updated in build.gradle.kts
const val TOTALLED_VERSION = "1.0.1"

fun main() = application {
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        isMinimized = false,
        width = 600.dp,
        height = 450.dp,
    )
    val title = "Totalled"
    val icon = painterResource("totalled-icon.svg")

    Window(
        onCloseRequest = ::exitApplication,
        icon = icon,
        title = title,
        undecorated = true,
        transparent = true,
        state = windowState,
    ) {
        AppTheme() {
            Column(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            ) {
                WindowTitleBar(windowState, title, icon)
                Wizard(SelectInputScreen(TotalledInput()), numberOfSteps = 4)
            }
        }
    }
}
