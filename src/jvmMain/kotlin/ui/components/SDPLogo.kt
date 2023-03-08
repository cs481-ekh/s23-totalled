package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SDPLogo(
    onClick: () -> Unit,
    isActive: Boolean,
    imageModifier: Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clip(RoundedCornerShape(4.dp)).clickable(onClick = onClick).requiredSize(80.dp, 40.dp),
    ) {
        Image(
            painterResource("sdp-logo80x40.png"),
            contentDescription = "sdp logo",
            contentScale = ContentScale.FillBounds,
            modifier = imageModifier,
        )
        if (isActive) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background.copy(alpha = .75f)),
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "File Icon",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize(.75f),
                )
            }
        }
    }
}
