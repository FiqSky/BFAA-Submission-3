package com.fiqsky.githubuserapp.db

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

/*
@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        */
/**
         * The only instance
         *//*

        private var sInstance: AppDatabase? = null

        */
/**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         *//*

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room
                    .databaseBuilder(context.applicationContext, AppDatabase::class.java, "user")
                    .fallbackToDestructiveMigration()
//                    .enableMultiInstanceInvalidation()
                    .build()
            }
            return sInstance!!
        }
    }
}*/
