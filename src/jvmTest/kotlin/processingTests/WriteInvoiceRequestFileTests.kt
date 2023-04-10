package processingTests

import data.ProjectMetadata
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Test
import processing.writeInvoiceRequestFile
import java.nio.file.Paths
import kotlin.test.junit5.JUnit5Asserter

class WriteInvoiceRequestFileTests {
    private val path = System.getProperty("user.dir")
    private val outputPathString = "$path/src/jvmTest/TestOutputFiles"
    private val outputPath = Paths.get(outputPathString)
    private val outputFileName = "InvoiceRequest.xlsx"

    private val projectList = listOf(
        ProjectMetadata(
            teamAbbr = "JBL",
            billTo = "JB laser",
            email = "wittylettuce@wittylettuce.com",
            contactInfo = "Witty Lettuce",
            addressLine1 = "JB laser",
            addressLine2 = "4129 Plant Way",
            city = "Boise",
            state = "ID",
            zip = "83709",
            amount = 666.66,
        ),
        ProjectMetadata(
            teamAbbr = "NLG",
            billTo = "NLG LLC",
            email = "z@zezzel.com, Wonderful.Broccoli@gmail.com",
            contactInfo = "Wonderful Broccoli",
            addressLine1 = "",
            addressLine2 = "",
            city = "",
            state = "",
            zip = "",
            amount = 7.27,
        ),
        ProjectMetadata(
            teamAbbr = "LUNAR",
            billTo = "Visioneering Space Corp",
            email = "strategic.blueberry@visioneeringspace.com",
            contactInfo = "Strategic Blueberry",
            addressLine1 = "Visioneering Space Corp",
            addressLine2 = "3380 W Country Platform, Suite 110",
            city = "Boise",
            state = "ID",
            zip = "83706",
            amount = 3560.03,
        ),
        ProjectMetadata(
            teamAbbr = "CODEX",
            billTo = "Visioneering Space Corp",
            email = "lively.carrot@visioneeringspace.com",
            contactInfo = "Lively Carrot",
            addressLine1 = "Visioneering Space Corp",
            addressLine2 = "3380 W Country Platform, Suite 110",
            city = "Boise",
            state = "ID",
            zip = "83706",
            amount = 1000.0,
        ),
    )

