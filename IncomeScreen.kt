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
fun IncomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)
    var incomeList by remember { mutableStateOf(db.getIncome()) }

    val refreshIncomeList: () -> Unit = {
        incomeList = db.getIncome()
    }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            refreshIncomeList()
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
            TopBar(name = "Income")
            Spacer(modifier = Modifier.height(10.dp))
            IncomeCategory()
            IncomeCategory2()
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = refreshIncomeList,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Refresh Income", fontWeight = FontWeight.Bold, color = Color.White)
            }
            Spacer(modifier = Modifier.height(15.dp))
            IncomeList(incomeData = incomeList)
        }
    }
}

@Composable
fun IncomeCategory() {
    val items = listOf(
        IconsWithName(name = "Salary", iconRes = R.drawable.salary),
        IconsWithName(name = "Deposits", iconRes = R.drawable.deposit),
        IconsWithName(name = "Bonds", iconRes = R.drawable.bond),
        IconsWithName(name = "Stocks", iconRes = R.drawable.stocks)
    )
    IconsRow(iconsList = items)
}


@Composable
fun IncomeCategory2() {
    val items = listOf(
        IconsWithName(name = "Rental", iconRes = R.drawable.rent),
        IconsWithName(name = "P2P", iconRes = R.drawable.p2p),
        IconsWithName(name = "Market", iconRes = R.drawable.market),
    )
    IconsRow(iconsList = items)
}

@Composable
fun IncomeList(modifier: Modifier = Modifier, incomeData: List<Map<String, Any>>) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.DarkGray.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Column {
            Text(
                "Income List",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            LazyColumn {
                items(incomeData) { item ->
                    IncomeItem(item)
                }
            }
        }
    }
}

@Composable
fun IncomeItem(item: Map<String, Any>) {
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
