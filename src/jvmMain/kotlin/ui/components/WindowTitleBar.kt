package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import kotlin.system.exitProcess

@Composable
fun WindowScope.WindowTitleBar(windowState: WindowState) {
    WindowDraggableArea {
        Row(
            modifier = Modifier.fillMaxWidth().height(24.dp).background(MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left
            Box(Modifier.weight(.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painterResource("sdp-logo-squares.png"),
                        modifier = Modifier.height(16.dp).padding(start = 4.dp).clip(RoundedCornerShape(2.dp)),
                        contentDescription = "App Icon",
                    )
                    Text(
                        "Totalled",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
            // Right
            Box(Modifier.weight(.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TitleBarButton({ windowState.isMinimized = true }, "horizontal_rule_black_24dp.svg")
                    TitleBarButton({ exitProcess(0) }, "close_black_24dp.svg")
                }
            }
        }
    }
}

@Composable
fun TitleBarButton(onClick: () -> Unit, iconResourcePath: String) {
    val interactionSource = remember { MutableInteractionSource() }
    // val isHovered by interactionSource.collectIsHoveredAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxHeight().requiredWidth(48.dp)
            .hoverable(interactionSource)
            .clickable(onClick = onClick),
    ) {
        Icon(
            painterResource(iconResourcePath),
            contentDescription = "Close",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxHeight(.75f),
        )
    }
}
