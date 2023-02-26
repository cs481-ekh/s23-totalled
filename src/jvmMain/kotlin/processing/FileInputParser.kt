package processing

import com.github.pjfanning.xlsx.StreamingReader
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileInputStream

class FileInputParser(private var primaryPath: String, private var secondaryPath: String? = null) {
    private var _primaryWorkbook: Workbook = StreamingReader.builder()
        .rowCacheSize(100)
        .bufferSize(700)
        .open(FileInputStream(File(primaryPath)))

    private lateinit var _secondaryWorkbook: Workbook

    /**
     * Always checks if the secondary path was set, and creates the workbook if so.
     */
    init {
        if (secondaryPath != null) {
            _secondaryWorkbook = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(700)
                .open(FileInputStream(File(secondaryPath)))
        }
    }

    /**
     * Gets all worksheets from both workbooks.
     * Returns a list of worksheets
     */
    fun getAllSheets(): MutableList<Sheet> {
        val allSheets = mutableListOf<Sheet>()

        allSheets.addAll(getSheetsFromFile(_primaryWorkbook))

        if (secondaryPath != null) {
            allSheets.addAll(getSheetsFromFile(_secondaryWorkbook))
        }

        return allSheets
    }

    /**
     * Closes both currently open workbooks
     */
    fun closeWorkbooks() {
        _primaryWorkbook.close()

        if (secondaryPath != null) {
            _secondaryWorkbook.close()
        }
    }

    /**
     * Returns a list of sheets given the [workbook]
     */
    private fun getSheetsFromFile(workbook: Workbook): MutableList<Sheet> {
        val sheets = mutableListOf<Sheet>()

        for (sheet: Sheet in workbook) {
            if (!sheet.sheetName.equals("account codes", ignoreCase = true)) {
                sheets.add(sheet)
            }
        }

        return sheets
    }
}
