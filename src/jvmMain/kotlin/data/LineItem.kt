package data

enum class PurchaseType {
    AH,
    PH,
    ME,
    TRV,
    JL,
    RMBS
}

/**
 * This data class will be a line item read from the Expense Report to be used in the Team Expense Breakdown
 * totalTaxable - Double value that is the total amount of the materials minus shipping and handling
 * totalNonTaxable - Double value of nontaxable totals this is shipping and handling, services, etc.
 * description - this is a string value that describes what the purchase was for
 * date - this is a string containing the date of the purchase
 * vendor - this is a string value of the vendor of the purchase (who sold the items purchased)
 * type - this is an enum with different types of purchases/card types
 */
data class LineItem(val totalTaxable: Double, val totalNonTaxable: Double, val description: String,
                    val date: String, val vendor: String, val type: PurchaseType){
}
