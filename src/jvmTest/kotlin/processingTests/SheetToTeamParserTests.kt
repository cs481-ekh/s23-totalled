package processingTests

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory
import processing.FileInputParser
import processing.SheetToTeamParser
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertTrue

class SheetToTeamParserTests {

    private val dir = System.getProperty("user.dir")
    private val path: String = "$dir/src/jvmTest/TestExcelFiles/PrimaryWorkbook.xlsx"
    private val fip = FileInputParser(path)
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val mockSheetList = Mockito.mock(MutableList::class.java)
    private val mockEmptySheet = Mockito.mock(Sheet::class.java)
    private val mockFullSheet1 = Mockito.mock(Sheet::class.java)
    private val mockFullSheet2 = Mockito.mock(Sheet::class.java)
    private val fakeEmptyMutableList = mutableListOf<Sheet>(mockEmptySheet, mockEmptySheet, mockEmptySheet)
    private val fakeFullMutableList = mutableListOf<Sheet>(mockFullSheet1, mockFullSheet2)
    private val fakeMixedMutableList = mutableListOf<Sheet>(mockFullSheet2, mockEmptySheet, mockFullSheet1)
    private val fakeSingleMutableList = mutableListOf<Sheet>(mockFullSheet1)
    private val fakeRow1 = Mockito.mock(Row::class.java)
    private val fakeRow2 = Mockito.mock(Row::class.java)
    private val fakeCell1 = Mockito.mock(Cell::class.java)
    private val fakeCell2 = Mockito.mock(Cell::class.java)
    private val fakeCell3 = Mockito.mock(Cell::class.java)
    private val fakeCell4 = Mockito.mock(Cell::class.java)
    private val fakeCellList1 = mutableListOf<Cell>(fakeCell1, fakeCell2, fakeCell3, fakeCell4)
    private val fakeCellList2 = mutableListOf<Cell>(fakeCell4, fakeCell3, fakeCell2, fakeCell1)
    private val fakeRowData1 = Mockito.mock(Row::class.java)
    private val fakeRowData2 = Mockito.mock(Row::class.java)
    private val fakeRowData3 = Mockito.mock(Row::class.java)
    private val fakeRowData4 = Mockito.mock(Row::class.java)
    private val fakeData1 = Mockito.mock(Cell::class.java)
    private val fakeData2 = Mockito.mock(Cell::class.java)
    private val fakeData3 = Mockito.mock(Cell::class.java)
    private val fakeData4 = Mockito.mock(Cell::class.java)
    private val fakeData5 = Mockito.mock(Cell::class.java)
    private val fakeData6 = Mockito.mock(Cell::class.java)
    private val fakeData7 = Mockito.mock(Cell::class.java)
    private val fakeData8 = Mockito.mock(Cell::class.java)
    private val fakeData9 = Mockito.mock(Cell::class.java)
    private val fakeData10 = Mockito.mock(Cell::class.java)
    private val fakeData11 = Mockito.mock(Cell::class.java)
    private val fakeData12 = Mockito.mock(Cell::class.java)
    private val fakeData13 = Mockito.mock(Cell::class.java)
    private val fakeData14 = Mockito.mock(Cell::class.java)
    private val fakeData15 = Mockito.mock(Cell::class.java)
    private val fakeData16 = Mockito.mock(Cell::class.java)
    private val fakeBlankRow = Mockito.mock(Row::class.java)
    private val filteredRow1 = Mockito.mock(Row::class.java)
    private val filteredRow2 = Mockito.mock(Row::class.java)
    private val mockFilteredRowListIterator = mutableListOf<Row>(filteredRow1, filteredRow2)
    private val mockHeadingsMap = HashMap<Int, HashMap<String, Int>>()
    private val mockMap = HashMap<String, Int>()

    private fun prepCellMock() {
        Mockito.`when`(fakeCell1.stringCellValue).thenReturn("Senior Design PO ")
        Mockito.`when`(fakeCell2.stringCellValue).thenReturn("Business purpose ")
        Mockito.`when`(fakeCell3.stringCellValue).thenReturn("Amount 2-shipping and handling ")
        Mockito.`when`(fakeCell4.stringCellValue).thenReturn("Total Amount ")
        Mockito.`when`(fakeCell1.columnIndex).thenReturn(1)
        Mockito.`when`(fakeCell2.columnIndex).thenReturn(2)
        Mockito.`when`(fakeCell3.columnIndex).thenReturn(3)
        Mockito.`when`(fakeCell4.columnIndex).thenReturn(4)
    }

