package com.example.tryingtodo

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryingtodo.ui.theme.*


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = DBSupport(context)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = FullBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar(name = "Home")
            Spacer(modifier = Modifier.height(20.dp))

            Budget(dbSupport = db)

            Spacer(modifier = Modifier.height(20.dp))

            IncomeExpenseInfo(dbSupport = db)
        }
    }
}

@Composable
fun Budget(modifier: Modifier = Modifier, dbSupport: DBSupport) {
    val incomeList = dbSupport.getIncome()
    val expensesList = dbSupport.getExpenses()

    val total = incomeList.sumOf { it[DBSupport.COLUMN_INCOME_AMOUNT] as Double }.toInt()
    val expenses = expensesList.sumOf { it[DBSupport.COLUMN_EXPENSES_AMOUNT] as Double }.toInt()
    val budget = total - expenses

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Your Budget",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(

            ) {
                Text(
                    text = "Income: \$${total}",
                    color = Color.Black,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Expenses: \$${expenses}",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Budget: \$${budget}",
                color = if (budget >= 0) Color(46,111,64) else Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun IncomeExpenseInfo(modifier: Modifier = Modifier, dbSupport: DBSupport) {
    val incomeCategoryWithHighestAmount = dbSupport.getIncomeByCategory().maxByOrNull { it.value }
    val expenseCategoryWithHighestAmount = dbSupport.getExpensesByCategory().maxByOrNull { it.value }

    val mostCommonIncomeCategory = dbSupport.getIncomeByCategory().maxByOrNull { it.value }?.key
    val mostCommonExpenseCategory = dbSupport.getExpensesByCategory().maxByOrNull { it.value }?.key

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        InfoCard(
            title = "Income",
            subtitle = "Highest Category",
            category = mostCommonIncomeCategory ?: "N/A",
            amount = incomeCategoryWithHighestAmount?.value?.toInt() ?: 0
        )


        InfoCard(
            title = "Expenses",
            subtitle = "Highest Category",
            category = mostCommonExpenseCategory ?: "N/A",
            amount = expenseCategoryWithHighestAmount?.value?.toInt() ?: 0
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        InfoCard(
            title = "Most Frequent Income",
            subtitle = "Category",
            category = mostCommonIncomeCategory ?: "N/A",
            amount = incomeCategoryWithHighestAmount?.value?.toInt() ?: 0
        )


        InfoCard(
            title = "Most Frequent Expense",
            subtitle = "Category",
            category = mostCommonExpenseCategory ?: "N/A",
            amount = expenseCategoryWithHighestAmount?.value?.toInt() ?: 0
        )
    }
}

@Composable
fun InfoCard(
    title: String,
    subtitle: String,
    category: String,
    amount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .background(Color.LightGray)
            .width(180.dp)
            .height(180.dp),
        shape = RoundedCornerShape(12.dp),



    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = Color.Black,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Amount: \$${amount}",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}


