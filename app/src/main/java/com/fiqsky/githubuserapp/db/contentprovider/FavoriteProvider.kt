package com.fiqsky.githubuserapp.db.contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.fiqsky.githubuserapp.db.DatabaseContract
import com.fiqsky.githubuserapp.db.UserHelper

class FavoriteProvider : ContentProvider() {

    companion object{
        private const val USER = 1
        private const val USER_ID = 2
        private lateinit var helper: UserHelper

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(
                DatabaseContract.AUTHORITY,
                DatabaseContract.UserColumns.TABLE_NAME, USER
            )
            sUriMatcher.addURI(
                DatabaseContract.AUTHORITY,
                "${DatabaseContract.UserColumns.TABLE_NAME}/#",
                USER_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        helper = UserHelper.getInstance(context as Context)
        helper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> helper.queryAll()
            USER_ID -> helper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(p0) -> helper.insert(p1)
            else -> 0
        }
        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI, null)
        return Uri.parse("${DatabaseContract.UserColumns.CONTENT_URI}/$added")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(p0) -> helper.deleteById(p0.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(DatabaseContract.UserColumns.CONTENT_URI, null)
        return deleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}