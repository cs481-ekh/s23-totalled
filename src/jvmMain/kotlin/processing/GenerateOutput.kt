package processing

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Paths

/**
 * Placeholder, rename package/file/function if needed.
 */
fun generateOutput(
    expenseLogPath1: String,
    expenseLogPath2: String,
    projectBookPath: String,
    outputDirPath: String,
    columnNamesPath: String,
) {
    println(expenseLogPath1)
    println(expenseLogPath2)
    println(projectBookPath)
    println(outputDirPath)
    println(columnNamesPath)

    val fileParser = if (expenseLogPath2.trim().isEmpty()) {
        FileInputParser(expenseLogPath1, null)
    } else {
        FileInputParser(expenseLogPath1, expenseLogPath2)
    }
    val sheetList = fileParser.getAllSheets()

    val teamParser = SheetToTeamParser(sheetList)
    val teams = teamParser.processAndGetTeams()
    // Teams is a hash map with the String key value being the team name and the Team being the team
    // object for the specific Team. This value will be passed to the next step which should be the
    // Team output writer

    // generate team expense breakdown for each team in teams using the lineItemWriter function and format using totalCalculations
    for (team in teams) {
        val lastLine = lineItemWriter(team.value, Paths.get(outputDirPath), "${team.key} Team Expense Breakdown.xlsx")

        // Open the generated .xlsx file
        val filePath = "$outputDirPath/${team.key} Team Expense Breakdown.xlsx"
        val currentWorkbook = XSSFWorkbook(FileInputStream(File(filePath)))
        team.value.totalCharges = totalCalculations(currentWorkbook, 5, lastLine + 1, 6)
        FileOutputStream(filePath).use { outputStream -> currentWorkbook.write(outputStream) }
        currentWorkbook.close()
    }
}