    private fun prepareDataRows() {
        Mockito.`when`(fakeRowData1.firstCellNum).thenReturn(1)
        Mockito.`when`(fakeRowData1.getCell(1)).thenReturn(fakeData1)
        Mockito.`when`(fakeRowData2.firstCellNum).thenReturn(1)
        Mockito.`when`(fakeRowData2.getCell(1)).thenReturn(fakeData5)
        Mockito.`when`(fakeRowData3.firstCellNum).thenReturn(1)
        Mockito.`when`(fakeRowData3.getCell(1)).thenReturn(fakeData9)
        Mockito.`when`(fakeRowData4.firstCellNum).thenReturn(1)
        Mockito.`when`(fakeRowData4.getCell(1)).thenReturn(fakeData13)
        Mockito.`when`(fakeData1.stringCellValue).thenReturn("team1")
        Mockito.`when`(fakeData2.stringCellValue).thenReturn("purchase 1")
        Mockito.`when`(fakeData3.stringCellValue).thenReturn("")
        Mockito.`when`(fakeData4.stringCellValue).thenReturn("123.54")
        Mockito.`when`(fakeData5.stringCellValue).thenReturn("team2")
        Mockito.`when`(fakeData6.stringCellValue).thenReturn("purchase 2")
        Mockito.`when`(fakeData7.stringCellValue).thenReturn("")
        Mockito.`when`(fakeData8.stringCellValue).thenReturn("123.00")
        Mockito.`when`(fakeData9.stringCellValue).thenReturn("team3")
        Mockito.`when`(fakeData10.stringCellValue).thenReturn("purchase 3")
        Mockito.`when`(fakeData11.stringCellValue).thenReturn("")
        Mockito.`when`(fakeData12.stringCellValue).thenReturn("7765.00")
        Mockito.`when`(fakeData13.stringCellValue).thenReturn("team4")
        Mockito.`when`(fakeData14.stringCellValue).thenReturn("purchase 4")
        Mockito.`when`(fakeData15.stringCellValue).thenReturn("23.0")
        Mockito.`when`(fakeData16.stringCellValue).thenReturn("321.11")
        Mockito.`when`(fakeBlankRow.firstCellNum).thenReturn(-1)
    }

    private fun prepFilteredRowList() {
        Mockito.`when`(filteredRow1.getCell(Mockito.anyInt())).thenReturn(Mockito.mock(Cell::class.java))
        Mockito.`when`(filteredRow2.getCell(Mockito.anyInt())).thenReturn(Mockito.mock(Cell::class.java))
        Mockito.`when`(filteredRow1.getCell(0).stringCellValue).thenReturn("PO-01") // Return a string value
        Mockito.`when`(filteredRow2.getCell(0).stringCellValue).thenReturn("PO-02") // Return a string value
        Mockito.`when`(filteredRow1.getCell(1).stringCellValue).thenReturn("$123") // Return a string value
        Mockito.`when`(filteredRow2.getCell(1).stringCellValue).thenReturn("$234") // Return a string value
        Mockito.`when`(filteredRow1.getCell(2).stringCellValue).thenReturn("$12") // Return a string value
        Mockito.`when`(filteredRow2.getCell(2)).thenReturn(null) // Return a string value
        Mockito.`when`(filteredRow1.getCell(3).stringCellValue).thenReturn("AH") // Return a string value
        Mockito.`when`(filteredRow2.getCell(3).stringCellValue).thenReturn("JL") // Return a string value
        Mockito.`when`(filteredRow1.getCell(4).stringCellValue).thenReturn("Purchase of Stuff") // Return a string value
        Mockito.`when`(filteredRow2.getCell(4).stringCellValue).thenReturn("Amazon Order") // Return a string value
        Mockito.`when`(filteredRow1.getCell(5).stringCellValue).thenReturn("5/25/2022") // Return a string value
        Mockito.`when`(filteredRow2.getCell(5).stringCellValue).thenReturn("5/22/2023") // Return a string value
        Mockito.`when`(filteredRow1.getCell(6).stringCellValue).thenReturn("StuffCentral") // Return a string value
        Mockito.`when`(filteredRow2.getCell(6).stringCellValue).thenReturn("Amazon") // Return a string value
    }
    private fun prepHeadingsMap() {
        mockMap["senior design po"] = 0
        mockMap["Total Amount"] = 1
        mockMap["amount 2"] = 2
        mockMap["card"] = 3
        mockMap["Business Purpose"] = 4
        mockMap["date"] = 5
        mockMap["vendor"] = 6

        mockHeadingsMap[0] = mockMap
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfSheetsGiven_ConstructorCalled_ObjectCreated() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        assertTrue("Constructor failed", parser != null)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfEmptySheetsGiven_populateColumHeadingsCalled_NoErrorsThrown() {
        val parser = SheetToTeamParser(mutableListOf())

        parser.populateColumnHeadingMap()
        assert(true)
    }

    @Test
    fun listOfSheetsGiven_populateColumnHeadingsCalled_HeadingsMapPopulatedNoMocks() {
        val parser = SheetToTeamParser(fip.getAllSheets())

        parser.populateColumnHeadingMap()
        assertEquals("Error Wrong Number of sheets with Senior Design PO found", 12, parser.sheetToHeadingsMap.size)
    }

    @Test
    fun givenSheetsWithMapFilledOut_filterRowsCalled_ProperRowsRecorded() {
        val parser = SheetToTeamParser(fip.getAllSheets())
        parser.populateColumnHeadingMap()
        parser.filterRows()
//        for (row in parser.filteredRowList) {
//            val builder = StringBuilder()
//            for (cell in row) {
//                builder.append(cell.stringCellValue)
//                builder.append(" ")
//            }
//            logger.info(builder.toString())
//        }

        assertEquals("Unexpected Number of Rows Returned", 50, parser.filteredRowList.size)
    }

    @Test
    @SuppressWarnings("UNCHECKED_CAST")
    fun givenParserWithFilteredRows_createTeamsCalled_TeamItemsPopulated() {
        val parser = SheetToTeamParser(fip.getAllSheets())
        parser.populateColumnHeadingMap()
        parser.filterRows()

        parser.createTeams()
        val teamMap = parser.getTeams()
        for ((teamName, teamObject) in teamMap) {
            logger.info(teamName)
        }
    }
}
