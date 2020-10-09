package com.fiqsky.githubuserapp.db

import android.database.Cursor
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.ID
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.USERNAME
//import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.NAME
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.AVATAR_URL
/*import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.LOCATION
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.COMPANY
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.BLOG
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.REPO
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.FOLLOWER
import com.fiqsky.githubuserapp.db.DatabaseContract.UserColumns.Companion.FOLLOWING*/
import com.fiqsky.githubuserapp.utils.User

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

    /*fun mapCursorToArrayList(userCursor: Cursor): ArrayList<User>{
        val userList = ArrayList<User>()

        while (userCursor.moveToNext()){
            val id = userCursor.getString(userCursor.getColumnIndexOrThrow(ID))
            val username = userCursor.getString(userCursor.getColumnIndexOrThrow(USERNAME))
            val name = userCursor.getString(userCursor.getColumnIndexOrThrow(NAME))
            val avatar = userCursor.getString(userCursor.getColumnIndexOrThrow(AVATAR_URL))
            val location = userCursor.getString(userCursor.getColumnIndexOrThrow(LOCATION))
            val company = userCursor.getString(userCursor.getColumnIndexOrThrow(COMPANY))
            val blog = userCursor.getString(userCursor.getColumnIndexOrThrow(BLOG))
            val repo = userCursor.getString(userCursor.getColumnIndexOrThrow(REPO))
            val follower = userCursor.getString(userCursor.getColumnIndexOrThrow(FOLLOWER))
            val following = userCursor.getString(userCursor.getColumnIndexOrThrow(FOLLOWING))
            userList.add(User(id,username,name,avatar,location,company, blog,repo,follower,following))
        }
        return userList
    }*/
}