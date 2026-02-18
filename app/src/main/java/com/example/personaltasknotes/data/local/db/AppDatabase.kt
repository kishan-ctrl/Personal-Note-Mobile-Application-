package com.example.personaltasknotes.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.personaltasknotes.data.local.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "tasknote_db"
                        ).build()
                    }
                }
            }
        }

        fun get(): AppDatabase {
            return INSTANCE ?: throw IllegalStateException("AppDatabase is not initialized. Call AppDatabase.init() in Application.")
        }
    }
}