    @Test
    fun givenListOfProjects_whenWriteInvoiceRequestFileCalled_thenCorrectFileIsGenerated() {
        writeInvoiceRequestFile(outputPathString, projectList)
        val inputStream = outputPath.resolve(outputFileName).toFile().inputStream()
        val workbook = XSSFWorkbook(inputStream)
        val sheet = workbook.getSheetAt(0)

        // Check some headings
        assertCellAtIndexMatchesValue(sheet, 0, 'a'.asColNum, "Invoice number")
        assertCellAtIndexMatchesValue(sheet, 0, 'b'.asColNum, "Billing Date")
        assertCellAtIndexMatchesValue(sheet, 0, 'q'.asColNum, "Total")

        // Check first project
        assertCellAtIndexMatchesValue(sheet, 2, 'a'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 2, 'c'.asColNum, "JB laser")
        assertCellAtIndexMatchesValue(sheet, 2, 'e'.asColNum, "wittylettuce@wittylettuce.com")
        assertCellAtIndexMatchesValue(sheet, 2, 'f'.asColNum, "Witty Lettuce")
        assertCellAtIndexMatchesValue(sheet, 2, 'g'.asColNum, "JB laser")
        assertCellAtIndexMatchesValue(sheet, 2, 'h'.asColNum, "4129 Plant Way")
        assertCellAtIndexMatchesValue(sheet, 2, 'i'.asColNum, "Boise")
        assertCellAtIndexMatchesValue(sheet, 2, 'j'.asColNum, "ID")
        assertCellAtIndexMatchesValue(sheet, 2, 'k'.asColNum, "83709")
        assertCellAtIndexMatchesValue(sheet, 2, 'n'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 2, 'o'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 2, 'p'.asColNum, 666.66)
        assertCellAtIndexMatchesValue(sheet, 2, 'q'.asColNum, 666.66)

        // Check second project
        assertCellAtIndexMatchesValue(sheet, 6, 'a'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 6, 'c'.asColNum, "NLG LLC")
        assertCellAtIndexMatchesValue(sheet, 6, 'e'.asColNum, "z@zezzel.com, Wonderful.Broccoli@gmail.com")
        assertCellAtIndexMatchesValue(sheet, 6, 'f'.asColNum, "Wonderful Broccoli")
        assertCellAtIndexMatchesValue(sheet, 6, 'g'.asColNum, "")
        assertCellAtIndexMatchesValue(sheet, 6, 'h'.asColNum, "")
        assertCellAtIndexMatchesValue(sheet, 6, 'i'.asColNum, "")
        assertCellAtIndexMatchesValue(sheet, 6, 'j'.asColNum, "")
        assertCellAtIndexMatchesValue(sheet, 6, 'k'.asColNum, "")
        assertCellAtIndexMatchesValue(sheet, 6, 'n'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 6, 'o'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 6, 'p'.asColNum, 7.27)
        assertCellAtIndexMatchesValue(sheet, 6, 'q'.asColNum, 7.27)

        // Check third project
        assertCellAtIndexMatchesValue(sheet, 10, 'a'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 10, 'c'.asColNum, "Visioneering Space Corp")
        assertCellAtIndexMatchesValue(sheet, 10, 'e'.asColNum, "strategic.blueberry@visioneeringspace.com")
        assertCellAtIndexMatchesValue(sheet, 10, 'f'.asColNum, "Strategic Blueberry")
        assertCellAtIndexMatchesValue(sheet, 10, 'g'.asColNum, "Visioneering Space Corp")
        assertCellAtIndexMatchesValue(sheet, 10, 'h'.asColNum, "3380 W Country Platform, Suite 110")
        assertCellAtIndexMatchesValue(sheet, 10, 'i'.asColNum, "Boise")
        assertCellAtIndexMatchesValue(sheet, 10, 'j'.asColNum, "ID")
        assertCellAtIndexMatchesValue(sheet, 10, 'k'.asColNum, "83706")
        assertCellAtIndexMatchesValue(sheet, 10, 'n'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 10, 'o'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 10, 'p'.asColNum, 3560.03)
        assertCellAtIndexMatchesValue(sheet, 10, 'q'.asColNum, 3560.03)

        // Check fourth project
        assertCellAtIndexMatchesValue(sheet, 14, 'a'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 14, 'c'.asColNum, "Visioneering Space Corp")
        assertCellAtIndexMatchesValue(sheet, 14, 'e'.asColNum, "lively.carrot@visioneeringspace.com")
        assertCellAtIndexMatchesValue(sheet, 14, 'f'.asColNum, "Lively Carrot")
        assertCellAtIndexMatchesValue(sheet, 14, 'g'.asColNum, "Visioneering Space Corp")
        assertCellAtIndexMatchesValue(sheet, 14, 'h'.asColNum, "3380 W Country Platform, Suite 110")
        assertCellAtIndexMatchesValue(sheet, 14, 'i'.asColNum, "Boise")
        assertCellAtIndexMatchesValue(sheet, 14, 'j'.asColNum, "ID")
        assertCellAtIndexMatchesValue(sheet, 14, 'k'.asColNum, "83706")
        assertCellAtIndexMatchesValue(sheet, 14, 'n'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 14, 'o'.asColNum, "FILL OUT")
        assertCellAtIndexMatchesValue(sheet, 14, 'p'.asColNum, 1000.00)
        assertCellAtIndexMatchesValue(sheet, 14, 'q'.asColNum, 1000.00)

        // check some colors
        checkSeparatorRow(sheet, 4)
        checkSeparatorRow(sheet, 8)
        checkSeparatorRow(sheet, 12)

        // check that the last row doesn't have a separator
        val cell = sheet.getRow(16).getCell('a'.asColNum)
        JUnit5Asserter.assertEquals(
            "Expected null, but got ${cell.cellStyle.fillBackgroundXSSFColor} instead!",
            null,
            cell.cellStyle.fillBackgroundXSSFColor,
        )
    }

    private fun assertCellAtIndexMatchesValue(sheet: Sheet, rowIdx: Int, colIdx: Int, value: String) {
        val cell = sheet.getRow(rowIdx).getCell(colIdx)
        JUnit5Asserter.assertEquals(
            "Expected $value, but got ${cell.stringCellValue} instead!",
            value,
            cell.stringCellValue,
        )
    }
    private fun assertCellAtIndexMatchesValue(sheet: Sheet, rowIdx: Int, colIdx: Int, value: Double) {
        val cell = sheet.getRow(rowIdx).getCell(colIdx)
        JUnit5Asserter.assertEquals(
            "Expected $value, but got ${cell.numericCellValue} instead!",
            value,
            cell.numericCellValue,
        )
    }

    private fun checkSeparatorRow(sheet: XSSFSheet, rowIdx: Int) {
        val row = sheet.getRow(rowIdx)
        for (i in 0..'q'.asColNum) {
            val cell = row.getCell(i)
            // background should be bright yellow
            JUnit5Asserter.assertEquals(
                "Expected FFFFFF00, but got ${cell.cellStyle.fillBackgroundXSSFColor.argbHex} instead!",
                "FFFFFF00",
                cell.cellStyle.fillBackgroundXSSFColor.argbHex,
            )
            // should be empty
            JUnit5Asserter.assertEquals(
                "Expected nothing, but got ${cell.stringCellValue} instead!",
                "",
                cell.stringCellValue,
            )
        }
    }

    private val Char.asColNum: Int
        get() = this.code - 97
}
