package processing

import data.ProjectMetadata
import org.apache.poi.ss.usermodel.CellCopyPolicy
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

fun writeInvoiceRequestFile(outputPath: String, projectList: List<ProjectMetadata>) {
    // If there are no projects write a file making that clear to the user
    if (projectList.isEmpty()) {
        val outputFile = Paths.get(outputPath, "NoInvoiceRequestGenerated.txt").toFile()
        outputFile.writeText("No projects in the project book were found that match with the teams in the expense log")
        return
    }

    // To access a file in this the resources folder we do this:
    val templateFile = object {}.javaClass.classLoader.getResourceAsStream("InvoicingTemplate.xlsx")!!

    val outputFile = Paths.get(outputPath, "InvoiceRequest.xlsx").toFile()

    // Make sure that we clean up any existing file
    if (outputFile.exists()) {
        outputFile.delete()
    }

    Files.copy(templateFile, outputFile.toPath())

    val wb = XSSFWorkbook(FileInputStream(outputFile))
    val sheet = wb.getSheetAt(0)

    var dataRowIndex = 2
    var separatorRowIndex = 4
    val iterator = projectList.iterator()
    while (iterator.hasNext()) {
        val project = iterator.next()
        if (dataRowIndex != 2) {
            sheet.copyRows(2, 2, dataRowIndex, CellCopyPolicy())
        }

        val row = sheet.getRow(dataRowIndex)
        row.getCell('c'.asColNum).setCellValue(project.billTo)
        row.getCell('e'.asColNum).setCellValue(project.email)
        row.getCell('f'.asColNum).setCellValue(project.contactInfo)
        row.getCell('g'.asColNum).setCellValue(project.addressLine1)
        row.getCell('h'.asColNum).setCellValue(project.addressLine2)
        row.getCell('i'.asColNum).setCellValue(project.city)
        row.getCell('j'.asColNum).setCellValue(project.state)
        row.getCell('k'.asColNum).setCellValue(project.zip)
        row.getCell('p'.asColNum).setCellValue(project.amount)
        row.getCell('q'.asColNum).setCellValue(project.amount)

        if (iterator.hasNext() && separatorRowIndex != 4) {
            sheet.copyRows(4, 4, separatorRowIndex, CellCopyPolicy())
        }
        dataRowIndex += 4
        separatorRowIndex += 4
    }

    FileOutputStream("$outputPath/InvoiceRequest.xlsx").use { outputStream -> wb.write(outputStream) }
    wb.close()
}

val Char.asColNum: Int
    get() = this.code - 97
