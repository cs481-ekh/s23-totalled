package processingTests
import data.Project
import data.Team
import org.junit.jupiter.api.Test
import processing.getProjectBookProjects
import kotlin.test.assertTrue

class ProjectBookParserTests {
    private val path = System.getProperty("user.dir")
    private val projectBookPath = "$path/src/jvmTest/TestExcelFiles/ProjectBookSample.xlsx"

    private val team1 = Team(
        "LUNAR",
        mutableListOf(),
    )
    private val team1ExpectedProject = Project(
        teamAbbr = "LUNAR",
        billTo = "Visioneering Space Corp",
        email = "strategic.blueberry@visioneeringspace.com",
        contactInfo = "Strategic Blueberry",
        addressLine1 = "Visioneering Space Corp",
        addressLine2 = "3380 W Country Platform, Suite 110",
        city = "Boise",
        state = "ID",
        zip = "83706",
        amount = 3560.03,
    )

    private val team2 = Team(
        "CODEX",
        mutableListOf(),
    )
    private val team2ExpectedProject = Project(
        teamAbbr = "CODEX",
        billTo = "Visioneering Space Corp",
        email = "lively.carrot@visioneeringspace.com",
        contactInfo = "Lively Carrot",
        addressLine1 = "Visioneering Space Corp",
        addressLine2 = "3380 W Country Platform, Suite 110",
        city = "Boise",
        state = "ID",
        zip = "83706",
        amount = 1000.00,
    )

    private val team3 = Team(
        "NLG",
        mutableListOf(),
    )
    private val team3ExpectedProject = Project(
        teamAbbr = "NLG",
        billTo = "NLG LLC",
        email = "z@zezzel.com, Wonderful.Broccoli@gmail.com",
        contactInfo = "Wonderful Broccoli",
        addressLine1 = "",
        addressLine2 = "",
        city = "",
        state = "",
        zip = "",
        amount = 7.27,
    )

    private val team4 = Team(
        "JBL",
        mutableListOf(),
    )
    private val team4ExpectedProject = Project(
        teamAbbr = "JBL",
        billTo = "JB laser",
        email = "wittylettuce@wittylettuce.com",
        contactInfo = "Witty Lettuce",
        addressLine1 = "JB laser",
        addressLine2 = "4129 Plant Way",
        city = "Boise",
        state = "ID",
        zip = "83709",
        amount = 666.66,
    )

    private val team5 = Team(
        "BAJA",
        mutableListOf(),
    )

    private val team6 = Team(
        "VITRO",
        mutableListOf(),
    )

    private val team7 = Team(
        "FAKEPROJECT",
        mutableListOf(),
    )

    init {
        // just making up some amounts
        team1.totalCharges = 3560.03
        team2.totalCharges = 1000.00
        team3.totalCharges = 7.27
        team4.totalCharges = 666.66
        team5.totalCharges = 976.21
        team6.totalCharges = 121.21
    }

    @Test
    fun projectBookAndTeamListGiven_parseProjectBookCalled_projectListCreated() {
        val teamList = listOf(
            team1,
            team2,
            team5,
            team3,
            team7,
            team4,
            team6,
        )
        val expectedProjectList = listOf(
            team1ExpectedProject,
            team2ExpectedProject,
            team3ExpectedProject,
            team4ExpectedProject,
        )

        val actualProjectList = getProjectBookProjects(projectBookPath, teamList)

        // order shouldn't matter so this is the easiest way to check equality
        assertTrue { actualProjectList.size == expectedProjectList.size }
        assertTrue { actualProjectList.containsAll(expectedProjectList) }
    }

    @Test
    fun teamListWithOnlyUnneededProjectsGiven_parseProjectBookCalled_emptyListCreated() {
        val teamList = listOf(
            team7,
            team6,
            team5,
        )

        val actualProjectList = getProjectBookProjects(projectBookPath, teamList)

        assertTrue { actualProjectList.isEmpty() }
    }

    @Test
    fun teamListWithOneNeededProjectGiven_parseProjectBookCalled_projectListCreated() {
        val teamList = listOf(
            team7,
            team6,
            team1,
            team5,
        )

        val expectedProjectList = listOf(
            team1ExpectedProject,
        )

        val actualProjectList = getProjectBookProjects(projectBookPath, teamList)

        assertTrue { actualProjectList == expectedProjectList }
    }
}
