package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bottom navigation bar containing a next button and back button
 * @param onClickNext Function to be called when next is clicked
 * @param onClickBack Function to be called when back is clicked
 * @param nextEnabled True if next button should be enabled
 * @param backEnabled True if back button should be enabled
 * @param nextButtonText Text displayed on next button
 * @param backButtonText Text displayed on back button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    onClickNext: () -> Unit,
    onClickBack: (() -> Unit)? = null,
    nextEnabled: () -> Boolean = { true },
    backEnabled: () -> Boolean = { true },
    nextButtonText: String = "Next >",
    backButtonText: String = "< Back",
) {
    BottomAppBar(
        tonalElevation = 3.dp,
        contentPadding = PaddingValues(12.dp),
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
    }
}
