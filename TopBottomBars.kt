package com.example.tryingtodo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryingtodo.ui.theme.BottomBlue
import com.example.tryingtodo.ui.theme.TextWhite
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tryingtodo.ui.theme.FullBlack


@Composable
fun TopBar(modifier: Modifier = Modifier, name: String) {
    val height = 70.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .background(color = Color.Black)
            .fillMaxWidth(1f)
            .height(height)

    ) {
        Text(
            text = name,
            color = TextWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun BottomNav(modifier: Modifier = Modifier, navController: NavController) {
    val height = 80.dp
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Expenses,
        BottomNavItem.Stats,
        BottomNavItem.Income
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .background(color = Color.Black)
            .fillMaxWidth()
            .height(height)
    ) {
        items.forEach { item ->
            val backStackEntry = navController.currentBackStackEntryAsState()
            val selected = backStackEntry.value?.destination?.route == item.route
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.route,
                modifier = Modifier
                    .size(70.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                colorFilter = ColorFilter.tint(if (selected) Color.Yellow else Color.White)
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: Int) {
    object Home : BottomNavItem("home", R.drawable.home)
    object Expenses : BottomNavItem("expenses", R.drawable.expenses)
    object Stats : BottomNavItem("stats", R.drawable.stats)
    object Income : BottomNavItem("income", R.drawable.income)
}

