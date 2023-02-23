package processing

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

class FileInputParser(private var primaryPath: String, private var secondaryPath: String? = null) {
    fun getAllSheets(): MutableList<Sheet> {
        val allSheets = mutableListOf<Sheet>()

        allSheets.addAll(getSheetsFromFile(primaryPath))

        if (secondaryPath != null) {
            allSheets.addAll(getSheetsFromFile(secondaryPath!!))
        }

        return allSheets
    }

    private fun getSheetsFromFile(path: String): MutableList<Sheet> {
        val sheets = mutableListOf<Sheet>()

        val workbook = WorkbookFactory.create(File(path))

        for (sheet: Sheet in workbook) {
            if (!sheet.sheetName.equals("account codes", ignoreCase = true)) {
                sheets.add(sheet)
            }
        }

        workbook.close()

        return sheets
    }
}
