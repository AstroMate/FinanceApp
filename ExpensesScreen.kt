package com.example.tryingtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*



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
            kotlinx.coroutines.delay(500)
            refreshExpensesList()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar(name = "Expenses")
            ExpensesPlot()
            ExpensesCategory()
            ExpensesCategory2()
            ButtonRow()
            ExpensesList(expensesData = expensesList)
        }


    }
}

@Composable
fun ExpensesPlot(modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(start = 10.dp, top = 15.dp)
            .fillMaxWidth(0.95f)
            .height(140.dp)
            .background(Color.LightGray, shape = RectangleShape)
    ) { Text(text = "Chart") }
}

@Composable
fun ExpensesCategory() {
    val items = listOf(
        IconsWithName(name = "Clothes", iconRes = R.drawable.home),
        IconsWithName(name = "Food", iconRes = R.drawable.home),
        IconsWithName(name = "Car", iconRes = R.drawable.home),
        IconsWithName(name = "Housing", iconRes = R.drawable.home)
    )
    IconsRow(iconsList = items)
}


@Composable
fun ExpensesCategory2() {
    val items = listOf(
        IconsWithName(name = "Education", iconRes = R.drawable.home),
        IconsWithName(name = "Loan", iconRes = R.drawable.home),
        IconsWithName(name = "Health", iconRes = R.drawable.home),
        IconsWithName(name = "Entertainment", iconRes = R.drawable.home)
    )
    IconsRow(iconsList = items)
}


@Composable
fun ButtonRow(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {},
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
}

@Composable
fun ExpensesList(modifier: Modifier = Modifier, expensesData: List<Map<String, Any>>) {
    Box(
        modifier
            .fillMaxWidth(0.95f)
            .padding(start = 15.dp, top = 15.dp)
            .height(200.dp)
            .background(color = Color.LightGray, shape = RectangleShape)
    ) {
        Column {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                Text("ID", fontWeight = FontWeight.Bold)
                Text("Category", fontWeight = FontWeight.Bold)
                Text("Money", fontWeight = FontWeight.Bold)
                Text("Date", fontWeight = FontWeight.Bold)
            }
            for (item in expensesData) {
                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(item["id"].toString())
                    Text(item["category"].toString())
                    Text(item["amount"].toString())
                    Text(item["date"].toString())
                }
            }
        }
    }
}