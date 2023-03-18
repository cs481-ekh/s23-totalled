package ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TotalledInput {
    var expenseLogPath1 by mutableStateOf("")
    var expenseLogPath2 by mutableStateOf("")
    var projectBookPath by mutableStateOf("")
    var outputDirPath by mutableStateOf("")
}
