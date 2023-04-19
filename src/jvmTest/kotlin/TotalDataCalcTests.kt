
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import processing.totalCalculations
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class TotalDataCalcTests {
    private val path = System.getProperty("user.dir")
    private val file: File = File("$path/src/jvmTest/TestExcelFiles/", "TotalCalcWorkbook.xlsx")
    private lateinit var tempFile: File
    private lateinit var fIP: java.io.FileInputStream
    private lateinit var wb: XSSFWorkbook
    private lateinit var sheet: Sheet
    private lateinit var totalStyle: CellStyle
    private var merchCellTotal: Double = 0.0
    private var shipCellTotal: Double = 0.0
    private var servCellTotal: Double = 0.0
    private var travelCellTotal: Double = 0.0
    private var completeTotal: Double = 0.0
    private var expectedToString: String = ""
    private var actualToString: String = ""

    @BeforeTest
    fun initVars() {
        // create dummy file to write to
        tempFile = File.createTempFile("tempTotalCalcWorkbook", ".xlsx", File("$path/src/jvmTest/TestExcelFiles/"))
        // copy formatting of supplied template file
        file.copyTo(tempFile, true)
        // Intiate streams and workbook info
        fIP = java.io.FileInputStream(tempFile)
        wb = XSSFWorkbook(fIP)
        sheet = wb.getSheetAt(0)
        totalStyle = sheet.getRow(5).getCell(6).cellStyle
        merchCellTotal = 0.0
        shipCellTotal = 0.0
        servCellTotal = 0.0
        travelCellTotal = 0.0
        completeTotal = 0.0
        expectedToString = " "
        actualToString = ""
        // return tempFile
    }

    @Test
    fun normalDataValues_totalCalculationsDetermined_resultingValuesNormal() {
        // fill cells with dummy data
        for (i in 1..5) {
            val currRow = sheet.createRow(4 + i)
            val merchCell = currRow.createCell(6)
            merchCell.cellStyle = totalStyle
            val shipCell = currRow.createCell(7)
            shipCell.cellStyle = totalStyle
            val servCell = currRow.createCell(8)
            servCell.cellStyle = totalStyle
            val travelCell = currRow.createCell(9)
            travelCell.cellStyle = totalStyle
            merchCell.setCellValue(Random.nextInt(0, 3000).toDouble())
            shipCell.setCellValue(Random.nextInt(0, 3000).toDouble())
            servCell.setCellValue(Random.nextInt(0, 3000).toDouble())
            travelCell.setCellValue(Random.nextInt(0, 3000).toDouble())
            merchCellTotal += merchCell.numericCellValue
            shipCellTotal += shipCell.numericCellValue
            servCellTotal += servCell.numericCellValue
            travelCellTotal += travelCell.numericCellValue
        }
        // calculate cell formula values
        val tempTax = merchCellTotal * 0.06
        val taxTotal = "%.2f".format(tempTax).toDouble()
        val tempComplete = merchCellTotal + shipCellTotal + servCellTotal + travelCellTotal + taxTotal
        val completeTotal = "%.2f".format(tempComplete).toDouble()
        expectedToString = " $merchCellTotal $shipCellTotal $servCellTotal $travelCellTotal $taxTotal $completeTotal"
        // run function to test
        totalCalculations(wb, 5, 10, 6)
        val eval = wb.creationHelper.createFormulaEvaluator()
        val fileOutputStream = FileOutputStream(tempFile)
        // get formula calculated values
        for (i in 1..4) {
            actualToString = "$actualToString ${eval.evaluateInCell(wb.getSheetAt(0).getRow(12).getCell(5 + i))}"
            wb.write(fileOutputStream)
        }
        actualToString = "$actualToString ${eval.evaluateInCell(wb.getSheetAt(0).getRow(13).getCell(6))}"
        actualToString = "$actualToString ${eval.evaluateInCell(wb.getSheetAt(0).getRow(15).getCell(6))}"
        wb.close()
        fileOutputStream.close()

        assertEquals(expectedToString, actualToString, "Base case Failed")
    }

    @Test
    fun noDataValues_totalCalculationsDetermined_resultingValuesZero() {
        // calculate cell formula values
        completeTotal = merchCellTotal + shipCellTotal + servCellTotal + travelCellTotal + merchCellTotal * 0.06
        expectedToString = " " + merchCellTotal.toString() + " " + shipCellTotal + " " + servCellTotal + " " + travelCellTotal + " " + (merchCellTotal * 0.06) + " " + completeTotal
        // run function to test
        totalCalculations(wb, 5, 6, 6)
        val eval = wb.creationHelper.createFormulaEvaluator()
        val fileOutputStream = FileOutputStream(tempFile)
        // get formula calculated values
        for (i in 1..4) {
            actualToString = actualToString + " " + eval.evaluateInCell(wb.getSheetAt(0).getRow(8).getCell(5 + i))
            wb.write(fileOutputStream)
        }
        actualToString = actualToString + " " + eval.evaluateInCell(wb.getSheetAt(0).getRow(9).getCell(6))
        actualToString = actualToString + " " + eval.evaluateInCell(wb.getSheetAt(0).getRow(11).getCell(6))
        wb.close()
        fileOutputStream.close()

        assertEquals(expectedToString, actualToString, "Base case Failed")
    }

    companion object {
        @AfterAll
        @JvmStatic
        // remove/replace temporary files created by tests
        internal fun removeTempFiles() {
            val path = System.getProperty("user.dir")
            val directory = Paths.get("$path/src/jvmTest/TestExcelFiles/")
            val stream = Files.newDirectoryStream(directory)
            for (f in stream) {
                if (f.name.contains("tempTotalCalcWorkbook")) {
                    File(f.toFile().toURI()).delete()
                }
            }
        }
    }
}
