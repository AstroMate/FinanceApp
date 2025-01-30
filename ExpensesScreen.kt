package com.example.tryingtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryingtodo.ui.theme.FullBlack

@Composable
fun ExpensesScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)
    var expensesList by remember { mutableStateOf(db.getExpenses()) }

    val refreshExpensesList: () -> Unit = {
        expensesList = db.getExpenses()
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000) // Refresh every second
            refreshExpensesList()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FullBlack)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(name = "Expenses")



            Spacer(modifier = Modifier.height(10.dp))

            ExpensesCategory()
            ExpensesCategory2()

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = refreshExpensesList,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Refresh Expenses", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(15.dp))

            ExpensesList(expensesData = expensesList)
        }
    }
}


@Composable
fun ExpensesCategory() {
    val items = listOf(
        IconsWithName(name = "Clothes", iconRes = R.drawable.clothes),
        IconsWithName(name = "Food", iconRes = R.drawable.food),
        IconsWithName(name = "Car", iconRes = R.drawable.car),
        IconsWithName(name = "Housing", iconRes = R.drawable.home)
    )
    IconsRow(iconsList = items)
}


@Composable
fun ExpensesCategory2() {
    val items = listOf(
        IconsWithName(name = "Education", iconRes = R.drawable.education),
        IconsWithName(name = "Loan", iconRes = R.drawable.loan),
        IconsWithName(name = "Health", iconRes = R.drawable.health),
        IconsWithName(name = "Entertainment", iconRes = R.drawable.happy)
    )
    IconsRow(iconsList = items)
}


@Composable
fun ExpensesList(modifier: Modifier = Modifier, expensesData: List<Map<String, Any>>) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.DarkGray.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(
                "Expenses List",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            LazyColumn {
                items(expensesData) { item ->
                    ExpenseItem(item)
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(item: Map<String, Any>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF37474F)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(item["category"].toString(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("${item["amount"]} $", color = Color.Yellow, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(item["date"].toString(), color = Color.LightGray, fontSize = 14.sp)
        }
    }
}
