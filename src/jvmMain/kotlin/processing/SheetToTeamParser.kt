package processing

import org.apache.poi.ss.usermodel.Sheet

class SheetToTeamParser(private var sheetList: MutableList<Sheet>) {
    private val tempHeadingIndiciesMap = HashMap<String, Int>()

    // Because each sheet may contain different indicies for headings, we will store the map for each sheet
    val sheetToHeadingsMap = HashMap<Int, HashMap<String, Int>>()
    fun populateColumnHeadingMap() {
        for ((index, curSheet) in sheetList.withIndex()) {
            if (curSheet.firstRowNum < 0) {
                continue // There is no data in the sheet if firstRowNum is negative
            }
            for (curCell in curSheet.getRow(curSheet.firstRowNum)) {
                // This is the kotlin equivalent of a switch statement.
                // I am trimming the string to exclude leading or trailing whitespace, be all lowercase
                // and for amount 2 excluding everything after the hyphen as it is the only heading with a hyphen
                when (curCell.stringCellValue.substringBefore('-').lowercase().trim()) {
                    "senior design po" -> tempHeadingIndiciesMap["senior design po"] = curCell.columnIndex
                    "business purpose" -> tempHeadingIndiciesMap["Business Purpose"] = curCell.columnIndex
                    "total amount" -> tempHeadingIndiciesMap["Total Amount"] = curCell.columnIndex
                    // There are technically multiple headings with "amount 2" in each sheet,
                    // so we only want the first one
                    "amount 2" -> { if (!tempHeadingIndiciesMap.containsKey("Shipping and Handling")) {
                        tempHeadingIndiciesMap["Shipping and Handling"] = curCell.columnIndex } }
                }
            }
            sheetToHeadingsMap[index] = HashMap<String, Int>(tempHeadingIndiciesMap)
            // Clear current sheets mappings before moving on to the next one
            tempHeadingIndiciesMap.clear()
        }
    }
}
