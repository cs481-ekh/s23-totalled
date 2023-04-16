package ui.components

import TOTALLED_VERSION
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.screens.SDPInfoScreen

/**
 * Bottom navigation bar containing a next button and back button
 * @param onClickNext Function to be called when next is clicked
 * @param onClickBack Function to be called when back is clicked
 * @param nextEnabled True if next button should be enabled
 * @param backEnabled True if back button should be enabled
 * @param nextButtonText Text displayed on next button
 * @param backButtonText Text displayed on back button
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavBar(
    onClickNext: () -> Unit,
    onClickBack: (() -> Unit)? = null,
    nextEnabled: () -> Boolean = { true },
    backEnabled: () -> Boolean = { true },
    nextButtonText: String = "Next >",
    backButtonText: String = "< Back",
) {
    val navigator = LocalNavigator.currentOrThrow
    val sdpInfoScreen = remember { SDPInfoScreen() }
    var sdpInfoActive by remember { mutableStateOf(false) }
    var sdpImageBlur by remember { mutableStateOf(Modifier.blur(0.dp)) }
    fun sdpButtonOnClick() {
        sdpInfoActive = if (navigator.lastItem.key == "SDPInfoScreen") {
            navigator.pop()
            sdpImageBlur = Modifier.blur(0.dp)
            false
        } else {
            navigator.push(sdpInfoScreen)
            sdpImageBlur = Modifier.blur(4.dp)
            true
        }
    }

    BottomAppBar(
        tonalElevation = 3.dp,
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.requiredHeight(64.dp),
    ) {
        Row {
            SDPLogo(
                onClick = { sdpButtonOnClick() },
                isActive = sdpInfoActive,
                imageModifier = sdpImageBlur,
            )
            Spacer(Modifier.weight(1f))
            AnimatedContent(
                targetState = sdpInfoActive,
                transitionSpec = {
                    fadeIn(spring(stiffness = Spring.StiffnessMediumLow)) with
                        fadeOut(spring(stiffness = Spring.StiffnessMediumLow))
                },
            ) { sdpInfoActive ->
                // Only show navigation buttons if SDP info is not being shown
                if (!sdpInfoActive) {
                    Row {
                        OutlinedButton(
                            onClick = {
                                if (onClickBack != null) {
                                    onClickBack()
                                }
                            },
                            enabled = backEnabled(),
                            modifier = Modifier.padding(end = 8.dp),
                        ) {
                            Text(backButtonText)
                        }
                        OutlinedButton(
                            onClick = { onClickNext() },
                            enabled = nextEnabled(),
                        ) {
                            Text(nextButtonText)
                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        Text(
                            text = "Version $TOTALLED_VERSION",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}
