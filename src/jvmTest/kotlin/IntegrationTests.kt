import com.github.pjfanning.xlsx.StreamingReader
import org.apache.poi.ss.usermodel.Workbook
import org.junit.jupiter.api.Test
import processing.generateOutput
import java.io.File
import java.io.FileInputStream
import kotlin.test.assertEquals

class IntegrationTests {
    private val dir = System.getProperty("user.dir")
    private val path: String = "$dir/src/jvmTest/TestInputFiles/PrimaryWorkbook.xlsx"
    private val outputPath: String = "$dir/src/jvmTest/TestOutputFiles"
    private val outputFileNames = listOf("$outputPath/me 310 Team Expense Breakdown.xlsx", "$outputPath/vitro Team Expense Breakdown.xlsx")

    @Test
    fun givenSingleWorkbook_GenerateOutputCalled_OutputContainsValidData() {
        generateOutput(path, "", "", outputPath, "")

        var firstWorkbook: Workbook = StreamingReader.builder()
            .rowCacheSize(100)
            .bufferSize(700)
            .open(FileInputStream(File(outputFileNames[0])))

        var secondWorkbook: Workbook = StreamingReader.builder()
            .rowCacheSize(100)
            .bufferSize(700)
            .open(FileInputStream(File(outputFileNames[1])))

        for (sheet in firstWorkbook) {
            for ((index, row) in sheet.withIndex()) {
                when (index) {
                    1 -> {
                        assertEquals("TEAM:", row.getCell(1).stringCellValue)
                        assertEquals("me 310", row.getCell(2).stringCellValue)
                    }
                    3 -> {
                        assertEquals("Card", row.getCell(0).stringCellValue)
                        assertEquals("Travel Total", row.getCell(9).stringCellValue)
                    }
                    4 -> {
                        assertEquals("PH", row.getCell(0).stringCellValue)
                        assertEquals(21.60, row.getCell(6).numericCellValue)
                    }
                    5 -> {
                        assertEquals("PH", row.getCell(0).stringCellValue)
                        assertEquals(83.83, row.getCell(6).numericCellValue)
                    }
                }
            }
        }

        for (sheet in secondWorkbook) {
            for ((index, row) in sheet.withIndex()) {
                when (index) {
                    1 -> {
                        assertEquals("TEAM:", row.getCell(1).stringCellValue)
                        assertEquals("vitro", row.getCell(2).stringCellValue)
                    }
                    3 -> {
                        assertEquals("Card", row.getCell(0).stringCellValue)
                        assertEquals("Travel Total", row.getCell(9).stringCellValue)
                    }
                    4 -> {
                        assertEquals("PH", row.getCell(0).stringCellValue)
                        assertEquals(17.79, row.getCell(6).numericCellValue)
                    }
                    5 -> {
                        assertEquals("PH", row.getCell(0).stringCellValue)
                        assertEquals(64.09, row.getCell(6).numericCellValue)
                    }
                }
            }
        }
    }
}
