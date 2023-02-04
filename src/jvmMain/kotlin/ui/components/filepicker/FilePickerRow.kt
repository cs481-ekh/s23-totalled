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
