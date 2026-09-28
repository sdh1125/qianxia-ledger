package com.example.qianxialedger.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class TransactionRepository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.transactionDao()

    fun getAll(): Flow<List<TransactionEntity>> = dao.getAll()

    suspend fun insert(tx: TransactionEntity): Long = dao.insert(tx)

    suspend fun update(tx: TransactionEntity) = dao.update(tx)

    suspend fun delete(tx: TransactionEntity) = dao.delete(tx)

    fun getBetween(from: Long, to: Long): Flow<List<TransactionEntity>> = dao.getBetween(from, to)
}
