package com.fiqsky.githubuserapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.fiqsky.githubuserapp.db.DatabaseContract.Companion.AUTHORITY
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.fiqsky.githubuserapp.db.UserHelper

class FavoriteProvider : ContentProvider() {

    companion object{
        private const val USER = 1
        private const val USER_ID = 2
        private lateinit var helper: UserHelper

        //Inisialisasi uriMatcher
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            sUriMatcher.addURI(AUTHORITY,
                "$TABLE_NAME/#",
                USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        helper = UserHelper.getInstance(context as Context)
        helper.open()
        return true
    }

    override fun query(
        /*p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(p0)) {
            USER -> cursor = helper.queryAll() //Dapatkan list user dari db
            USER_ID -> cursor = helper.queryById(p0.lastPathSegment.toString()) //Dapatkan detail user
            else -> cursor = null
        }
        return cursor*/
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            USER -> cursor = helper.queryAll()
            USER_ID -> cursor = helper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(p0) -> helper.insert(p1)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(p0) -> helper.deleteById(p0.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    /*companion object {
        private const val FAV = 1
        private const val FAV_ID = 2
        private lateinit var userHelper: UserHelper
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                FAV_ID
            )
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> userHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            sUriMatcher.match(uri) -> userHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        userHelper = UserHelper.getInstance(context as Context)
        userHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAV -> userHelper.queryAll() // get all data
            FAV_ID -> userHelper.queryById(uri.lastPathSegment.toString()) // get data by id
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Not yet implemented")
    }*/
}
