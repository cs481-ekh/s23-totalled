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
    private val fakeRow1 = Mockito.mock(Row::class.java)
    private val fakeRow2 = Mockito.mock(Row::class.java)
    private val fakeCell1 = Mockito.mock(Cell::class.java)
    private val fakeCell2 = Mockito.mock(Cell::class.java)
    private val fakeCell3 = Mockito.mock(Cell::class.java)
    private val fakeCell4 = Mockito.mock(Cell::class.java)
    private val fakeCellList1 = mutableListOf<Cell>(fakeCell1, fakeCell2, fakeCell3, fakeCell4)
    private val fakeCellList2 = mutableListOf<Cell>(fakeCell4, fakeCell3, fakeCell2, fakeCell1)

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
}
