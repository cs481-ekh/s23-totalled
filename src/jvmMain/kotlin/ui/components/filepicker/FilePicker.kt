package ui.filepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * This file contains code from:
 * https://github.com/Wavesonics/compose-multiplatform-file-picker
 *
 * At the moment of writing this, the library hasn't been updated
 * to include support for multiple file extensions, so I just copied
 * the code from GitHub.
 */
@Composable
fun FilePicker(
    show: Boolean,
    initialDirectory: String?,
    fileExtensions: List<String>,
    onFileSelected: (String?) -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(show) {
        if(show) {
            scope.launch(Dispatchers.Default) {
                val fileFilter = if (fileExtensions.isNotEmpty()) {
                    fileExtensions.joinToString(",")
                } else {
                    ""
                }

                val initialDir = initialDirectory ?: System.getProperty("user.dir")
                val fileChosen = FileChooser.chooseFile(
                    initialDirectory = initialDir,
                    fileExtensions = fileFilter
                )
                withContext(Dispatchers.Main) {
                    onFileSelected(fileChosen)
                }
            }
        }
    }
}

@Composable
fun DirectoryPicker(
    show: Boolean,
    initialDirectory: String?,
    onFileSelected: (String?) -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(show) {
        if(show) {
            scope.launch(Dispatchers.Default) {
                val initialDir = initialDirectory ?: System.getProperty("user.dir")
                val fileChosen = FileChooser.chooseDirectory(initialDir)
                withContext(Dispatchers.Main) {
                    onFileSelected(fileChosen)
                }
            }
        }
    }
}

