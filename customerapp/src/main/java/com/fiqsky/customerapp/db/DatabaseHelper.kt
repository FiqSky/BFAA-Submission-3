package com.fiqsky.customerapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.ID
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.NAME
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.BLOG
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.REPO
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.FOLLOWER
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.FOLLOWING

internal class DatabaseHelper(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_user"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " (${ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $NAME TEXT," +
                " $AVATAR_URL TEXT," +
                " $LOCATION TEXT," +
                " $COMPANY TEXT," +
                " $BLOG TEXT," +
                " $REPO TEXT NOT NULL," +
                " $FOLLOWER TEXT NOT NULL," +
                " $FOLLOWING TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}