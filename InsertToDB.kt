package com.example.tryingtodo

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


fun insertIncomeToDB(context: Context, category: String, amount: Double, date: String) {
    val db = DBSupport(context)
    db.insertIncome(category, amount, date)
}

fun insertExpenseToDB(context: Context, category: String, amount: Double, date: String) {
    val db = DBSupport(context)
    db.insertExpense(category, amount, date)
}

/*@Composable
fun ButtonRow(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { db.clearAllData() },
            modifier.width(100.dp)
        ) {
            Text(text = "All")
        }
        Button(
            onClick = {},
            modifier.width(100.dp)
        ) {
            Text(text = "Month")
        }
        Button(
            onClick = {},
            modifier.width(100.dp)
        ) {
            Text(text = "Week")
        }
    }
}*/