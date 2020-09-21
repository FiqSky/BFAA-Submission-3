package com.fiqsky.customerapp.db

import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {
    companion object {
        const val AUTHORITY = "com.fiqsky.githubuserapp"
        const val SCHEME = "context"
    }

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

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}