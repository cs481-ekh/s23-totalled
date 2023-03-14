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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import kotlin.system.exitProcess

@Composable
fun WindowScope.WindowTitleBar(windowState: WindowState, title: String, icon: Painter) {
    var isFocused by remember { mutableStateOf(true) }
    // Listening for focus events.
    DisposableEffect(Unit) {
        window.addWindowFocusListener(object : WindowFocusListener {
            override fun windowGainedFocus(e: WindowEvent) {
                isFocused = true
            }

            override fun windowLostFocus(e: WindowEvent) {
                isFocused = false
            }
        })
        onDispose { }
    }
    WindowDraggableArea {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left side
            Box(Modifier.weight(.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        icon,
                        modifier = Modifier.height(16.dp).padding(start = 6.dp).clip(RoundedCornerShape(2.dp)),
                        contentDescription = "App Icon",
                    )
                    Text(
                        title,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isFocused) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
            // Right side
            Box(Modifier.weight(.5f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TitleBarButton(
                        { windowState.isMinimized = true },
                        "horizontal_rule_black_24dp.svg",
                        isFocused,
                    )
                    TitleBarButton(
                        { exitProcess(0) },
                        "close_black_24dp.svg",
                        isFocused,
                    )
                }
            }
        }
    }
}

@Composable
fun TitleBarButton(onClick: () -> Unit, iconResourcePath: String, isFocused: Boolean) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxHeight().requiredWidth(48.dp)
            .hoverable(interactionSource)
            .clickable(onClick = onClick),
    ) {
        Icon(
            painterResource(iconResourcePath),
            contentDescription = "Close",
            tint = if (isFocused) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.outline
            },
            modifier = Modifier.fillMaxHeight(.65f),
        )
    }
}
