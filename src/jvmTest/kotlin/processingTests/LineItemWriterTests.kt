package processingTests

import data.CardType
import data.LineItem
import data.PurchaseType
import data.Team
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import processing.lineItemWriter
import java.nio.file.Paths
import kotlin.test.junit5.JUnit5Asserter.assertEquals

class LineItemWriterTests {
    // fake data yoinked from TeamDataClassTests.kt, thank you Paul!
    private val item1 = LineItem(12.00, 0.00, "Sheet Metal", "1/2/2023", "Home Depot", CardType.AH, PurchaseType.PURCHASE, "team-001")
    private val item2 = LineItem(0.00, 1000.00, "Sponsor Fee", "8/1/2022", "MBE", CardType.NONE, PurchaseType.SERVICE, "team-001")
    private val item3 = LineItem(12.00, 3.50, "Piping", "1/3/23", "PipesUSA.Com", CardType.RMB, PurchaseType.PURCHASE, "team-001")
    private val item4 = LineItem(0.00, 140.0, "Hotel Stay", "12/25/23", "Hilton Garden Inn", CardType.TRV, PurchaseType.TRAVEL, "team-001")
    private val lineItemList = mutableListOf(item1, item2, item3, item4)
    private val teamName = "Team1"

    private lateinit var testTeam: Team

    var outputPath = Paths.get("src/jvmTest/TestOutputFiles")
    var outputFileName = "LineItemWriterTests.xlsx"

    @BeforeEach
    fun init() {
        testTeam = Team(teamName, lineItemList)
        // Call LineItemWriter on testTeam
        lineItemWriter(testTeam, outputPath, outputFileName)
    }

    @Test
    fun sampleTeamObject_LineItemsWritten_ExpectedValuesPopulateExcelSheet() {
        // Ensure cell values are populated as expected
        // Open LineItemWriterTests.xlsx
        val inputStream = outputPath.resolve(outputFileName).toFile().inputStream()
        val workbook = XSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)

        // Assert that the cell values are as expected

        // header cells
        AssertCellAtIndexMatchesValue(sheet, 0, 0, "Fiscal Year 2023 Capstone Design")
        AssertCellAtIndexMatchesValue(sheet, 1, 2, "Team1")
        AssertCellAtIndexMatchesValue(sheet, 1, 5, "Team1 Project")

        // item1
        AssertCellAtIndexMatchesValue(sheet, 4, 0, "AH")
        AssertCellAtIndexMatchesValue(sheet, 4, 1, "team-001")
        AssertCellAtIndexMatchesValue(sheet, 4, 2, "Home Depot")
        AssertCellAtIndexMatchesValue(sheet, 4, 3, "1/2/2023")
        AssertCellAtIndexMatchesValue(sheet, 4, 4, "12.0")
        AssertCellAtIndexMatchesValue(sheet, 4, 5, "Sheet Metal")
        AssertCellAtIndexMatchesValue(sheet, 4, 6, "12.0")
        AssertCellAtIndexMatchesValue(sheet, 4, 7, "0.0")

        // item2
        AssertCellAtIndexMatchesValue(sheet, 5, 0, "NONE")
        AssertCellAtIndexMatchesValue(sheet, 5, 1, "team-001")
        AssertCellAtIndexMatchesValue(sheet, 5, 2, "MBE")
        AssertCellAtIndexMatchesValue(sheet, 5, 3, "8/1/2022")
        AssertCellAtIndexMatchesValue(sheet, 5, 4, "1000.0")
        AssertCellAtIndexMatchesValue(sheet, 5, 5, "Sponsor Fee")
        AssertCellAtIndexMatchesValue(sheet, 5, 8, "1000.0")

        // item3
        AssertCellAtIndexMatchesValue(sheet, 6, 0, "RMB")
        AssertCellAtIndexMatchesValue(sheet, 6, 1, "team-001")
        AssertCellAtIndexMatchesValue(sheet, 6, 2, "PipesUSA.Com")
        AssertCellAtIndexMatchesValue(sheet, 6, 3, "1/3/23")
        AssertCellAtIndexMatchesValue(sheet, 6, 4, "15.5")
        AssertCellAtIndexMatchesValue(sheet, 6, 5, "Piping")
        AssertCellAtIndexMatchesValue(sheet, 6, 6, "12.0")
        AssertCellAtIndexMatchesValue(sheet, 6, 7, "3.5")

        // item4
        AssertCellAtIndexMatchesValue(sheet, 7, 0, "TRV")
        AssertCellAtIndexMatchesValue(sheet, 7, 1, "team-001")
        AssertCellAtIndexMatchesValue(sheet, 7, 2, "Hilton Garden Inn")
        AssertCellAtIndexMatchesValue(sheet, 7, 3, "12/25/23")
        AssertCellAtIndexMatchesValue(sheet, 7, 4, "140.0")
        AssertCellAtIndexMatchesValue(sheet, 7, 5, "Hotel Stay")
        AssertCellAtIndexMatchesValue(sheet, 7, 9, "140.0")
    }

    fun AssertCellAtIndexMatchesValue(sheet: Sheet, rowIndx: Int, colIdx: Int, value: String) {
        val cell = sheet.getRow(rowIndx).getCell(colIdx)
        assertEquals("Expected $value, but got ${cell.stringCellValue} instead!", cell.stringCellValue, value)
    }
}
