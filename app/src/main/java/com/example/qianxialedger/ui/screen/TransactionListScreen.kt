package com.example.qianxialedger.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qianxialedger.viewmodel.TransactionViewModel
import com.example.qianxialedger.data.TransactionEntity

@Composable
fun TransactionListScreen(vm: TransactionViewModel = viewModel()) {
    val transactions = vm.transactions.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(transactions.value) { tx: TransactionEntity ->
            Card(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text(tx.category); Text(tx.note ?: "") }
                    Column { Text(if (tx.isExpense) "-¥${tx.amount}" else "+¥${tx.amount}") }
                }
            }
        }
    }
}
