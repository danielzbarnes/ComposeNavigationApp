package com.danielzbarnes.composenavigationapp.ui.accounts

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.danielzbarnes.composenavigationapp.R
import com.danielzbarnes.composenavigationapp.data.Account
import com.danielzbarnes.composenavigationapp.ui.components.AccountRow
import com.danielzbarnes.composenavigationapp.ui.components.StatementBody

@Composable
fun AccountsBody(
    accounts: List<Account>,
    onAccountClick: (String) -> Unit = {},
) {
    StatementBody(
        modifier = Modifier.semantics { contentDescription = "Accounts Screen" },
        items = accounts,
        amounts = { account -> account.balance },
        colors = { account -> account.color },
        amountsTotal = accounts.map { account -> account.balance }.sum(),
        circleLabel = stringResource(R.string.total),
        rows = { account ->
            AccountRow(
                modifier = Modifier.clickable { onAccountClick(account.name) },
                name = account.name,
                number = account.number,
                amount = account.balance,
                color = account.color
            )
        }
    )
}

/**
 * Detail screen for a single account.
 */
@Composable
fun SingleAccountBody(account: Account) {
    StatementBody(
        items = listOf(account),
        colors = { account.color },
        amounts = { account.balance },
        amountsTotal = account.balance,
        circleLabel = account.name,
    ) { row ->
        AccountRow(
            name = row.name,
            number = row.number,
            amount = row.balance,
            color = row.color
        )
    }
}