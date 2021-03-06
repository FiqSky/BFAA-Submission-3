package com.fiqsky.customerapp.db

import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {
    companion object{
        private const val AUTHORITY = "com.fiqsky.githubuserapp"
        const val SCHEME = "content"
    }

    internal class UserColumns: BaseColumns {
        companion object {
            private const val TABLE_NAME = "user"
            const val ID = "id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}