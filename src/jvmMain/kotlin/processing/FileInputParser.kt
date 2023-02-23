package processing

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream

class FileInputParser(private var primaryPath: String, private var secondaryPath: String? = null) {
    fun getAllSheets(): MutableList<Sheet> {
        val allSheets = mutableListOf<Sheet>()

        allSheets.addAll(getSheetsFromFile(primaryPath))

        if(secondaryPath != null){
            allSheets.addAll(getSheetsFromFile(secondaryPath!!))
        }

        return allSheets
    }

    private fun getSheetsFromFile(path: String): MutableList<Sheet> {
        val sheets = mutableListOf<Sheet>()

        val file = File(path)
        val inputStream = FileInputStream(file)

        val workbook = WorkbookFactory.create(inputStream)

        for (sheet in workbook){
            if(!sheet.sheetName.equals("account codes", ignoreCase = true)) {
                sheets.add(sheet)
            }
        }

        workbook.close()
        inputStream.close()

        return sheets
    }
}