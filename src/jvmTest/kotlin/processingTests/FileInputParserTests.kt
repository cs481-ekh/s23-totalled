package processingTests
import org.apache.poi.ss.usermodel.Sheet
import org.junit.jupiter.api.Test
import processing.FileInputParser
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertTrue

class FileInputParserTests {
    val path = System.getProperty("user.dir")
    private var primaryPath = "$path/src/jvmTest/TestExcelFiles/PrimaryWorkbook.xlsx"
    private var secondaryPath = primaryPath

    private val primaryList = mutableListOf<String>(
        "July 2021",
        "August 2021",
        "September 2021",
        "October 2021",
        "November 2021",
        "December 2021",
        "January 2022",
        "February 2022",
        "March 2022",
        "April 2022",
        "May 2022",
        "June 2022",
    )

    private val secondaryList = mutableListOf<String>(
        "July 2021",
        "August 2021",
        "September 2021",
        "October 2021",
        "November 2021",
        "December 2021",
        "January 2022",
        "February 2022",
        "March 2022",
        "April 2022",
        "May 2022",
        "June 2022",
        "July 2021",
        "August 2021",
        "September 2021",
        "October 2021",
        "November 2021",
        "December 2021",
        "January 2022",
        "February 2022",
        "March 2022",
        "April 2022",
        "May 2022",
        "June 2022",
    )

    @Test
    fun primaryFile_GivenToParser_CreatesAccurateSheetsList() {
        // Create Parser object with only primary file
        val parser = FileInputParser(primaryPath)

        // Create list using getSheetsFromFile()
        val sheetsList = parser.getAllSheets()

        val sheetNamesList = mutableListOf<String>()

        // Easier to compare two list of strings than it is to compare two differently typed lists
        for (sheet: Sheet in sheetsList) {
            sheetNamesList.add(sheet.sheetName)
        }

        // Assert that the expected size matches up
        assertEquals("Number of sheets from input does not match what is expected!", sheetsList.size, 12)

        // Assert that the contents match
        assertTrue(sheetNamesList.containsAll(primaryList) && primaryList.containsAll(sheetNamesList), "Contents do not match!")

        // Make sure none of the sheets are named "account codes"
        for (sheet: String in sheetNamesList) {
            assertTrue(!sheet.equals("account codes", ignoreCase = true), "List contains account codes sheet!")
        }
    }

    @Test
    fun primaryAndSecondaryFile_GivenToParser_CreatesAccurateSheetsList() {
        // Create Parser object with both primary and secondary files
        val parser = FileInputParser(primaryPath, secondaryPath)

        // Create list using getSheetsFromFile()
        val sheetsList = parser.getAllSheets()

        val sheetNamesList = mutableListOf<String>()

        // Easier to compare two list of strings than it is to compare two differently typed lists
        for (sheet: Sheet in sheetsList) {
            sheetNamesList.add(sheet.sheetName)
        }

        // Assert that the expected size matches up
        assertEquals("Number of sheets from input does not match what is expected!", sheetsList.size, 12)

        // Assert that the contents match
        assertTrue(sheetNamesList.containsAll(primaryList) && primaryList.containsAll(sheetNamesList), "Contents do not match!")

        // Make sure none of the sheets are named "account codes"
        for (sheet: Sheet in sheetsList) {
            assertTrue(!sheet.sheetName.equals("account codes", ignoreCase = true), "List contains account codes sheet!")
        }
    }
}
