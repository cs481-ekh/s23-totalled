package processing

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

class SheetToTeamParser(private var sheetList: MutableList<Sheet>) {
    private val tempHeadingIndiciesMap = HashMap<String, Int>()

    // Because each sheet may contain different indicies for headings, we will store the map for each sheet
    val sheetToHeadingsMap = HashMap<Int, HashMap<String, Int>>()

    val filteredRowList = mutableListOf<Row>()
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

    fun filterRows(){

        //for each sheet we will go from firstRow+1 to first 3 blank rows
            //Each row check if senior design po has data
                // push row into a list
        for((index, currentSheet) in sheetList.withIndex()){
            //This will get the current design po column, if null then continues to the next sheet
            val currentSDPColumn = sheetToHeadingsMap.get(index)?.get("senior design po") ?: continue
            var blankRows = 0;
            var currentRow = currentSheet.firstRowNum+1
            while (blankRows<3){
                val tempRow = currentSheet.getRow(currentRow)
                if(tempRow.firstCellNum.equals(-1)){
                    blankRows++
                    continue
                } else{
                    blankRows = 0
                }
                if (tempRow.getCell(currentSDPColumn) != null){
                    filteredRowList.add(tempRow)
                }
            }
        }
    }
}
