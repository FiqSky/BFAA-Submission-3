package com.fiqsky.customerapp.db

import android.database.Cursor
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
/*import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.BLOG
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.FOLLOWER
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.FOLLOWING*/
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.ID
/*import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.NAME
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.REPO*/
import com.fiqsky.customerapp.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.fiqsky.customerapp.utils.User

object MappingHelper {

    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()

        userCursor?.apply {
            while (userCursor.moveToNext()) {
                val id = userCursor.getString(getColumnIndexOrThrow(ID))
                val username = userCursor.getString(getColumnIndexOrThrow(USERNAME))
                val avatar = userCursor.getString(getColumnIndexOrThrow(AVATAR_URL))
                /*val name = userCursor.getString(userCursor.getColumnIndexOrThrow(NAME))
                val location = userCursor.getString(userCursor.getColumnIndexOrThrow(LOCATION))
                val company = userCursor.getString(userCursor.getColumnIndexOrThrow(COMPANY))
                val blog = userCursor.getString(userCursor.getColumnIndexOrThrow(BLOG))
                val repo = userCursor.getString(userCursor.getColumnIndexOrThrow(REPO))
                val follower = userCursor.getString(userCursor.getColumnIndexOrThrow(FOLLOWER))
                val following = userCursor.getString(userCursor.getColumnIndexOrThrow(FOLLOWING))*/
                userList.add(
                    User(
                        id = id,
                        userName = username,
                        avatarUrl = avatar
                    )
                )
            }
        }
        return userList
    }
}