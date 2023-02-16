package data

/**
 * This data class will hold the aggregated data for each Team, this includes team name, a list of line items, and totals
 *
 * teamName - String of the Team Name
 *
 * lineItemList - a mutable list of line items of team purchases
 *
 * totalTaxable - The total cost of all taxable items
 *
 * totalShippingHandling - The total of all Shipping and Handling Charges
 *
 * totalServices - Total of all Services charged
 *
 * totalTravel - Total of all Travel Related Charges
 *
 * totalTaxes - The total amount of taxes charged
 *
 * totalCharges - The sum of all other Totals (taxable + S&H + Services + Travel + taxes)
 */
data class Team(val teamName: String, var lineItemList: MutableList<LineItem>, var totalTaxable: Double,
                var totalShippingHandling: Double, var totalServices: Double, var totalTravel: Double,
                var totalTaxes: Double, var totalCharges:Double) {
    constructor(teamName: String, lineItemList: MutableList<LineItem>) : this(teamName, lineItemList, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0)
}
