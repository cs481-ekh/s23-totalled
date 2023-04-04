package processingTests

import data.CardType
import data.LineItem
import data.PurchaseType
import data.Team
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import processing.lineItemWriter
import java.nio.file.Paths

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
    }

    @Test
    fun sampleTeamObject_LineItemsWritten_ExpectedValuesPopulateExcelSheet() {
        // Call LineItemWriter on testTeam
        lineItemWriter(testTeam, outputPath, outputFileName)

        // Ensure cell values are populated as expected
    }
}
