package com.example.qianxialedger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val isExpense: Boolean,
    val category: String,
    val timestamp: Long,
    val note: String?
)
