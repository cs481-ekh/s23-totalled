package processing

import data.PurchaseType
import data.Team
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * This function/class simply writes out the TeamExpenseBreakdown when given a Team object.
 */
fun lineItemWriter(givenTeam: Team, outputPath: Path, fileName: String): Int {
    var lastLineWritten = 3 // setting it to 3 because we want first line written to be 4, which is really 5. See the for loop.

    val templateFile = object {}.javaClass.classLoader.getResourceAsStream("TeamExpenseBreakdownTemplate.xlsx")!!

    val outputFilePath = Paths.get(outputPath.toString(), fileName)

    val outputFile = outputFilePath.toFile()

    // Make sure that we clean up any existing file
    if (outputFile.exists()) {
        outputFile.delete()
    }

    // Copy the contents of templateFile to outputFile
    Files.copy(templateFile, outputFile.toPath())
    val fileInputStream = FileInputStream(outputFile)
    val workbook = XSSFWorkbook(fileInputStream)

    // Fill in appropriate cells with the corresponding information
    val sheet = workbook.getSheetAt(0)

    // Set up the headers
    // Write the Semester (A1)
    // find the first line item's date that isn't empty and get the semester from it
    val nonEmptyDateItem = givenTeam.lineItemList.find { it.date != "" }

    if (nonEmptyDateItem != null) {
        writeToCell(sheet, 0, 0, getSemester(nonEmptyDateItem.date) + " Capstone Design")
    } else {
        writeToCell(sheet, 0, 0, "Capstone Design")
    }

    // Write Team Name (C2)
    writeToCell(sheet, 1, 2, givenTeam.teamName)

    // Write Project Name (F2)
    writeToCell(sheet, 1, 5, "${givenTeam.teamName} Project")

    // Write out the line items (Starting on row 4 (5 in excel))
    for (lineItem in givenTeam.lineItemList) {
        lastLineWritten++

        // write out lineItem attributes
        writeToCell(sheet, lastLineWritten, 0, lineItem.cardType.toString())
        writeToCell(sheet, lastLineWritten, 1, lineItem.poNumber)
        writeToCell(sheet, lastLineWritten, 2, lineItem.vendor)
        writeToCell(sheet, lastLineWritten, 3, lineItem.date)
        writeToCell(sheet, lastLineWritten, 4, (lineItem.totalNonTaxable + lineItem.totalTaxable).toString())
        writeToCell(sheet, lastLineWritten, 5, lineItem.description)

        when (lineItem.purchaseType) {
            PurchaseType.PURCHASE -> {
                writeToCell(sheet, lastLineWritten, 6, lineItem.totalTaxable.toString())
                writeToCell(sheet, lastLineWritten, 7, lineItem.totalNonTaxable.toString())
            }
            PurchaseType.SERVICE -> writeToCell(sheet, lastLineWritten, 8, lineItem.totalNonTaxable.toString())
            PurchaseType.TRAVEL -> writeToCell(sheet, lastLineWritten, 9, lineItem.totalNonTaxable.toString())
        }
    }
    val fileOutputStream = FileOutputStream(outputFile)
    workbook.write(fileOutputStream)
    // Close things up
    fileOutputStream.close()
    workbook.close()
    return lastLineWritten
}

/**
 * Helper function to make writing to cells easier
 */
fun writeToCell(sheet: Sheet, rowIdx: Int, colIdx: Int, value: String) {
    // Check if the value is a number
    try {
        val number = value.toDouble()
        sheet.getRow(rowIdx).getCell(colIdx).setCellValue(number)
    } catch (ex: NumberFormatException) {
        sheet.getRow(rowIdx).getCell(colIdx).setCellValue(value)
    }
}

/**
 * Helper function to make finding the date easier and to tidy up the above code
 */
private fun getSemester(dateAsString: String): String {
    val acceptableFormats = mutableListOf<SimpleDateFormat>()
    lateinit var date: Date

    acceptableFormats.add(SimpleDateFormat("M/d/yyyy"))
    acceptableFormats.add(SimpleDateFormat("M/dd/yyyy"))
    acceptableFormats.add(SimpleDateFormat("MM/dd/yyyy"))
    acceptableFormats.add(SimpleDateFormat("M/d/yy"))
    acceptableFormats.add(SimpleDateFormat("M/dd/yy"))
    acceptableFormats.add(SimpleDateFormat("MM/d/yy"))

    for (pattern in acceptableFormats) {
        try {
            date = pattern.parse(dateAsString)
        } catch (ex: ParseException) { }
    }

    val thisCalendar = Calendar.getInstance()
    thisCalendar.time = date
    val year = thisCalendar.get(Calendar.YEAR)

    return "Fiscal Year $year"
}
