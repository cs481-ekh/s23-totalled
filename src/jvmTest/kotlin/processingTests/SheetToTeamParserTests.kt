package processingTests

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
    private val path: String = "$dir/src/jvmTest/TestInputFiles/PrimaryWorkbook.xlsx"
    private val fip = FileInputParser(path)
    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val mockSheetList = Mockito.mock(MutableList::class.java)
    private val expenseLogInternalColumnNames = listOf(
        "Senior Design PO",
        "Business Purpose",
        "Total Amount",
        "Amount 2- Shipping and Handling Costs. Senior Design Only.",
        "Card",
        "Date Ordered",
        "Vendor Name",
    )

    private val headingsMap = (expenseLogInternalColumnNames zip expenseLogInternalColumnNames).toMap()

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfSheetsGiven_ConstructorCalled_ObjectCreated() {
        logger.info("$dir $path")
        val parser = SheetToTeamParser(mockSheetList as MutableList<Sheet>, headingsMap)
        assertTrue("Constructor failed", parser != null)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun listOfEmptySheetsGiven_populateColumHeadingsCalled_NoErrorsThrown() {
        val parser = SheetToTeamParser(mutableListOf(), headingsMap)

        parser.populateColumnHeadingMap()
        assert(true)
    }

    @Test
    fun listOfSheetsGiven_populateColumnHeadingsCalled_HeadingsMapPopulatedNoMocks() {
        val parser = SheetToTeamParser(fip.getAllSheets(), headingsMap)

        parser.populateColumnHeadingMap()
        assertEquals("Error Wrong Number of sheets with Senior Design PO found", 12, parser.sheetToHeadingsMap.size)
    }

    @Test
    fun givenSheetsWithMapFilledOut_filterRowsCalled_ProperRowsRecorded() {
        val parser = SheetToTeamParser(fip.getAllSheets(), headingsMap)
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
        val parser = SheetToTeamParser(fip.getAllSheets(), headingsMap)
        parser.populateColumnHeadingMap()
        parser.filterRows()

        parser.createTeams()
        val teamMap = parser.getTeams()
//        for ((teamName, teamObject) in teamMap) {
//            logger.info(teamName)
//        }
        assertTrue("Incorrect Number of Teams Received, got ${teamMap.size} expected 12", 12 == teamMap.size)
    }

    @Test
    @SuppressWarnings("UNCHECKED_CAST")
    fun givenNewParser_processAndGetTeamsCalled_TeamItemsPopulated() {
        val parser = SheetToTeamParser(fip.getAllSheets(), headingsMap)

        val teamMap = parser.processAndGetTeams()

        assertTrue("Incorrect Number of Teams Received, got ${teamMap.size} expected 12", 12 == teamMap.size)
    }
}
