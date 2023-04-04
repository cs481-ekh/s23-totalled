
import org.apache.poi.ss.usermodel.DataFormat
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import processing.totalCalculations
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class TotalDataCalcTests {
    private val path = System.getProperty("user.dir")
    private val file: File = File("$path/src/jvmTest/TestExcelFiles/", "TotalCalcWorkbook.xlsx")
    private lateinit var tempFile: File // = File("$path/src/jvmTest/TestExcelFiles/", "tempTotalCalcWorkbook.xlsx")
    private lateinit var fIP: java.io.FileInputStream
    private lateinit var wb: XSSFWorkbook
    private lateinit var sheet: Sheet
    private lateinit var dataFormat: DataFormat
    private lateinit var dollarStyle: XSSFCellStyle
    private var merchCellTotal: Double = 0.0
    private var shipCellTotal: Double = 0.0
    private var servCellTotal: Double = 0.0
    private var travelCellTotal: Double = 0.0
    private var completeTotal: Double = 0.0
    private var expectedToString: String = ""
    private var actualToString: String = ""

    @BeforeTest
    fun initVars() {
        // reset the
        tempFile = File.createTempFile("tempTotalCalcWorkbook", ".xlsx", File("$path/src/jvmTest/TestExcelFiles/"))
        file.copyTo(tempFile, true)
        fIP = java.io.FileInputStream(tempFile)
        wb = XSSFWorkbook(fIP)
        sheet = wb.getSheetAt(0)
        dataFormat = wb.createDataFormat()
        dollarStyle = wb.createCellStyle()
        dollarStyle.dataFormat = dataFormat.getFormat("$#,#0.00")
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
            merchCell.cellStyle = dollarStyle
            val shipCell = currRow.createCell(7)
            shipCell.cellStyle = dollarStyle
            val servCell = currRow.createCell(8)
            servCell.cellStyle = dollarStyle
            val travelCell = currRow.createCell(9)
            travelCell.cellStyle = dollarStyle
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
        // get formula calculated values
        for (i in 1..4) {
            actualToString = "$actualToString ${eval.evaluateInCell(wb.getSheetAt(0).getRow(12).getCell(5 + i))}"
            wb.write(java.io.FileOutputStream(tempFile))
        }
        actualToString = "$actualToString ${eval.evaluateInCell(wb.getSheetAt(0).getRow(13).getCell(6))}"
        actualToString = "$actualToString ${eval.evaluateInCell(wb.getSheetAt(0).getRow(15).getCell(6))}"
        wb.close()

        assertEquals(expectedToString, actualToString, "Base case Failed")
    }

    @Test
    fun noDataValues_totalCalculationsDetermined_resultingValuesZero() {
        // calculate cell formula values
        completeTotal = merchCellTotal + shipCellTotal + servCellTotal + travelCellTotal + merchCellTotal * 0.06
        expectedToString = " " + merchCellTotal.toString() + " " + shipCellTotal + " " + servCellTotal + " " + travelCellTotal + " " + (merchCellTotal * 0.06) + " " + completeTotal
        // run function to test
        totalCalculations(wb, 5, 5, 6)
        val eval = wb.creationHelper.createFormulaEvaluator()
        // get formula calculated values
        for (i in 1..4) {
            actualToString = actualToString + " " + eval.evaluateInCell(wb.getSheetAt(0).getRow(7).getCell(5 + i))
            wb.write(java.io.FileOutputStream(tempFile))
        }
        actualToString = actualToString + " " + eval.evaluateInCell(wb.getSheetAt(0).getRow(8).getCell(6))
        actualToString = actualToString + " " + eval.evaluateInCell(wb.getSheetAt(0).getRow(10).getCell(6))
        wb.close()
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
