package com.fiqsky.githubuserapp.db

import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {
    val AUTHORITY = "com.fiqsky.githubuserapp"
    val SCHEME = "context"

//    val TABLE_NAME = "user"

    internal class UserColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR_URL = "avatar_url"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val BLOG = "blog"
            const val REPO = "repo"
            const val FOLLOWER = "follower"
            const val FOLLOWING = "following"

            val CONTENT_URI: Uri = Uri.Builder().scheme(DatabaseContract().SCHEME)
                .authority(DatabaseContract().AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}