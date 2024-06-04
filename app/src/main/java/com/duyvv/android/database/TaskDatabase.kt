package com.duyvv.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duyvv.android.database.dao.TaskDao
import com.duyvv.android.database.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}
