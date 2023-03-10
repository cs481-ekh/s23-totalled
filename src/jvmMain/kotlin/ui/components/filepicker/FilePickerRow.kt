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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Text field and button to open native file dialog
 * @param pathString string to store path in
 * @param onValueChange what to do when the value changes
 * @param label Label text for the text box
 * @param isDirPicker Set to true if you want to pick directories instead of files
 * @param fileExtensions File extensions whitelist, defaults to showing all file types
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilePickerRow(
    pathString: String = "",
    onValueChange: (String) -> Unit,
    label: String,
    isDirPicker: Boolean = false,
    fileExtensions: List<String> = listOf(""),
    visible: Boolean = true,
) {
    var showFilePicker by remember { mutableStateOf(false) }
    if (isDirPicker) {
        DirectoryPicker(showFilePicker, initialDirectory = null) { path ->
            if (path != null) {
                onValueChange(path)
            }
            showFilePicker = false
        }
    } else {
        FilePicker(showFilePicker, fileExtensions = fileExtensions, initialDirectory = null) { path ->
            if (path != null) {
                onValueChange(path)
            }
            showFilePicker = false
        }
    }

    AnimatedVisibility(visible = visible, enter = fadeIn() + scaleIn(initialScale = .8f)) {
        Row(modifier = Modifier.padding(24.dp).requiredHeight(60.dp)) {
            TextField(
                value = pathString,
                onValueChange = onValueChange,
                label = { Text(label) },
                singleLine = true,
                shape = RoundedCornerShape(topStart = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                ),
                modifier = Modifier.fillMaxWidth(0.79.toFloat()).fillMaxHeight(),
            )
            Button(
                onClick = {
                    showFilePicker = true
                },
                shape = RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp),
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            ) {
                Text("Browse...")
            }
        }
    }
}
