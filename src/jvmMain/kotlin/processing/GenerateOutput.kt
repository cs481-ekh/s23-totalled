package processing

import org.apache.poi.ss.usermodel.Sheet

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

    //This should probably be changes and the expenseLogPath2 should have some sort of nullable value
    //or change this to have it be a lateInit...
    val fileParser = FileInputParser(expenseLogPath1, expenseLogPath2)
    val sheetList = fileParser.getAllSheets()


    val teamParser = SheetToTeamParser(sheetList)
    val teams = teamParser.processAndGetTeams()
    //Teams is a hash map with the String key value being the team name and the Team being the team
    //object for the specific Team. This value will be passed to the next step which should be the
    //Team output writer

    Thread.sleep(1000)
    throw Exception("Output writing not implemented, parsing has been implemented and ${teams.size} teams found")
}
