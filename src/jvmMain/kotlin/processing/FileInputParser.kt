package processing

import com.github.pjfanning.xlsx.StreamingReader
import org.apache.poi.ss.usermodel.Sheet
import java.io.File
import java.io.FileInputStream

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

        val fileStream = FileInputStream(File(path))
        val workbook = StreamingReader.builder()
            .rowCacheSize(100)
            .bufferSize(700)
            .open(fileStream)

        for (sheet: Sheet in workbook) {
            if (!sheet.sheetName.equals("account codes", ignoreCase = true)) {
                sheets.add(sheet)
            }
        }

        workbook.close()

        return sheets
    }
}
