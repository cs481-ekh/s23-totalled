package ui.components

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScrollBox(modifier: Modifier = Modifier, content: @Composable (BoxScope.() -> Unit)) {
    Box(
        modifier = modifier,
    ) {
        val stateVertical = rememberScrollState(0)
        Box(modifier = Modifier.verticalScroll(stateVertical)) {
            content()
        }
        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(stateVertical),
            style = LocalScrollbarStyle.current.copy(
                unhoverColor = MaterialTheme.colorScheme.surfaceVariant,
                hoverColor = MaterialTheme.colorScheme.outline,
            ),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
        )
    }
}
