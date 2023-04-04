package processing

import com.github.pjfanning.xlsx.StreamingReader
import data.PurchaseType
import data.Team
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException



/**
 * This function/class simply writes out the TeamExpenseBreakdown when given a Team object.
 * TODO: Write tests for this class!
 */
fun LineItemWriter(givenTeam: Team, outputPath: Path): Number {
    var lastLineWritten = 3; // setting it to 3 because we want first line written to be 4, which is really 5. See the for loop.

    var templateFile = File("src/jvmMain/resources/TeamExpenseBreakdownTemplate.xlsx")

    var outputFile = outputPath.toFile();

    // Copy the contents of templatePath to outputFile
    Files.copy(templateFile.toPath(), outputFile.toPath());

    var workbook: Workbook = StreamingReader.builder()
        .rowCacheSize(100)
        .bufferSize(700)
        .open(FileInputStream(outputFile))

    // Fill in appropriate cells with the corresponding information
    var sheet = workbook.getSheetAt(0);

    //Set up the headers
    // Write the Semester (A1)
    WriteToCell(sheet, 0, 0, getSemester(givenTeam.lineItemList[0].date) + " Capstone Design");

    // Write Team Name (C2)
    WriteToCell(sheet, 1, 2, givenTeam.teamName);

    // Write Project Name (F2)
    WriteToCell(sheet, 1, 5, "{TODO: Add Project Name}");  // TODO: Implement after #118 is done

    // Write out the line items (Starting on row 4 (5 in excel))
    for(lineItem in givenTeam.lineItemList){
        lastLineWritten++;

        // write out lineItem attributes
        WriteToCell(sheet, lastLineWritten, 0, lineItem.cardType.toString());
        WriteToCell(sheet, lastLineWritten, 1, "{TODO: Get PO}"); // TODO: Implement after #118 is done
        WriteToCell(sheet, lastLineWritten, 2, lineItem.vendor);
        WriteToCell(sheet, lastLineWritten, 3, lineItem.date);
        WriteToCell(sheet, lastLineWritten, 4, (lineItem.totalNonTaxable + lineItem.totalTaxable).toString());
        WriteToCell(sheet, lastLineWritten, 5, lineItem.description);

        when(lineItem.purchaseType) {
            PurchaseType.PURCHASE -> {
                WriteToCell(sheet, lastLineWritten, 6, lineItem.totalTaxable.toString());
                WriteToCell(sheet, lastLineWritten, 7, lineItem.totalNonTaxable.toString());
            }
            PurchaseType.SERVICE -> WriteToCell(sheet, lastLineWritten, 8, lineItem.totalNonTaxable.toString());
            PurchaseType.TRAVEL -> WriteToCell(sheet, lastLineWritten, 9, lineItem.totalNonTaxable.toString());
        }
    }

    try {
        val out = FileOutputStream(outputPath.toString());
        workbook.write(out);
        out.close();
    } catch (ex: FileNotFoundException) {
        throw ex;
    }
    // Close things up
    workbook.close();
    return lastLineWritten;
}

/**
 * Helper function to make writing to cells easier
 */
private fun WriteToCell(sheet: Sheet,row: Int, column: Int, value: String){
    var cellRow = sheet.getRow(row);
    var cell = cellRow.getCell(column);

    cell.setCellValue(value);
}

/**
 * Helper function to make finding the date easier and to tidy up the above code
 */
private fun getSemester(dateAsString: String): String {
    var pattern = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    var date: LocalDateTime;

    try{
        date = LocalDateTime.parse(dateAsString, pattern); // attempt #1 to parse
    } catch(ex: DateTimeParseException){
        pattern = DateTimeFormatter.ofPattern("MM/dd/yy");
        try{
            date = LocalDateTime.parse(dateAsString, pattern); // attempt #2 to parse
        } catch (ex: DateTimeParseException){
            throw ex;
        }
    }

    var year = date.year.toString();

    if(year.length == 2){
        year = "20$year";
    }

    return "Fiscal Year $year";
}