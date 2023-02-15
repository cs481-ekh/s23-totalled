import data.CardType
import data.LineItem
import data.PurchaseType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertTrue

class DataClassTests {

    private var item1: LineItem? = null
    private var expectedToString = ""
    @BeforeEach
    fun initVars(){
        item1 = LineItem(1.11, 2.22, "test","1/2/23","Amazon", CardType.AH, PurchaseType.PURCHASE)
        expectedToString = "LineItem(totalTaxable=1.11, totalNonTaxable=2.22, description=test, date=1/2/23, vendor=Amazon, cardType=AH, purchaseType=PURCHASE)"
    }
    @Test
    fun lineItem_constructorAttempted_LineItemCreated(){
        assertEquals("Create Failed", expectedToString, item1.toString())
    }

    @Test
    fun lineItem_CopyCalled_IdenticalLineItemCreated(){
        var item2 = item1?.copy()
        assertEquals("Copy Failed", expectedToString, item2.toString())
    }

    @Test
    fun lineItemNonTaxable_ZeroValueGiven_LineItemCreationSuccessful(){
        var item2 = LineItem(3.00, 0.00, "Purchase with No S+H", "2/15/2023", "Home Depot", CardType.JL, PurchaseType.PURCHASE)
        var item2ExpectedString = "LineItem(totalTaxable=3.0, totalNonTaxable=0.0, description=Purchase with No S+H, date=2/15/2023, vendor=Home Depot, cardType=JL, purchaseType=PURCHASE)"
        assertEquals("No NonTax LineItem Creation Failed", item2ExpectedString,item2.toString())
    }

    @Test
    fun lineItemNonTaxable_NullValueGiven_LineItemCreationSuccessful(){
        var item2 = LineItem(3.00, null, "Purchase with No S+H", "2/15/2023", "Home Depot", CardType.JL, PurchaseType.PURCHASE)
        var item2ExpectedString = "LineItem(totalTaxable=3.0, totalNonTaxable=null, description=Purchase with No S+H, date=2/15/2023, vendor=Home Depot, cardType=JL, purchaseType=PURCHASE)"
        assertEquals("No NonTax LineItem Creation Failed", item2ExpectedString,item2.toString())
    }

    @Test
    fun lineItemTaxableNull_NullValueGiven_LineItemCreationSuccessful(){
        var item2 = LineItem(null, 3.00, "Purchase with No S+H", "2/15/2023", "Home Depot", CardType.JL, PurchaseType.PURCHASE)
        var item2ExpectedString = "LineItem(totalTaxable=null, totalNonTaxable=3.0, description=Purchase with No S+H, date=2/15/2023, vendor=Home Depot, cardType=JL, purchaseType=PURCHASE)"
        assertEquals("No NonTax LineItem Creation Failed", item2ExpectedString,item2.toString())
    }

    @Test
    fun lineItemTaxableZero_ZeroValueGiven_LineItemCreationSuccessful(){
        var item2 = LineItem(0.00, 3.00, "Purchase with No S+H", "2/15/2023", "Home Depot", CardType.JL, PurchaseType.PURCHASE)
        var item2ExpectedString = "LineItem(totalTaxable=0.0, totalNonTaxable=3.0, description=Purchase with No S+H, date=2/15/2023, vendor=Home Depot, cardType=JL, purchaseType=PURCHASE)"
        assertEquals("No NonTax LineItem Creation Failed", item2ExpectedString,item2.toString())
    }
}