package ui.components.filepicker

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.filepicker.DirectoryPicker
import ui.filepicker.FilePicker

/**
 * Text field and button to open native file dialog
 * @param pathState The state to store the path in
 * @param label Label text for the text box
 * @param isDirPicker Set to true if you want to pick directories instead of files
 * @param fileExtensions File extensions whitelist, defaults to showing all file types
 */
@Composable
fun FilePickerRow(
    pathState: MutableState<String>,
    label: String,
    isDirPicker: Boolean = false,
    fileExtensions: List<String> = listOf(""),
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
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        ) {
            Text("Browse...")
        }
    }
}
