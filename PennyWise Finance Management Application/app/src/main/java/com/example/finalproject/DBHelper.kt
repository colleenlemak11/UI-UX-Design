package com.example.finalproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        //SQLite creating table query
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                DATE_COL + " TEXT," +
                MONEY_COL + " TEXT," +
                CATEGORY_COL + " TEXT," +
                PERSON_COL + " TEXT," +
                PAID_COL + " TEXT" + ")")

        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addExpense(date : String, money : String, category: String, person: String, paid: String ){

        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(DATE_COL, date)
        values.put(MONEY_COL, money)
        values.put(CATEGORY_COL, category)
        values.put(PERSON_COL, person)
        values.put(PAID_COL, paid)


        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // closing our database
        db.close()
    }

    fun updateExpense(date: String, money: String, category: String, person: String, paid: String, mostRecent: Boolean) {
        val values = ContentValues()

        // Inserting values
        values.put(DATE_COL, date)
        values.put(MONEY_COL, money)
        values.put(CATEGORY_COL, category)
        values.put(PERSON_COL, person)
        values.put(PAID_COL, paid)

        // Creating a writable variable of our database
        val db = this.writableDatabase

        if (mostRecent) {
            // Update the most recent entry
            db.update(
                TABLE_NAME,
                values,
                "$DATE_COL = (SELECT MAX($DATE_COL) FROM $TABLE_NAME)",
                null
            )
        } else {
            // Insert new entry
            db.insert(TABLE_NAME, null, values)
        }

        // Closing the database
        db.close()
    }


    // all data from our database
    fun getName(): Cursor? {

        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // read data from the database
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun deleteExpenseByPerson(money: String): Int {
        val db = this.writableDatabase
        val deletedRows = db.delete(TABLE_NAME, "$PERSON_COL = ?", arrayOf(money))
        db.close()
        return deletedRows
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "CASH_COWS"

        // below is the variable for database version
        private val DATABASE_VERSION = 7

        // below is the variable for table name
        val TABLE_NAME = "finance_entries"

        val ID_COL = "id"

        val DATE_COL = "date"

        val MONEY_COL = "money"

        val CATEGORY_COL = "category"

        val PERSON_COL = "person"

        val PAID_COL = "paid"
    }
}
