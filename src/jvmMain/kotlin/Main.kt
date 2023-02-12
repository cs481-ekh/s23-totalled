import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import ui.screens.SelectInputScreen

@Composable
@Preview
fun App() {
    MaterialTheme(darkColors(surface = Color(0xff1f1f1f))) {
        Navigator(SelectInputScreen()) { CurrentScreen() }
    }
}

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Floating,
        isMinimized = false,
        width = 600.dp,
        height = 450.dp,
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Totalled",
        state = state,
    ) {
        App()
    }
}
