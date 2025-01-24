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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*


@Composable
fun IncomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)


    var incomeList by remember { mutableStateOf(db.getIncome()) }


    val refreshIncomeList: () -> Unit = {
        incomeList = db.getIncome()
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(500)
            refreshIncomeList()
        }
    }

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
        IconsWithName(name = "Salary", iconRes = R.drawable.home),
        IconsWithName(name = "Deposits", iconRes = R.drawable.home),
        IconsWithName(name = "Bonds", iconRes = R.drawable.home),
        IconsWithName(name = "Stocks", iconRes = R.drawable.home)
    )
    IconsRow(iconsList = items)
}


@Composable
fun IncomeCategory2() {
    val items = listOf(
        IconsWithName(name = "Rental", iconRes = R.drawable.home),
        IconsWithName(name = "P2P", iconRes = R.drawable.home),
        IconsWithName(name = "Market", iconRes = R.drawable.home),
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
                Text("ID", fontWeight = FontWeight.Bold)
                Text("Category", fontWeight = FontWeight.Bold)
                Text("Money", fontWeight = FontWeight.Bold)
                Text("Date", fontWeight = FontWeight.Bold)
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

