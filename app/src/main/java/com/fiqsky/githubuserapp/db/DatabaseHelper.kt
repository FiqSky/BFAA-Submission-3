package com.fiqsky.githubuserapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.ID
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.NAME
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.BLOG
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.REPO
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.FOLLOWER
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.FOLLOWING

internal class DatabaseHelper(context: Context?): SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "db_user"
        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_USER = "CREATE_TABLE ${DatabaseContract().TABLE_NAME}" +
                " (${ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $NAME TEXT NOT NULL," +
                " $AVATAR_URL TEXT NOT NULL," +
                " $LOCATION TEXT NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $BLOG TEXT NOT NULL," +
                " $REPO TEXT NOT NULL," +
                " $FOLLOWER TEXT NOT NULL," +
                " $FOLLOWING TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DatabaseContract().TABLE_NAME}")
        onCreate(db)
    }


}