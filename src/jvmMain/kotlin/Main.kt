import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
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
    val title = "Totalled"
    val icon = painterResource("totalled-icon.svg")
    var isOpen by remember { mutableStateOf(true) }

    Window(
        onCloseRequest = ::exitApplication,
        icon = icon,
        title = title,
        undecorated = true,
        transparent = true,
        state = windowState,
    ) {
        AppTheme() {
            LaunchedEffect(Unit) {
                delay(2000) // Do some heavy lifting
                isOpen = false
            }
            Column(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            ) {
                WindowTitleBar(windowState, title, icon)
                Wizard()
            }
        }
    }
}
