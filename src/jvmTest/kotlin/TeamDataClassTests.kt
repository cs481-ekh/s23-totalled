import data.CardType
import data.LineItem
import data.PurchaseType
import data.Team
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TeamDataClassTests {

    val item1 = LineItem(12.00, 0.00, "Sheet Metal", "1/2/2023", "Home Depot", CardType.AH, PurchaseType.PURCHASE)
    val item2 = LineItem(0.00, 1000.00, "Sponsor Fee", "8/1/2022", "MBE", CardType.NONE, PurchaseType.SERVICE)
    val item3 = LineItem(12.00, 3.50, "Piping", "1/3/23", "PipesUSA.Com", CardType.RMB, PurchaseType.PURCHASE)
    val item4 = LineItem(0.00, 140.0, "Hotel Stay", "12/25/23", "Marriott", CardType.TRV, PurchaseType.TRAVEL)
    val teamName = "Team1"
    val lineItemList = mutableListOf(item1, item2, item3)

    var team : Team? = null
    var team2 : Team? = null
    var expectedToString = ""

    @BeforeEach
    fun initVariables() {
        team = Team(teamName, lineItemList)
        team2 = Team(teamName, lineItemList, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        expectedToString = String.format(
            "Team(teamName=Team1, lineItemList=[LineItem(totalTaxable=12.0, totalNonTaxable=0.0," +
                    " description=Sheet Metal, date=1/2/2023, vendor=Home Depot, cardType=AH, purchaseType=PURCHASE)," +
                    " LineItem(totalTaxable=0.0, totalNonTaxable=1000.0, description=Sponsor Fee, date=8/1/2022," +
                    " vendor=MBE, cardType=NONE, purchaseType=SERVICE), LineItem(totalTaxable=12.0, totalNonTaxable=3.5," +
                    " description=Piping, date=1/3/23, vendor=PipesUSA.Com, cardType=RMB, purchaseType=PURCHASE)]," +
                    " totalTaxable=0.0, totalShippingHandling=0.0, totalServices=0.0, totalTravel=0.0, totalTaxes=0.0," +
                    " totalCharges=0.0)"
        )
    }

    @Test
    fun noTeamExists_SecondaryConstructorCalled_CorrectTeamExists(){
        assertEquals(expectedToString, team.toString(), "Secondary Constructor Failed")
    }

    @Test
    fun noTeamExists_PrimaryConstructorCalled_CorrectTeamExists(){
        assertEquals(expectedToString, team2.toString(), "Primary Constructor Failed")
    }

    @Test
    fun teamExistsNoTotal_ChangeTotals_CorrectTeamTotalsExist(){
        team2?.totalCharges = 44.0
        team2?.totalServices = 3.0
        team2?.totalTaxable = 7.0
        team2?.totalTaxes = 1.00
        team2?.totalTravel = 30.00
        team2?.totalShippingHandling = 3.00

        expectedToString = String.format(
            "Team(teamName=Team1, lineItemList=[LineItem(totalTaxable=12.0, totalNonTaxable=0.0," +
                    " description=Sheet Metal, date=1/2/2023, vendor=Home Depot, cardType=AH, purchaseType=PURCHASE)," +
                    " LineItem(totalTaxable=0.0, totalNonTaxable=1000.0, description=Sponsor Fee, date=8/1/2022," +
                    " vendor=MBE, cardType=NONE, purchaseType=SERVICE), LineItem(totalTaxable=12.0, totalNonTaxable=3.5," +
                    " description=Piping, date=1/3/23, vendor=PipesUSA.Com, cardType=RMB, purchaseType=PURCHASE)]," +
                    " totalTaxable=7.0, totalShippingHandling=3.0, totalServices=3.0, totalTravel=30.0, totalTaxes=1.0," +
                    " totalCharges=44.0)"
        )

        assertEquals(expectedToString, team2.toString(), "Changing Total Values Failed")

    }

    @Test
    fun teamExists_AddNewLineItemToList_CorrectLineItemListPrints(){
        team2?.lineItemList?.add(item4)
        expectedToString = String.format(
            "Team(teamName=Team1, lineItemList=[LineItem(totalTaxable=12.0, totalNonTaxable=0.0," +
                    " description=Sheet Metal, date=1/2/2023, vendor=Home Depot, cardType=AH, purchaseType=PURCHASE)," +
                    " LineItem(totalTaxable=0.0, totalNonTaxable=1000.0, description=Sponsor Fee, date=8/1/2022," +
                    " vendor=MBE, cardType=NONE, purchaseType=SERVICE), LineItem(totalTaxable=12.0, totalNonTaxable=3.5," +
                    " description=Piping, date=1/3/23, vendor=PipesUSA.Com, cardType=RMB, purchaseType=PURCHASE)"+
                    ", LineItem(totalTaxable=0.0, totalNonTaxable=140.0, description=Hotel Stay, date=12/25/23," +
                    " vendor=Marriott, cardType=TRV, purchaseType=TRAVEL)]," +
                    " totalTaxable=0.0, totalShippingHandling=0.0, totalServices=0.0, totalTravel=0.0, totalTaxes=0.0," +
                    " totalCharges=0.0)"
        )

        assertEquals(expectedToString, team2.toString(), "Changing Total Values Failed")
    }
}