package data

data class Team(val teamName: String, var lineItemList: List<LineItem>, var totalTaxable: Double,
                var totalShippingHandling: Double, var totalServices: Double, var totalTravel: Double,
                var totalTaxes: Double, var totalCharges:Double) {
    constructor(teamName: String, lineItemList: List<LineItem>) : this(teamName, lineItemList, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0)
}
