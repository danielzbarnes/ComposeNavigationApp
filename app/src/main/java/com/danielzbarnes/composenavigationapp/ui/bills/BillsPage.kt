package com.danielzbarnes.composenavigationapp.ui.bills

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import com.danielzbarnes.composenavigationapp.R
import com.danielzbarnes.composenavigationapp.data.Bill
import com.danielzbarnes.composenavigationapp.ui.components.BillRow
import com.danielzbarnes.composenavigationapp.ui.components.StatementBody

@Composable
fun BillsBody(bills: List<Bill>) {
    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Bills" },
        items = bills,
        amounts = { bill -> bill.amount },
        colors = { bill -> bill.color },
        amountsTotal = bills.map { bill -> bill.amount }.sum(),
        circleLabel = stringResource(R.string.due),
        rows = { bill ->
            BillRow(bill.name, bill.due, bill.amount, bill.color)
        }
    )
}