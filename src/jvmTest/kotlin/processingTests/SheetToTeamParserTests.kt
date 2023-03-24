package processingTests

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import processing.SheetToTeamParser
import kotlin.test.DefaultAsserter.assertTrue

class SheetToTeamParserTests {

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

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfSheetsGiven_ConstructorCalled_ObjectCreated() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        assertTrue("Constructor failed", parser != null)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfEmptySheetsGiven_populateColumHeadingsCalled_NoErrorsThrown() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        Mockito.`when`(mockSheetList.iterator()).thenAnswer { fakeEmptyMutableList.iterator() }
        Mockito.`when`(mockEmptySheet.firstRowNum).thenReturn(-1)
        parser.populateColumnHeadingMap()
        assert(true)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfFullSheetsGiven_populateColumnHeadingsCalled_HeadingMapPopulated() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        Mockito.`when`(mockSheetList.iterator()).thenAnswer { fakeFullMutableList.iterator() }
        Mockito.`when`(mockFullSheet1.firstRowNum).thenReturn(1)
        Mockito.`when`(mockFullSheet2.firstRowNum).thenReturn(1)
        Mockito.`when`(mockFullSheet1.getRow(1)).thenAnswer { fakeRow1 }
        Mockito.`when`(mockFullSheet2.getRow(1)).thenAnswer { fakeRow2 }
        Mockito.`when`(fakeRow1.iterator()).thenAnswer { fakeCellList1.iterator() }
        Mockito.`when`(fakeRow2.iterator()).thenAnswer { fakeCellList2.iterator() }
        prepCellMock()

        parser.populateColumnHeadingMap()
        if (parser.sheetToHeadingsMap.size == 2) {
            for (sheetSet in parser.sheetToHeadingsMap) {
                if (sheetSet.value.size != 4) {
                    assertTrue(
                        String.format(
                            "Parsing Sheet: %d of %d returned: %d values",
                            sheetSet.key,
                            parser.sheetToHeadingsMap.size - 1,
                            sheetSet.value.size,
                        ),
                        false,
                    )
                }
            }
            assertTrue("This should never be printed", true)
        } else {
            assertTrue("Parsing sheets returned incorrect number of maps", false)
        }
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfMixedSheetsGiven_populateColumnHeadingsCalled_HeadingMapPopulated() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        Mockito.`when`(mockSheetList.iterator()).thenAnswer { fakeMixedMutableList.iterator() }
        Mockito.`when`(mockFullSheet1.firstRowNum).thenReturn(1)
        Mockito.`when`(mockFullSheet2.firstRowNum).thenReturn(1)
        Mockito.`when`(mockEmptySheet.firstRowNum).thenReturn(-1)
        Mockito.`when`(mockFullSheet1.getRow(1)).thenAnswer { fakeRow1 }
        Mockito.`when`(mockFullSheet2.getRow(1)).thenAnswer { fakeRow2 }
        Mockito.`when`(fakeRow1.iterator()).thenAnswer { fakeCellList1.iterator() }
        Mockito.`when`(fakeRow2.iterator()).thenAnswer { fakeCellList2.iterator() }
        prepCellMock()

        parser.populateColumnHeadingMap()
        if (parser.sheetToHeadingsMap.size == 2) {
            for (sheetSet in parser.sheetToHeadingsMap) {
                if (sheetSet.value.size != 4) {
                    assertTrue(
                        String.format(
                            "Parsing Sheet: %d of %d returned: %d values",
                            sheetSet.key,
                            parser.sheetToHeadingsMap.size - 1,
                            sheetSet.value.size,
                        ),
                        false,
                    )
                }
            }
            assertTrue("This should never be printed", true)
        } else {
            assertTrue("Parsing sheets returned incorrect number of maps", false)
        }
    }

    @Test
    @SuppressWarnings("UNCHECKED_CAST")
    fun givenSheetsWithMapFilledOut_filterRowsCalled_properRowsRecorded() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        Mockito.`when`(mockSheetList.iterator()).thenAnswer { fakeSingleMutableList.iterator() }
        Mockito.`when`(mockFullSheet1.firstRowNum).thenReturn(1)
        Mockito.`when`(mockFullSheet1.getRow(1)).thenAnswer { fakeRow1 }
        Mockito.`when`(fakeRow1.iterator()).thenAnswer { fakeCellList1.iterator() }
        prepCellMock()
        parser.populateColumnHeadingMap()

        prepareDataRows()

        Mockito.`when`(mockFullSheet1.getRow(2)).thenReturn(fakeRowData1)
        Mockito.`when`(mockFullSheet1.getRow(3)).thenReturn(fakeRowData2)
        Mockito.`when`(mockFullSheet1.getRow(4)).thenReturn(fakeRowData3)
        Mockito.`when`(mockFullSheet1.getRow(5)).thenReturn(fakeRowData4)
        Mockito.`when`(mockFullSheet1.getRow(6)).thenReturn(fakeBlankRow)
        Mockito.`when`(mockFullSheet1.getRow(7)).thenReturn(fakeBlankRow)
        Mockito.`when`(mockFullSheet1.getRow(8)).thenReturn(fakeBlankRow)

        parser.filterRows()

        assertTrue(
            String.format("Error Filtering rows, got %d expected 4", parser.filteredRowList.size),
            parser.filteredRowList.size == 4,
        )
    }

    @Test
    @SuppressWarnings("UNCHECKED_CAST")
    fun givenSheetWith2BlankRows_filterRowsCalled_properRowsRecorded() {
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>)
        Mockito.`when`(mockSheetList.iterator()).thenAnswer { fakeSingleMutableList.iterator() }
        Mockito.`when`(mockFullSheet1.firstRowNum).thenReturn(1)
        Mockito.`when`(mockFullSheet1.getRow(1)).thenAnswer { fakeRow1 }
        Mockito.`when`(fakeRow1.iterator()).thenAnswer { fakeCellList1.iterator() }
        prepCellMock()
        parser.populateColumnHeadingMap()

        prepareDataRows()

        Mockito.`when`(mockFullSheet1.getRow(2)).thenReturn(fakeRowData1)
        Mockito.`when`(mockFullSheet1.getRow(3)).thenReturn(fakeRowData2)
        Mockito.`when`(mockFullSheet1.getRow(4)).thenReturn(fakeRowData3)
        Mockito.`when`(mockFullSheet1.getRow(5)).thenReturn(fakeBlankRow)
        Mockito.`when`(mockFullSheet1.getRow(6)).thenReturn(fakeBlankRow)
        Mockito.`when`(mockFullSheet1.getRow(7)).thenReturn(fakeRowData4)
        Mockito.`when`(mockFullSheet1.getRow(8)).thenReturn(fakeBlankRow)
        Mockito.`when`(mockFullSheet1.getRow(9)).thenReturn(fakeBlankRow)
        Mockito.`when`(mockFullSheet1.getRow(10)).thenReturn(fakeBlankRow)

        parser.filterRows()

        assertTrue(
            String.format("Error Filtering rows, got %d expected 4", parser.filteredRowList.size),
            parser.filteredRowList.size == 4,
        )
    }

    @Test
    @SuppressWarnings("UNCHECKED_CAST")
    fun givenParserWithFilteredRows_createTeamsCalled_TeamItemsPopulated(){
        val parser = Mockito.mock(SheetToTeamParser::class.java)
        parser.createTeams()
        parser.getTeams()
    }
}
