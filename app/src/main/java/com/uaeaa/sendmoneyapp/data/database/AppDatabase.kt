package com.uaeaa.sendmoneyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uaeaa.sendmoneyapp.data.database.dao.RequestHistoryDao
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity

@Database(entities = [RequestHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requestHistoryDao(): RequestHistoryDao
}