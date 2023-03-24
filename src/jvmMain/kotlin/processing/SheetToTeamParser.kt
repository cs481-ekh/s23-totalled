package processing

import data.CardType
import data.LineItem
import data.PurchaseType
import data.Team
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

class SheetToTeamParser(private var sheetList: MutableList<Sheet>) {
    private val teamList = HashMap<String, Team>()
    private val tempHeadingIndicesMap = HashMap<String, Int>()
    private val teamListRowMapIndex = mutableListOf<Int>()

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
                    "senior design po" -> tempHeadingIndicesMap["senior design po"] = curCell.columnIndex
                    "business purpose" -> tempHeadingIndicesMap["Business Purpose"] = curCell.columnIndex
                    "total amount" -> tempHeadingIndicesMap["Total Amount"] = curCell.columnIndex
                    // There are technically multiple headings with "amount 2" in each sheet,
                    // so we only want the first one
                    "amount 2" -> { if (!tempHeadingIndicesMap.containsKey("Shipping and Handling")) {
                        tempHeadingIndicesMap["Shipping and Handling"] = curCell.columnIndex } }
                    "card" -> tempHeadingIndicesMap["card"] = curCell.columnIndex
                    "date ordered" -> tempHeadingIndicesMap["date"] = curCell.columnIndex
                    "vendor name" -> tempHeadingIndicesMap["vendor"] = curCell.columnIndex
                }
            }
            sheetToHeadingsMap[index] = HashMap<String, Int>(tempHeadingIndicesMap)
            // Clear current sheets mappings before moving on to the next one
            tempHeadingIndicesMap.clear()
        }
    }

    fun filterRows() {
        // for each sheet we will go from firstRow+1 to first 3 blank rows
        // Each row check if senior design po has data
        // push row into a list
        for ((index, currentSheet) in sheetList.withIndex()) {
            // This will get the current design po column, if null then continues to the next sheet
            val currentSDPColumn = sheetToHeadingsMap[index]?.get("senior design po") ?: continue
            var blankRows = 0
            var currentRow = currentSheet.firstRowNum + 1
            val blankRowSignifier: Short = -1
            while (blankRows < 3) {
                var tempRow = currentSheet.getRow(currentRow)
                if (tempRow.firstCellNum == blankRowSignifier) {
                    blankRows++
                    currentRow++
                    continue
                } else {
                    blankRows = 0
                }
                if (tempRow.getCell(currentSDPColumn).stringCellValue != "") {
                    filteredRowList.add(tempRow)
                    teamListRowMapIndex.add(index)
                }
                currentRow++
            }
        }
    }

    fun createTeams() {
        for ((index, row) in filteredRowList.withIndex()) {
            val currentMap = sheetToHeadingsMap[teamListRowMapIndex[index]]
                ?: throw Exception("Null value found when non null expected")
            val currentTeam = row.getCell(currentMap["senior design po"] ?: -1).stringCellValue
                .substringBefore('-').trim().lowercase()

            if (teamList.containsKey(currentTeam)) {
                val teamObject = teamList[currentTeam] ?: throw Exception("Team object not found in list")
                teamObject.lineItemList.add(newLineItem(row, currentMap))
            } else {
                teamList[currentTeam] = Team(currentTeam, mutableListOf(newLineItem(row, currentMap)))
            }
        }
    }

    private fun newLineItem(row: Row, currentMap: HashMap<String, Int>): LineItem{
        val amount: Double = row.getCell(currentMap["Total Amount"] ?: throw Exception("Error"))
            .numericCellValue
        val amount2: Double = row.getCell(currentMap["amount 2"] ?: throw Exception("Error"))
            .numericCellValue
        val type: String = row.getCell(currentMap["card"] ?: throw Exception("Error"))
            .stringCellValue
        val description: String = row.getCell(currentMap["Business Purpose"] ?: throw Exception("Error"))
            .stringCellValue
        val date: String = row.getCell(currentMap["date"] ?: throw Exception("Error"))
            .stringCellValue
        val vendor: String = row.getCell(currentMap["vendor"] ?: throw Exception("Error"))
            .stringCellValue
        var purchaseType: PurchaseType = PurchaseType.PURCHASE
        var cardType: CardType = CardType.NONE
        var totalTaxable: Double = amount
        var totalNonTaxable: Double = amount2

        when(type){
            "AH" -> cardType = CardType.AH

            "PH" -> cardType = CardType.PH

            "ME" -> cardType = CardType.ME

            "JL" -> cardType = CardType.JL

            "TRV" -> { cardType = CardType.TRV
                purchaseType = PurchaseType.TRAVEL
                totalTaxable = 0.0
                totalNonTaxable += amount }

            "RMB" -> cardType = CardType.RMB

            "NONE" -> { cardType = CardType.NONE
                purchaseType = PurchaseType.SERVICE
                totalTaxable = 0.0
                totalNonTaxable += amount}
        }

        return LineItem(totalTaxable,
            totalNonTaxable,
            description,
            date,
            vendor,
            cardType,
            purchaseType)
    }

    fun getTeams(): HashMap<String, Team> {
        return teamList
    }

}
