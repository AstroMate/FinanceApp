@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tryingtodo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.text.input.KeyboardType
import com.example.tryingtodo.ui.theme.FullBlack
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import java.text.SimpleDateFormat
import java.util.*


data class IconsWithName(
    val name: String,
    val iconRes: Int
)

@Composable
fun IconsRow(iconsList: List<IconsWithName>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(iconsList) { item ->
            IconCard(item = item)
        }
    }
}

@Composable
fun IconCard(item: IconsWithName, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .width(100.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = item.iconRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = FullBlack)
                .clickable { showDialog = true },
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            text = item.name,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center
        )
    }

    if (showDialog) {
        InputDialog(
            category = item.name,
            onDismiss = { showDialog = false },
            onSave = { amount, date ->
                if (item.name in listOf(
                        "Salary", "Deposits", "Bonds", "Stocks",
                        "Rental", "P2P", "Market"
                    )
                ) {
                    insertIncomeToDB(context, item.name, amount, date)
                } else {
                    insertExpenseToDB(context, item.name, amount, date)
                }
                showDialog = false
            }
        )
    }
}


@Composable
fun InputDialog(
    category: String,
    onDismiss: () -> Unit,
    onSave: (amount: Double, date: String) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("OK", color = Color.White)
                }
            }
        ) {
            val datePickerState = rememberDatePickerState()
            DatePicker(
                state = datePickerState,
                modifier = Modifier.background(Color.Black)
            )
            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let {
                    val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(it))
                    date = selectedDate
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.Black,
        title = {
            Text(text = "Add Entry for $category", color = Color.White)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        if (it.all { char ->
                                char.isDigit() || char in listOf(
                                    '-',
                                    '/',
                                    '+',
                                    '.',
                                    ','
                                )
                            }) {
                            amount = it
                        }
                    },
                    label = { Text("Amount", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        cursorColor = Color.White
                    )
                )


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Date (YYYY-MM-DD)", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    trailingIcon = {
                        Image(
                            painter = painterResource(R.drawable.calendar),
                            contentDescription = "Pick Date",
                            modifier = Modifier
                                .clickable { showDatePicker = true }
                                .width(30.dp)
                                .height(30.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        cursorColor = Color.White
                    )
                )

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (amount.isNotEmpty() && date.isNotEmpty()) {
                        onSave(amount.toDouble(), date)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}

