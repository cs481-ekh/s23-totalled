package processing

import data.CardType
import data.LineItem
import data.PurchaseType
import data.Team
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.slf4j.LoggerFactory

/**
 * This class will take a list of sheets and process it into Team objects containing LineItems.
 * The expected call order of functions after creating a SheetToTeamParser object is as follows:
 * populateColumnHeadings(), filterRows(), createTeams(), then you can access the teams with
 * getTeams()
 */
class SheetToTeamParser(private var sheetList: MutableList<Sheet>) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    // Just to make this easier to deal with I have it up here
    private val amount2 = "Amount 2- Shipping and Handling Costs. Senior Design Only.".lowercase()

    // Instance Variables to be used in processing
    private val teamList = HashMap<String, Team>()
    private val tempHeadingIndicesMap = HashMap<String, Int>()
    private val shortNegativeOne: Short = -1
    private val teamListRowMapIndex = mutableListOf<Int>()

    // Because each sheet may contain different indices for headings, we will store the map for each sheet
    val sheetToHeadingsMap = HashMap<Int, HashMap<String, Int>>()

    val filteredRowList = mutableListOf<Row>()

    /**
     * @param row A row that contains the column headings for any sheet
     * This function will take the row and populate the tempHeadingIndicesMap to be saved
     * by the populateColumnHeadings function. If you call this function without saving the
     * tempHeadingIndicesMap to another location, you will lose that map.
     */
    private fun useRow(row: Row) {
        for (curCell in row) {
            // logger.info(curCell.stringCellValue.lowercase().trim())
            when (curCell.stringCellValue.lowercase().trim()) {
                "senior design po" -> tempHeadingIndicesMap["senior design po"] = curCell.columnIndex
                "business purpose" -> tempHeadingIndicesMap["Business Purpose"] = curCell.columnIndex
                "total amount" -> tempHeadingIndicesMap["Total Amount"] = curCell.columnIndex
                // There are technically multiple headings with "amount 2" in each sheet,
                // so we only want the first one
                amount2 -> { if (!tempHeadingIndicesMap.containsKey("Shipping and Handling")) {
                    tempHeadingIndicesMap["Shipping and Handling"] = curCell.columnIndex } }
                "card" -> tempHeadingIndicesMap["card"] = curCell.columnIndex
                "date ordered" -> tempHeadingIndicesMap["date"] = curCell.columnIndex
                "vendor name" -> tempHeadingIndicesMap["vendor"] = curCell.columnIndex
            }
        }
        // logger.info("The Final Map for this sheet is $tempHeadingIndicesMap")
    }

    /**
     * This function will take the spreadsheets passed into the constructor and go through them,
     * finding the column headings for the expected columns and saving them to a map in relation
     * to the index of the sheet within the provided sheet list.
     */
    fun populateColumnHeadingMap() {
        var nextSheet = false

        for ((index, curSheet) in sheetList.withIndex()) {
            // logger.info("Current sheet: ${curSheet.sheetName}")
            for (curRow in curSheet) {
                // logger.info("Current Row: ${curRow.rowNum}")
                if (curRow.rowNum > 10 || nextSheet) {
                    nextSheet = false
                    break
                }
                for (curCell in curRow) {
                    // logger.info("Current Cell: ${curCell.stringCellValue} Index: ${curCell.columnIndex}")
                    if (curCell.stringCellValue.contains("enior")) {
                        // logger.info("Senior Design PO Found at: ${curCell.columnIndex} See!: ${curCell.stringCellValue}")
                        useRow(curRow)
                        sheetToHeadingsMap[index] = tempHeadingIndicesMap.clone() as HashMap<String, Int>
                        tempHeadingIndicesMap.clear()
                        nextSheet = true
                        break
                    }
                }
            }
        }
    }

    /**
     * This function will go through the rows in the sheet and filter them out to only
     * be rows that contain data in the Senior Design PO column. This function expects
     * to be called after populateColumnHeadings has been called
     */
    fun filterRows() {
        // for each sheet we will go from firstRow+1 to first 3 blank rows
        // Each row check if senior design po has data
        // push row into a list
        var numRowsScanned = 0
        for ((index, currentSheet) in sheetList.withIndex()) {
            // This will get the current design po column, if null then continues to the next sheet
            // logger.info("SheetToHeadingsMap: $sheetToHeadingsMap")
            val currentSDPColumn = sheetToHeadingsMap[index]!!["senior design po"]
            var blankRows = 0
            // logger.info("Current Sheet: ${currentSheet.sheetName} Index: $index SDPColumn $currentSDPColumn")
            for (tempRow in currentSheet) {
                numRowsScanned++
                // logger.info("First Cell Num: ${tempRow.firstCellNum} at row $numRowsScanned")
                if (tempRow.firstCellNum == shortNegativeOne) {
                    blankRows++
                    if (blankRows < 3) {
                        continue
                    } else {
                        break
                    }
                } else {
                    blankRows = 0
                }
                if (tempRow.getCell(currentSDPColumn!!)?.stringCellValue != "" && !(tempRow.getCell(currentSDPColumn)?.stringCellValue?.contains("esign") ?: continue)) {
                    filteredRowList.add(tempRow)
                    teamListRowMapIndex.add(index)
                }
            }
        }
    }

    /**
     * This method will go through and populate a list with team objects each containing
     * the correct metadata for the team including line items. Calls to this function are
     * expected following calls to populateColumnHeadingsMap and filterRows
     */
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

    /**
     * @param row, The row you wish to create a new line item for
     * @param currentMap, The map of column names to cell indices to be used
     * @return A filled out LineItem
     * This private function is used when creating teams to process each row creating
     * a new LineItem object to be inserted into the Team Object
     */
    private fun newLineItem(row: Row, currentMap: HashMap<String, Int>): LineItem {
        val amount: Double = row.getCell(currentMap["Total Amount"] ?: throw Exception("Error"))
            .stringCellValue.replace("$", "").toDouble()
        val amount2: String = row.getCell(currentMap["Shipping and Handling"] ?: throw Exception("Error"))
            .stringCellValue
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
        var totalNonTaxable: Double
        if (amount2.length > 1) {
            totalNonTaxable = amount2.trim().replace("$", "").toDouble()
        } else {
            totalNonTaxable = 0.0
        }

        when (type) {
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
                totalNonTaxable += amount }
        }
        if (totalNonTaxable != 0.0 && totalTaxable != 0.0) {
            totalTaxable -= totalNonTaxable
        }

        return LineItem(
            totalTaxable,
            totalNonTaxable,
            description,
            date,
            vendor,
            cardType,
            purchaseType,
        )
    }

    fun getTeams(): HashMap<String, Team> {
        return teamList
    }
}
