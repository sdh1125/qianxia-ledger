package com.example.qianxialedger.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qianxialedger.viewmodel.TransactionViewModel
import java.util.*

@Composable
fun AddTransactionScreen(vm: TransactionViewModel = viewModel()) {
    var amountText by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }
    var category by remember { mutableStateOf("餐饮") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("添加账单", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = amountText, onValueChange = { amountText = it }, label = { Text("金额") })
        Spacer(Modifier.height(8.dp))
        Row { 
            Button(onClick = { isExpense = true }, colors = ButtonDefaults.buttonColors(if (isExpense) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)) { Text("支出") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { isExpense = false }, colors = ButtonDefaults.buttonColors(if (!isExpense) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)) { Text("收入") }
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("类别") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("备注") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val amt = amountText.toDoubleOrNull() ?: 0.0
            if (amt > 0.0) {
                vm.addTransaction(amt, isExpense, category, Calendar.getInstance().timeInMillis, if (note.isBlank()) null else note)
                amountText = ""
                note = ""
            }
        }) { Text("保存") }
    }
}
