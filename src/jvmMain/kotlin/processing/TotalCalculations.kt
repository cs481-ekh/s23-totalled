package processing

import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Workbook
import java.lang.Exception

/*
    Takes the information given by existing workbook
    Calculates totals based on different types of
    merchandise, shipping, service, and travel
    whilst formatting output of cells
 */

fun totalCalculations(wb: Workbook, topCell: Int, bottomCell: Int, merchCol: Int): Double {
    // initialize worksheet
    val sheet = wb.getSheetAt(0)

    // initialize data format for cell
    val itemStyle = sheet.getRow(5).getCell(5).cellStyle
    val totalStyle = sheet.getRow(5).getCell(6).cellStyle
    // Create cells and their column reference values
    val totalRow = sheet.createRow(bottomCell + 2)

    for (i in 0..5) {
        totalRow.createCell(i).cellStyle = itemStyle
    }
    val merchColLetter = merchCol.toChar() + 65
    val shipColLetter = (merchCol + 1).toChar() + 65
    val servColLetter = (merchCol + 2).toChar() + 65
    val travelColLetter = (merchCol + 3).toChar() + 65
    val merchCell = totalRow.createCell(6)
    val shipCell = totalRow.createCell(7)
    val servCell = totalRow.createCell(8)
    val travelCell = totalRow.createCell(9)

    // Set cell formula for total cells
    merchCell.cellFormula = "SUM($merchColLetter$topCell:$merchColLetter$bottomCell)"
    merchCell.cellStyle = totalStyle
    shipCell.cellFormula = "SUM($shipColLetter$topCell:$shipColLetter$bottomCell)"
    shipCell.cellStyle = totalStyle
    servCell.cellFormula = "SUM($servColLetter$topCell:$servColLetter$bottomCell)"
    servCell.cellStyle = totalStyle
    travelCell.cellFormula = "SUM($travelColLetter$topCell:$travelColLetter$bottomCell)"
    travelCell.cellStyle = totalStyle

    // Set cell formula calculations for taxes
    val taxRow = sheet.createRow(bottomCell + 3)
    val taxCell = taxRow.createCell(6)
    taxCell.cellFormula = "${merchCell.columnIndex.toChar() + 65}${bottomCell + 3}*0.06"
    taxCell.cellStyle = totalStyle

    // Create and set cell for total sum of costs
    val totalChargesRow = sheet.createRow(bottomCell + 5)
    val totalChargesCell = totalChargesRow.createCell(6)
    totalChargesCell.cellFormula = "SUM(${taxCell.columnIndex.toChar() + 65}${bottomCell + 4}:${travelCell.columnIndex.toChar() + 65}${bottomCell + 3})"
    totalChargesCell.cellStyle = totalStyle

    // Set display cells with formatting
    // Create font style
    val fontHeight = 16.toShort()
    val stringStyle: CellStyle = wb.createCellStyle()
    stringStyle.cloneStyleFrom(itemStyle)
    val font = wb.createFont()
    font.bold = true
    font.fontHeightInPoints = fontHeight
    stringStyle.setFont(font)

    // Create cells with font type
    val totalString = totalRow.createCell(5)
    totalString.cellStyle = stringStyle
    totalString.setCellValue("Totals:")
    val taxString = taxRow.createCell(5)
    taxString.cellStyle = stringStyle
    taxString.setCellValue("Sales Tax @6%")
    val chargesString = totalChargesRow.createCell(5)
    chargesString.cellStyle = stringStyle
    chargesString.setCellValue("TOTAL CHARGES")

    // Format leftover cells
    for (i in 7..9) {
        taxRow.createCell(i).cellStyle = totalStyle
        totalChargesRow.createCell(i).cellStyle = totalStyle
    }

    return try {
        // Can't do totalChargesCell.numericCellValue because the formula won't be evaluated
        val evaluator: FormulaEvaluator = wb.creationHelper.createFormulaEvaluator()
        evaluator.evaluate(totalChargesCell).numberValue
    } catch (e: Exception) {
        e.printStackTrace()
        0.0
    }
}
