package ui.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(
    onClickNext: () -> Unit,
    onClickBack: (() -> Unit)? = null,
    isNextEnabled: () -> Boolean= { true },
    validateOnNext: () -> Boolean = { true },
    nextButtonText: String = "Next >",
    backButtonText: String = "< Back",
    content: @Composable () -> Unit
) {
    var isBackEnabled = true
    if (onClickBack == null) {
        isBackEnabled = false
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                elevation = 2.dp,
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
                        enabled = isBackEnabled,
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                    ) {
                        Text(backButtonText)
                    }
                    OutlinedButton(
                        onClick = {
                            if (validateOnNext()) {
                                onClickNext()
                            }
                        },
                        enabled = isNextEnabled(),
                        modifier = Modifier.padding(start = 4.dp, end = 24.dp, top = 4.dp, bottom = 4.dp)
                    ) {
                        Text(nextButtonText)
                    }
                }
            }
        }
    ) {
        content()
    }
}