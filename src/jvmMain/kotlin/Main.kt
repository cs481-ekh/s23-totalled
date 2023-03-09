import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.components.WindowTitleBar
import ui.components.Wizard
import ui.theme.AppTheme

fun main() = application {
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        isMinimized = false,
        width = 600.dp,
        height = 450.dp,
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Totalled",
        undecorated = true,
        state = windowState,
    ) {
        AppTheme() {
            Column {
                WindowTitleBar(windowState)
                Wizard()
            }
        }
    }
}
