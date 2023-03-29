package data

data class ProjectMetadata(
    var teamAbbr: String = "",
    var billTo: String = "",
    var email: String = "",
    var contactInfo: String = "",
    var addressLine1: String = "",
    var addressLine2: String = "",
    var city: String = "",
    var state: String = "",
    var zip: String = "",
    var amount: Double = 0.0,
)
