package com.fiqsky.githubuserapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.ID
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fiqsky.githubuserapp.utils.User

class UserHelper(context: Context?) {
    companion object {
        val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var database : SQLiteDatabase

        private var INSTANCE : UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE?: synchronized(this) {
                INSTANCE?: UserHelper(context)
            }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }

    fun deleteByUsername(username: String) : Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }
}
