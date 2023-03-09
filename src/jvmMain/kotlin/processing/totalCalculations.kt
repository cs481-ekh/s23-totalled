package processing

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFColor

/*
    Takes the information given by existing workbook
    Calculates totals based on different types of
    merchandise, shipping, service, and travel
    whilst formatting output of cells
 */

fun totalCalculation(wb: Workbook, topCell: Int, bottomCell: Int, merchCol: Int) {
    val sheet = wb.getSheet("Sheet1")
    val dataFormat: DataFormat = wb.createDataFormat()
    var dollarStyle: CellStyle = wb.createCellStyle()
    dollarStyle.dataFormat = dataFormat.getFormat("$#,#0.00")
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
    val totalRow = sheet.createRow(bottomCell + 2)
    val merchColLetter = merchCol.toChar() + 65
    val shipColLetter = (merchCol + 1).toChar() + 65
    val servColLetter = (merchCol + 2).toChar() + 65
    val travelColLetter = (merchCol + 3).toChar() + 65
    val merchCell = totalRow.createCell(6)
    val shipCell = totalRow.createCell(7)
    val servCell = totalRow.createCell(8)
    val travelCell = totalRow.createCell(9)
    merchCell.cellFormula = "SUM($merchColLetter$topCell:$merchColLetter$bottomCell)"
    merchCell.cellStyle = dollarStyle
    shipCell.cellFormula = "SUM($shipColLetter$topCell:$shipColLetter$bottomCell)"
    shipCell.cellStyle = dollarStyle
    servCell.cellFormula = "SUM($servColLetter$topCell:$servColLetter$bottomCell)"
    servCell.cellStyle = dollarStyle
    travelCell.cellFormula = "SUM($travelColLetter$topCell:$travelColLetter$bottomCell)"
    travelCell.cellStyle = dollarStyle
    val taxRow = sheet.createRow(bottomCell + 3)
    val taxCell = taxRow.createCell(6)
    val eval = wb.creationHelper.createFormulaEvaluator()
    var res = eval.evaluate(merchCell).formatAsString().toDouble()
    taxCell.cellFormula = "${res}*0.06"
    taxCell.cellStyle = dollarStyle
    val totalChargesRow = sheet.createRow(bottomCell + 5)
    val totalChargesCell = totalChargesRow.createCell(6)
    val chargesString = totalChargesRow.createCell(5)
    chargesString.setCellValue("TOTAL CHARGES")
    res = eval.evaluate(merchCell).formatAsString().toDouble()
    var res1 = eval.evaluate(shipCell).formatAsString().toDouble()
    var res2 = eval.evaluate(servCell).formatAsString().toDouble()
    var res3 = eval.evaluate(travelCell).formatAsString().toDouble()
    var res4 = eval.evaluate(taxCell).formatAsString().toDouble()
    totalChargesCell.cellFormula = "$res + $res1 + $res2 + $res3 + $res4"
    totalChargesCell.cellStyle = dollarStyle
}
