package com.example.tryingtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tryingtodo.ui.theme.FullBlack


@Composable
fun StatisticScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)

    val totalIncome = db.getIncome().sumOf { it["amount"] as Double }
    val totalExpenses = db.getExpenses().sumOf { it["amount"] as Double }

    val highestIncomeCategory = db.getIncomeByCategory().maxByOrNull { it.value }
    val highestExpenseCategory = db.getExpensesByCategory().maxByOrNull { it.value }

    val mostFrequentIncomeCategory = db.getIncomeByCategory().maxByOrNull { it.value }?.key
    val mostFrequentExpenseCategory = db.getExpensesByCategory().maxByOrNull { it.value }?.key

    val balance = totalIncome - totalExpenses

    Box(modifier = modifier
        .fillMaxSize()
        .background(color = FullBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar(name = "Statistic")

            Spacer(modifier = Modifier.height(16.dp))

            // Total Income vs Total Expenses
            StatisticCard(
                title = "Total Income vs Total Expenses",
                value1 = "Income: \$${totalIncome.toInt()}",
                value2 = "Expenses: \$${totalExpenses.toInt()}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Highest Income Category
            StatisticCard(
                title = "Highest Income Category",
                value1 = highestIncomeCategory?.key ?: "N/A",
                value2 = "Amount: \$${highestIncomeCategory?.value?.toInt() ?: 0}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Highest Expense Category
            StatisticCard(
                title = "Highest Expense Category",
                value1 = highestExpenseCategory?.key ?: "N/A",
                value2 = "Amount: \$${highestExpenseCategory?.value?.toInt() ?: 0}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Most Frequent Income Category
            StatisticCard(
                title = "Most Frequent Income Category",
                value1 = mostFrequentIncomeCategory ?: "N/A",
                value2 = "Amount: \$${highestIncomeCategory?.value?.toInt() ?: 0}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Most Frequent Expense Category
            StatisticCard(
                title = "Most Frequent Expense Category",
                value1 = mostFrequentExpenseCategory ?: "N/A",
                value2 = "Amount: \$${highestExpenseCategory?.value?.toInt() ?: 0}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Balance
            StatisticCard(
                title = "Overall Balance",
                value1 = "Balance: \$${balance.toInt()}",
                value2 = if (balance >= 0) "Positive Balance" else "Negative Balance"
            )
        }
    }
}

// A reusable card to show statistical info
@Composable
fun StatisticCard(title: String, value1: String, value2: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Gray, shape = RectangleShape)
            .padding(16.dp)
    ) {
        Column {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = value1, color = Color.White)
            Text(text = value2, color = Color.White)
        }
    }
}


