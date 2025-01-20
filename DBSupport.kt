package com.example.tryingtodo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBSupport(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MoneyTracker.db"
        const val DATABASE_VERSION = 1

        // Income Table
        const val TABLE_INCOME = "Income"
        const val COLUMN_INCOME_ID = "id"
        const val COLUMN_INCOME_CATEGORY = "category"
        const val COLUMN_INCOME_AMOUNT = "amount"
        const val COLUMN_INCOME_DATE = "date"

        // Expenses Table
        const val TABLE_EXPENSES = "Expenses"
        const val COLUMN_EXPENSES_ID = "id"
        const val COLUMN_EXPENSES_CATEGORY = "category"
        const val COLUMN_EXPENSES_AMOUNT = "amount"
        const val COLUMN_EXPENSES_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createIncomeTable = """
            CREATE TABLE $TABLE_INCOME (
                $COLUMN_INCOME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_INCOME_CATEGORY TEXT NOT NULL,
                $COLUMN_INCOME_AMOUNT REAL NOT NULL,
                $COLUMN_INCOME_DATE TEXT NOT NULL
            )
        """.trimIndent()

        val createExpensesTable = """
            CREATE TABLE $TABLE_EXPENSES (
                $COLUMN_EXPENSES_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EXPENSES_CATEGORY TEXT NOT NULL,
                $COLUMN_EXPENSES_AMOUNT REAL NOT NULL,
                $COLUMN_EXPENSES_DATE TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createIncomeTable)
        db.execSQL(createExpensesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INCOME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }

    // Insert Data into Income Table
    fun insertIncome(category: String, amount: Double, date: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_INCOME_CATEGORY, category)
        values.put(COLUMN_INCOME_AMOUNT, amount)
        values.put(COLUMN_INCOME_DATE, date)
        return db.insert(TABLE_INCOME, null, values)
    }

    // Insert Data into Expenses Table
    fun insertExpense(category: String, amount: Double, date: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EXPENSES_CATEGORY, category)
        values.put(COLUMN_EXPENSES_AMOUNT, amount)
        values.put(COLUMN_EXPENSES_DATE, date)
        return db.insert(TABLE_EXPENSES, null, values)
    }

    // Fetch Data from Income Table
    fun getIncome(): List<Map<String, Any>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_INCOME", null)
        val result = mutableListOf<Map<String, Any>>()

        while (cursor.moveToNext()) {
            val data = mapOf(
                COLUMN_INCOME_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCOME_ID)),
                COLUMN_INCOME_CATEGORY to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_CATEGORY)),
                COLUMN_INCOME_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INCOME_AMOUNT)),
                COLUMN_INCOME_DATE to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCOME_DATE))
            )
            result.add(data)
        }
        cursor.close()
        return result
    }

    // Fetch Data from Expenses Table
    fun getExpenses(): List<Map<String, Any>> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EXPENSES", null)
        val result = mutableListOf<Map<String, Any>>()

        while (cursor.moveToNext()) {
            val data = mapOf(
                COLUMN_EXPENSES_ID to cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSES_ID)),
                COLUMN_EXPENSES_CATEGORY to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSES_CATEGORY)),
                COLUMN_EXPENSES_AMOUNT to cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EXPENSES_AMOUNT)),
                COLUMN_EXPENSES_DATE to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSES_DATE))
            )
            result.add(data)
        }
        cursor.close()
        return result
    }
}
