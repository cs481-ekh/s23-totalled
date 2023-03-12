package processing

import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.DataFormat
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFColor

/*
    Takes the information given by existing workbook
    Calculates totals based on different types of
    merchandise, shipping, service, and travel
    whilst formatting output of cells
 */

fun totalCalculations(wb: Workbook, topCell: Int, bottomCell: Int, merchCol: Int) {
    // initialize worksheet
    val sheet = wb.getSheetAt(0)

    // initialize data format for cell for dollar double type
    val dataFormat: DataFormat = wb.createDataFormat()
    var dollarStyle: CellStyle = wb.createCellStyle()
    dollarStyle.dataFormat = dataFormat.getFormat("$#,#0.00")

    // set cell color and border color
    dollarStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
    val fillRgb = byteArrayOf(221.toByte(), 235.toByte(), 247.toByte())
    val color = XSSFColor()
    color.rgb = fillRgb
    dollarStyle.setFillForegroundColor(color)
    dollarStyle.borderBottom = BorderStyle.THIN
    dollarStyle.bottomBorderColor = IndexedColors.CORNFLOWER_BLUE.index
    dollarStyle.borderTop = BorderStyle.THIN
    dollarStyle.topBorderColor = IndexedColors.CORNFLOWER_BLUE.index
    dollarStyle.borderLeft = BorderStyle.THIN
    dollarStyle.leftBorderColor = IndexedColors.CORNFLOWER_BLUE.index
    dollarStyle.borderRight = BorderStyle.THIN
    dollarStyle.rightBorderColor = IndexedColors.CORNFLOWER_BLUE.index

    // Create cells and their column reference values
    val totalRow = sheet.createRow(bottomCell + 2)
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
    merchCell.cellStyle = dollarStyle
    shipCell.cellFormula = "SUM($shipColLetter$topCell:$shipColLetter$bottomCell)"
    shipCell.cellStyle = dollarStyle
    servCell.cellFormula = "SUM($servColLetter$topCell:$servColLetter$bottomCell)"
    servCell.cellStyle = dollarStyle
    travelCell.cellFormula = "SUM($travelColLetter$topCell:$travelColLetter$bottomCell)"
    travelCell.cellStyle = dollarStyle

    // Set cell formula calculations for taxes
    val taxRow = sheet.createRow(bottomCell + 3)
    val taxCell = taxRow.createCell(6)
    val eval = wb.creationHelper.createFormulaEvaluator()
    var res = eval.evaluate(merchCell).formatAsString().toDouble()
    taxCell.cellFormula = "$res*0.06"
    taxCell.cellStyle = dollarStyle

    // Create and set cell for total sum of costs
    val totalChargesRow = sheet.createRow(bottomCell + 5)
    val totalChargesCell = totalChargesRow.createCell(6)
    res = eval.evaluate(merchCell).formatAsString().toDouble()
    val res1 = eval.evaluate(shipCell).formatAsString().toDouble()
    val res2 = eval.evaluate(servCell).formatAsString().toDouble()
    val res3 = eval.evaluate(travelCell).formatAsString().toDouble()
    val res4 = eval.evaluate(taxCell).formatAsString().toDouble()
    totalChargesCell.cellFormula = "$res + $res1 + $res2 + $res3 + $res4"
    totalChargesCell.cellStyle = dollarStyle

    // Set display cells with formatting
    // Create font style
    var fontHeight = 16.toShort()
    var stringStyle: CellStyle = wb.createCellStyle()
    stringStyle.alignment = HorizontalAlignment.RIGHT
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
    taxRow.createCell(7).cellStyle = dollarStyle
    taxRow.createCell(8).cellStyle = dollarStyle
    taxRow.createCell(9).cellStyle = dollarStyle
    totalChargesRow.createCell(7).cellStyle = dollarStyle
    totalChargesRow.createCell(8).cellStyle = dollarStyle
    totalChargesRow.createCell(9).cellStyle = dollarStyle
}
