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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun IncomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)

    // Example: Insert data (optional, for testing)
    db.insertIncome("Paycheck", 1000.0, "2025-01-17")

    // Fetch data from the database
    val incomeList = db.getIncome()

    val navController = rememberNavController()
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar(name = "Income")
            IncomePlot()
            IncomeCategory()
            IncomeCategory2()
            ButtonRow()

            // Pass incomeList to IncomeList
            IncomeList(incomeData = incomeList)
        }
    }
}


@Composable
fun IncomePlot(modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(start = 10.dp, top = 15.dp)
            .fillMaxWidth(0.95f)
            .height(140.dp)
            .background(Color.LightGray, shape = RectangleShape)
    ) { Text(text = "Chart") }
}

@Composable
fun IncomeCategory() {
    val items = listOf(
        IconsWithName(name = "Aaaa", iconRes = R.drawable.home),
        IconsWithName(name = "bbbb", iconRes = R.drawable.home),
        IconsWithName(name = "cccc", iconRes = R.drawable.home),
        IconsWithName(name = "ddddd", iconRes = R.drawable.home)
    )
    IconsRow(iconsList = items)
}


@Composable
fun IncomeCategory2() {
    val items = listOf(
        IconsWithName(name = "Paycheck", iconRes = R.drawable.home),
        IconsWithName(name = "Investments", iconRes = R.drawable.home),
        IconsWithName(name = "Gifts", iconRes = R.drawable.home),
        IconsWithName(name = "Stealing", iconRes = R.drawable.home)
    )
    IconsRow(iconsList = items)
}

@Composable
fun IncomeList(modifier: Modifier = Modifier, incomeData: List<Map<String, Any>>) {
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
                Text("ID")
                Text("Category")
                Text("Money")
                Text("Date")
            }
            for (item in incomeData) {
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

