package com.example.tryingtodo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Expenses.route) { ExpensesScreen() }
        composable(BottomNavItem.Stats.route) { StatisticScreen() }
        composable(BottomNavItem.Income.route) { IncomeScreen() }
    }
}
