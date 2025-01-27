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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            kotlinx.coroutines.delay(500)
            refreshExpensesList()
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(color = FullBlack)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar(name = "Expenses")
            ExpensesPlot()
            ExpensesCategory()
            ExpensesCategory2()
            //ButtonRow()
            ExpensesList(expensesData = expensesList)
            //populateExpenseData(dbSupport = db)
        }


    }
}


fun populateExpenseData(dbSupport: DBSupport) {
    val expenseCategories = listOf(
        "Clothes", "Food", "Car", "Housing", "Education", "Loan", "Health", "Entertainment"
    )
    val random = java.util.Random()

    val data = List(5) {
        val category = expenseCategories[random.nextInt(expenseCategories.size)]
        val amount = 18000 + random.nextInt(5000)
        val month = random.nextInt(12) + 1
        val day = random.nextInt(28) + 1

        // Ensure month and day have leading zeros if between 1-9
        val date = String.format("2025-%02d-%02d", month, day)

        Triple(category, amount.toDouble(), date)
    }

    data.forEach { (category, amount, date) ->
        dbSupport.insertExpense(category, amount, date)
    }

    println("Inserted ${data.size} rows into Expenses table.")
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
fun ExpensesList(modifier: Modifier = Modifier, expensesData: List<Map<String, Any>>) {
    val headerSize = 24.sp
    val bodySize = 16.sp

    Box(
        modifier
            .fillMaxWidth(1f)
            .padding(start = 10.dp, top = 15.dp, end = 10.dp)
            .height(700.dp)
            .background(color = FullBlack, shape = RectangleShape)
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(400.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "ID",
                    fontWeight = FontWeight.Bold,
                    fontSize = headerSize,
                    color = Color.White
                )
                Text(
                    "Category",
                    fontWeight = FontWeight.Bold,
                    fontSize = headerSize,
                    color = Color.White
                )
                Text(
                    "Money",
                    fontWeight = FontWeight.Bold,
                    fontSize = headerSize,
                    color = Color.White
                )
                Text(
                    "Date",
                    fontWeight = FontWeight.Bold,
                    fontSize = headerSize,
                    color = Color.White
                )
            }


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(expensesData) { item ->
                    Row(
                        modifier = Modifier
                            .width(400.dp)
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            item["id"].toString(),
                            modifier.padding(start = 35.dp),
                            color = Color.White,
                            fontSize = bodySize,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            item["category"].toString(),
                            modifier.padding(start = 25.dp),
                            color = Color.White,
                            fontSize = bodySize,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            item["amount"].toString(),
                            modifier.padding(start = 35.dp),
                            color = Color.White,
                            fontSize = bodySize,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            item["date"].toString(),
                            modifier.padding(end = 10.dp),
                            color = Color.White,
                            fontSize = bodySize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}