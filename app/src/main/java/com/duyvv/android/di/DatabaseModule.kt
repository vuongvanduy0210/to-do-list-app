package com.duyvv.android.di

import android.content.Context
import androidx.room.Room
import com.duyvv.android.database.TaskDatabase
import com.duyvv.android.database.dao.TaskDao
import com.duyvv.android.util.app.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext appContext: Context): TaskDatabase {
        return Room.databaseBuilder(
            appContext,
            TaskDatabase::class.java,
            AppConstants.TASK_DB_NAME
        ).build()
    }

    @Provides
    fun provideTaskDao(roomDatabase: TaskDatabase): TaskDao {
        return roomDatabase.taskDao
    }
}
