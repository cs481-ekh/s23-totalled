package processingTests

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import processing.getColumnNames
import kotlin.test.assertTrue

class GetColumnNamesTests {
    private val path = System.getProperty("user.dir")

    @Test
    fun givenDefaultColumnNamesTxt_whenGetColumnNamesCalled_thenCorrectMapsReturned() {
        val txtFilePath = "$path/src/jvmTest/TestInputFiles/column-names-default.txt"
        val expectedExpenseLogMap = mapOf(
            "Senior Design PO" to "Senior Design PO",
            "Business Purpose" to "Business Purpose",
            "Total Amount" to "Total Amount",
            "Amount 2" to "Amount 2",
            "Card" to "Card",
            "Date Ordered" to "Date Ordered",
            "Vendor Name" to "Vendor Name",
        )
        val expectedProjectBookMap = mapOf(
            "Team Abbr" to "Team Abbr",
            "Sponsor / Funding Department" to "Sponsor / Funding Department",
            "Billing Address / BSU Department ID" to "Billing Address / BSU Department ID",
            "Sponsor Contact" to "Sponsor Contact",
            "Sponsor Contact email (optional)" to "Sponsor Contact email (optional)",
        )

        val columnNames = getColumnNames(txtFilePath)

        assertTrue { columnNames.expenseLogColumnNames == expectedExpenseLogMap }
        assertTrue { columnNames.projectBookColumnNames == expectedProjectBookMap }
    }

    @Test
    fun givenGarbageColumnNamesTxt_whenGetColumnNamesCalled_thenCorrectMapsReturned() {
        val txtFilePath = "$path/src/jvmTest/TestInputFiles/column-names-garbage.txt"
        val expectedExpenseLogMap = mapOf(
            "kdjflg192 dsls 302934js" to "Senior Design PO",
            "n9043@2222 222223j" to "Business Purpose",
            "kljfld9230{RT}Y" to "Total Amount",
            "12930lksAKLA()FKL" to "Amount 2",
            "43jljdfklglmn asda" to "Card",
            "943059j--4" to "Date Ordered",
            ">HJ/hjK>//hj/HJ\\a" to "Vendor Name",
        )
        val expectedProjectBookMap = mapOf(
            "/nkasjd3203" to "Team Abbr",
            "ksjdf/naks jda299" to "Sponsor / Funding Department",
            "490 iioIJAidjoaji eeeeee" to "Billing Address / BSU Department ID",
            "(@))@(\$))@(\$#)" to "Sponsor Contact",
            "so true" to "Sponsor Contact email (optional)",
        )

        val columnNames = getColumnNames(txtFilePath)

        assertTrue { columnNames.expenseLogColumnNames == expectedExpenseLogMap }
        assertTrue { columnNames.projectBookColumnNames == expectedProjectBookMap }
    }

    @Test
    fun givenEmptyColumnNamesTxt_whenGetColumnNamesCalled_thenExceptionThrown() {
        val txtFilePath = "$path/src/jvmTest/TestInputFiles/column-names-empty.txt"
        assertThrows<Exception> { getColumnNames(txtFilePath) }
    }
}
