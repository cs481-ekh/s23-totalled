import org.apache.poi.ss.usermodel.DataFormat
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import processing.totalCalculations
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random
import kotlin.test.assertEquals

class TotalDataCalcTests {

    private val path = System.getProperty("user.dir")
    private val file: File = File("$path/src/jvmTest/TestExcelFiles/", "TotalCalcWorkbook.xlsx")
    private val tempFile: File = File("$path/src/jvmTest/TestExcelFiles/", "tempTotalCalcWorkbook.xlsx")
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

    @BeforeEach
    fun initVars() {
        // reset the file
        tempFile.delete()
        file.copyTo(tempFile)
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
    }

    @Test
    fun totalData_NormalDataFormat() {
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
            for (j in 1..4) {
                merchCell.setCellValue(Random.nextDouble(0.0, 3000.0))
                shipCell.setCellValue(Random.nextDouble(0.0, 3000.0))
                servCell.setCellValue(Random.nextDouble(0.0, 3000.0))
                travelCell.setCellValue(Random.nextDouble(0.0, 3000.0))
                merchCellTotal += merchCell.numericCellValue
                shipCellTotal += shipCell.numericCellValue
                servCellTotal += servCell.numericCellValue
                travelCellTotal += travelCell.numericCellValue
            }
        }
        completeTotal = merchCellTotal + shipCellTotal + servCellTotal + travelCellTotal
        expectedToString = merchCellTotal.toString() + " " + shipCellTotal.toString() + " " + servCellTotal.toString() + " " + travelCellTotal.toString() + " " + (completeTotal * 0.06).toString() + " " + completeTotal.toString()
        totalCalculations(wb, 4, 9, 6)
        FileOutputStream(file).use { outputStream -> wb.write(outputStream) }
        for (i in 1..4) {
            actualToString = actualToString + " " + wb.getSheetAt(0).getRow(11).getCell(5 + i).reference.toString()
        }
        actualToString = actualToString + " " + wb.getSheetAt(0).getRow(12).getCell(6).reference.toString()
        actualToString = actualToString + " " + wb.getSheetAt(0).getRow(14).getCell(6).reference.toString()
        print(actualToString)
        print(expectedToString)
        assertEquals("Base case Failed", expectedToString, actualToString)
    }
}