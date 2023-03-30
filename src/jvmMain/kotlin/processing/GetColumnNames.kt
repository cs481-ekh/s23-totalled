package processing

import data.ColumnNames
import java.io.File

// Internal names used by our program for column names
private val expenseLogInternalColumnNames = listOf(
    "Senior Design PO",
    "Business Purpose",
    "Total Amount",
    "Amount 2",
    "Card",
    "Date Ordered",
    "Vendor Name",
)

// Internal names used by our program for column names
private val projectBookInternalColumnNames = listOf(
    "Team Abbr",
    "Sponsor / Funding Department",
    "Billing Address / BSU Department ID",
    "Sponsor Contact",
    "Sponsor Contact email (optional)",
)

// (list zip list).toMap will create a map where each value in the list is value -> value in the map
// so "Senior Design PO" in the list will become "Senior Design PO"->"Senior Design PO" key/value pair in the map
private val defaultColumnNames = ColumnNames(
    (expenseLogInternalColumnNames zip expenseLogInternalColumnNames).toMap(),
    (projectBookInternalColumnNames zip projectBookInternalColumnNames).toMap(),
)

fun getColumnNames(columnNamesFilePath: String): ColumnNames {
    if (columnNamesFilePath.isEmpty()) {
        // by default the internal names should match the external ones
        return defaultColumnNames
    }
    val expenseLogColumnsToName = mutableMapOf<String, String>()
    val projectBookColumnsToName = mutableMapOf<String, String>()

    val fileLines = File(columnNamesFilePath).useLines { it.toList() }
    var currentLine = 0
    var currentExpenseLogColumn = 0
    var currentProjectBookColumn = 0

    while (currentExpenseLogColumn < expenseLogInternalColumnNames.size) {
        val line = fileLines[currentLine].trim()
        if (!line.startsWith("#") && line.isNotEmpty()) {
            expenseLogColumnsToName[line] = expenseLogInternalColumnNames[currentExpenseLogColumn]
            currentExpenseLogColumn++
        }
        currentLine++
    }

    while (currentProjectBookColumn < projectBookInternalColumnNames.size) {
        val line = fileLines[currentLine].trim()
        if (!line.startsWith("#") && line.isNotEmpty()) {
            projectBookColumnsToName[line] = projectBookInternalColumnNames[currentProjectBookColumn]
            currentProjectBookColumn++
        }
        currentLine++
    }

    return ColumnNames(expenseLogColumnsToName, projectBookColumnsToName)
}

fun main() {
    val columnNames = getColumnNames("/home/d/column-names-default.txt")
    println(columnNames.projectBookColumnNames)
}
