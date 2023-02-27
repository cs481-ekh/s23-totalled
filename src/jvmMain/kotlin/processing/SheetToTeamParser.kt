package processing

import org.apache.poi.ss.usermodel.Sheet

class SheetToTeamParser (private var sheetList: MutableList<Sheet>) {
    val headingIndiciesMap = HashMap<String, Int>()
    fun populateColumnHeadingMap(){
        for(curSheet in sheetList){
            //Here is where to do stuff
            if(curSheet.firstRowNum <0){
                continue //There is no data in the sheet if firstRowNum is negative
            }
            for((index, curCell) in curSheet.getRow(curSheet.firstRowNum).withIndex()){
                when (curCell.stringCellValue){
                    "senior design po" -> headingIndiciesMap["senior design po"] = index
                    "Business Purpose" -> headingIndiciesMap["Business Purpose"] = index
                    "Total Amount" -> headingIndiciesMap["Total Amount"] = index
                    "Amount 2- Shipping and handling costs. Senior Design Only" ->
                        headingIndiciesMap["Shipping and Handling"] = index
                }
            }
        }
    }
}