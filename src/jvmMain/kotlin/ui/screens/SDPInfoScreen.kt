package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.WizardScreen
import java.awt.Desktop
import java.net.URISyntaxException
import java.net.URL

class SDPInfoScreen : WizardScreen() {
    override val key = "SDPInfoScreen"
    override val title = ""
    override val step = -1
    override val nextEnabled = false
    override val backEnabled = false

    private fun openWebpage(url: String): Boolean {
        try {
            val desktop = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(URL(url).toURI())
                return true
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        return false
    }

    @Composable
    override fun Content() {
        val url = remember { "https://www.boisestate.edu/coen-cs/community/cs481-senior-design-project/" }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(24.dp),
        ) {
            Box(
                modifier = Modifier.requiredSize(160.dp, 80.dp).clip(RoundedCornerShape(4.dp)),
            ) {
                Image(
                    // Scaling bitmap images does not look good so use exact size
                    painterResource("sdp-logo160x80.png"),
                    contentDescription = "sdp logo",
                    contentScale = ContentScale.FillBounds,
                )
            }
            Text(
                text = "This app was created for a\n" +
                    "Boise State University\n" +
                    "Computer Science Senior Design Project by:\n\n" +
                    "Bree Latimer\n" +
                    "Chase Stauts\n" +
                    "D Metz\n" +
                    "Paul Ellis\n\n" +
                    "For information about sponsoring a project go to",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = url,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { openWebpage(url) },
            )
        }
    }
}
