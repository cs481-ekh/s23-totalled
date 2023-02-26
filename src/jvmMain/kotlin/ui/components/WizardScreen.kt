package ui.components

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

/**
 * Abstract class for a wizard screen.
 */
abstract class WizardScreen : Screen {
    /**
     * Title of the screen.
     */
    abstract val title: String

    /**
     * Current step of the screen, 0 is the first step.
     */
    abstract val step: Int

    /**
     * Text to display on the next button.
     */
    open val nextButtonText: String = "Next >"

    /**
     * Text to display on the back button.
     */
    open val backButtonText: String = "< Back"

    /**
     * True if the next button should be clickable.
     */
    open val nextEnabled = true

    /**
     * True if the back button should be clickable.
     */
    open val backEnabled = true

    /**
     * Called when the next button is clicked.
     */
    open fun onClickNext(navigator: Navigator) { }

    /**
     * Called with the back button is clicked.
     */
    open fun onClickBack(navigator: Navigator) {
        navigator.pop()
    }
}
