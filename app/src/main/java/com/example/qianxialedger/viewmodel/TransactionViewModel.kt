package com.example.qianxialedger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.qianxialedger.data.TransactionEntity
import com.example.qianxialedger.data.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = TransactionRepository(application.applicationContext)

    val transactions: StateFlow<List<TransactionEntity>> = repo.getAll()
        .map { it }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addTransaction(amount: Double, isExpense: Boolean, category: String, timestamp: Long, note: String?) {
        viewModelScope.launch {
            val tx = TransactionEntity(amount = amount, isExpense = isExpense, category = category, timestamp = timestamp, note = note)
            repo.insert(tx)
        }
    }

    fun delete(tx: TransactionEntity) {
        viewModelScope.launch { repo.delete(tx) }
    }
}
