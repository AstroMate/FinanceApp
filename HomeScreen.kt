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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar(name = "Home")
            Budget()
            IncomeExpensesChart()
            IncomeExpenseInfo()
        }

    }
}

@Composable
fun Budget(modifier: Modifier = Modifier) {
    var budget = 12345
    var total = 15000
    Row(modifier = modifier.padding(top = 20.dp)) {
        Text(
            text = "Your budget:",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(start = 10.dp)
        )

        Text(
            text = "$budget / $total",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun IncomeExpensesChart(modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(start = 10.dp, top = 15.dp)
            .fillMaxWidth(0.95f)
            .height(200.dp)
            .background(Color.LightGray, shape = RectangleShape)
    ) {}
}


@Composable
fun IncomeExpenseInfo(modifier: Modifier = Modifier) {
    Row(

        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = modifier
            .padding(start = 10.dp, top = 15.dp)
            .fillMaxWidth(0.95f)
    ) {
        Box(
            modifier
                .background(Color.LightGray, shape = RectangleShape)
                .height(150.dp)
                .width(180.dp)

        ) { Text(text = "A", color = Color.Black) }
        Box(
            modifier
                .background(Color.LightGray, shape = RectangleShape)
                .height(150.dp)
                .width(180.dp)


        ) { Text(text = "B", color = Color.Black) }
    }
}
