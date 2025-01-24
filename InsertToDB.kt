package com.example.tryingtodo

import android.content.Context


fun insertIncomeToDB(context: Context, category: String, amount: Double, date: String) {
    val db = DBSupport(context)
    db.insertIncome(category, amount, date)
}

fun insertExpenseToDB(context: Context, category: String, amount: Double, date: String) {
    val db = DBSupport(context)
    db.insertExpense(category, amount, date)
}
