package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bottom navigation bar containing a next button and back button
 * @param onClickNext Function to be called when next is clicked
 * @param onClickBack Function to be called when back is clicked
 * @param nextEnabled Function used to check if next should be enabled
 * @param validateOnNext Will only call onClickNext if this returns true
 * @param nextButtonText Text displayed on next button
 * @param backButtonText Text displayed on back button
 * @param content The content that should be located above this bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    onClickNext: () -> Unit,
    onClickBack: (() -> Unit)? = null,
    nextEnabled: () -> Boolean = { true },
    validateOnNext: () -> Boolean = { true },
    nextButtonText: String = "Next >",
    backButtonText: String = "< Back",
    content: @Composable () -> Unit,
) {
    var backEnabled = true
    if (onClickBack == null) {
        backEnabled = false
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                tonalElevation = 2.dp,
                modifier = Modifier.requiredHeight(64.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    OutlinedButton(
                        onClick = {
                            if (onClickBack != null) {
                                onClickBack()
                            }
                        },
                        enabled = backEnabled,
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                    ) {
                        Text(backButtonText)
                    }
                    OutlinedButton(
                        onClick = {
                            if (validateOnNext()) {
                                onClickNext()
                            }
                        },
                        enabled = nextEnabled(),
                        modifier = Modifier.padding(start = 4.dp, end = 24.dp, top = 4.dp, bottom = 4.dp),
                    ) {
                        Text(nextButtonText)
                    }
                }
            }
        },
    ) {
        content()
    }
}
