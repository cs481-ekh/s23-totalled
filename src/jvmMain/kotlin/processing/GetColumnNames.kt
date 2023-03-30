package processing

import data.ColumnNames
import java.io.File

fun getColumnNames(columnNamesFilePath: String): ColumnNames {
    val expenseLogInternalColumnNames = listOf(
        "Senior Design PO",
        "Business Purpose",
        "Total Amount",
        "Amount 2",
        "Card",
        "Date Ordered",
        "Vendor Name",
    )
    val projectBookInternalColumnNames = listOf(
        "Team Abbr",
        "Sponsor / Funding Department",
        "Billing Address / BSU Department ID",
        "Sponsor Contact",
        "Sponsor Contact email (optional)",
    )
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
