package data

/**
 * An Enum used in differentiating between card types, Reimbursements, and Travel in the Expense Workbook
 *
 * AH, PH, ME, JL - Different Cards Used for Purchases
 *
 * TRV - Any travel Expenses that were paid for with something other than one of the cards
 *
 * RMB - Any reimbursements paid for services, or material purchases
 *
 * NONE - No Card Type Found in the Expense Report (Such as Sponsorship Fees)
 */
enum class CardType {
    AH,
    PH,
    ME,
    JL,
    TRV,
    RMB,
    NONE,
}

/**
 * An Enum used in differentiating between purchasing types
 * We will need to separate material purchases, travel, and services for tax reasons
 *
 * PURCHASE is any materials paid for that can be taxed, if a line item is of this type,
 *          any NonTaxable totals is Shipping and Handling
 *
 * TRAVEL is any travel expenses (Car Rentals, Hotels, etc.)
 *
 * SERVICE is any services paid for (Welding, Sponsor Fee, etc.) - Non Taxable
 */
enum class PurchaseType {
    PURCHASE, TRAVEL, SERVICE
}

/**
 * This data class will be a line item read from the Expense Report to be used in the Team Expense Breakdown
 * All fields are val's, so they cannot be changed once the LineItem is created.
 *
 * totalTaxable - Double value that is the total amount of the materials minus shipping and handling - cannot be null but can be 0
 *
 * totalNonTaxable - Double value of nontaxable totals this is shipping and handling, services, etc. - cannot be null but can be 0
 *
 * description - this is a string value that describes what the purchase was for
 *
 * date - this is a string containing the date of the purchase
 *
 * vendor - this is a string value of the vendor of the purchase (who sold the items purchased)
 *
 * cardType - this is an enum with different types of cards used in the Expense Workbook
 *
 * purchaseType - this is an enum that will allow us to separate Travel, services, and material purchases
 */
data class LineItem(
    val totalTaxable: Double,
    val totalNonTaxable: Double,
    val description: String,
    val date: String,
    val vendor: String,
    val cardType: CardType,
    val purchaseType: PurchaseType,
    val poNumber: String,
)
