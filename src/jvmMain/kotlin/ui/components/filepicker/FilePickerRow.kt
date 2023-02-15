package ui.components.filepicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Text field and button to open native file dialog
 * @param pathState The state to store the path in
 * @param label Label text for the text box
 * @param isDirPicker Set to true if you want to pick directories instead of files
 * @param fileExtensions File extensions whitelist, defaults to showing all file types
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilePickerRow(
    pathState: MutableState<String>,
    label: String,
    isDirPicker: Boolean = false,
    fileExtensions: List<String> = listOf(""),
    visible: Boolean = true,
) {
    var showFilePicker by remember { mutableStateOf(false) }
    if (isDirPicker) {
        DirectoryPicker(showFilePicker, initialDirectory = null) { path ->
            pathState.value = path ?: ""
            showFilePicker = false
        }
    } else {
        FilePicker(showFilePicker, fileExtensions = fileExtensions, initialDirectory = null) { path ->
            pathState.value = path ?: ""
            showFilePicker = false
        }
    }

    AnimatedVisibility(visible = visible, enter = fadeIn() + scaleIn(initialScale = .8f)) {
        Row(modifier = Modifier.padding(24.dp).requiredHeight(60.dp)) {
            TextField(
                value = pathState.value,
                onValueChange = { pathState.value = it },
                label = { Text(label) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8.toFloat()).fillMaxHeight(),
            )
            Button(
                onClick = {
                    showFilePicker = true
                },
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            ) {
                Text("Browse...")
            }
        }
    }
}
