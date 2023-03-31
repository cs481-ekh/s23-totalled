package processing

import com.github.pjfanning.xlsx.StreamingReader
import data.ProjectMetadata
import data.Team
import org.apache.poi.ss.usermodel.Row
import java.io.File
import java.io.FileInputStream

/**
 * Parses the project book and finds all relevant projects
 * @param projectBookPath path to the project book xlsx file
 * @param teamList list of teams, each team should have a total calculated
 * @param columnNames map of actual column name in the spreadsheet to the internal column name
 * @return list of relevant projects for invoicing
 */
fun getProjectBookProjects(
    projectBookPath: String,
    teamList: List<Team>,
    columnNames: Map<String, String>,
): List<ProjectMetadata> {
    val wb = StreamingReader.builder()
        .rowCacheSize(50)
        .bufferSize(700)
        .open(FileInputStream(File(projectBookPath)))
    val sheet = wb.getSheetAt(0)

    val columnNameToNumber = mutableMapOf<String, Int>()

    val projects = mutableListOf<ProjectMetadata>()

    var foundColumnHeadings = false // set to true when the column headings are found
    var prevEmptyProject = false // if 2 empty project rows are found consecutively we are done
    for (r: Row in sheet) {
        if (r.getCell(1).stringCellValue == "Project #") {
            // if this row contains column headings add the ones we care about to the map then continue
            for (i in 1 until r.physicalNumberOfCells) {
                val cellString = r.getCell(i).stringCellValue.trim()
                if (columnNames.containsKey(cellString) && !columnNameToNumber.containsKey(columnNames[cellString])) {
                    columnNameToNumber[columnNames[cellString]!!] = i
                }
            }
            foundColumnHeadings = true
            continue
        }
        if (!foundColumnHeadings) {
            // if the column headings haven't been found yet continue to the next row
            continue
        }

        // if we get here the column headings should have been found
        if (columnNameToNumber.size != columnNames.size) {
            throw Exception("Could not find all project book columns")
        }

        if (r.getCell(1).stringCellValue.isEmpty()) {
            if (prevEmptyProject) {
                // if the previous project was empty and this on is also empty we are done
                break
            } else {
                // if this project is empty remember that and continue to the next row
                prevEmptyProject = true
                continue
            }
        }
        // mark project as not empty
        prevEmptyProject = false

        val project = getProject(r, columnNameToNumber, teamList)
        if (project != null) {
            projects.add(project)
        }
    }
    wb.close()
    return projects
}

/**
 * Gets a project from the given row
 * @param r the row
 * @param columnNameToNumber map of column names to their actual index
 * @param teamList list containing each team
 * @return the project, null if it shouldn't be used
 */
private fun getProject(
    r: Row,
    columnNameToNumber: Map<String, Int>,
    teamList: List<Team>,
): ProjectMetadata? {
    val project = ProjectMetadata()
    project.teamAbbr = r.getCell(columnNameToNumber["Team Abbr"]!!).stringCellValue

    // find the amount in the team list
    for (team in teamList) {
        if (team.teamName.lowercase() == project.teamAbbr.lowercase()) {
            project.amount = team.totalCharges
        }
    }
    if (project.amount == 0.0) {
        return null
    }

    project.billTo = r.getProjectValue(columnNameToNumber["Sponsor / Funding Department"]!!)
    project.contactInfo = r.getProjectValue(columnNameToNumber["Sponsor Contact"]!!)
    project.email = r.getProjectValue(columnNameToNumber["Sponsor Contact email (optional)"]!!)

    val addressLines = r.getProjectValue(columnNameToNumber["Billing Address / BSU Department ID"]!!)
        .split("\n")

    when (addressLines.size) {
        1 -> {
            if (addressLines[0].trim().isEmpty()) {
                // The only example we were given includes a project with no address
                return project
            }
            // 1 non-empty line looks like a bsu department id
            // It doesn't look like projects with department ids should be included
            return null
        }
        2 -> {
            project.addressLine1 = project.billTo
            project.addressLine2 = addressLines[0]
            val line3 = addressLines[1].split(" ")
            project.city = line3[0].dropLastWhile { it == ',' } // drop the comma
            project.state = line3[1]
            project.zip = line3[2]
            return project
        }
        3 -> {
            project.addressLine1 = project.billTo
            project.addressLine2 = addressLines[1]
            val line3 = addressLines[2].split(" ")
            project.city = line3[0].dropLastWhile { it == ',' } // drop the comma
            project.state = line3[1]
            project.zip = line3[2]
            return project
        }
        else -> {
            throw Exception("Unexpected number of address lines. Should be 1, 2, or 3")
        }
    }
}

/**
 * Helper to get a column's string cell value
 */
private fun Row.getProjectValue(columnNumber: Int): String {
    return this.getCell(columnNumber)
        .stringCellValue
        .trim()
}
