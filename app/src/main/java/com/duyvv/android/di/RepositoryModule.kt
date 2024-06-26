package com.duyvv.android.di

import com.duyvv.android.repository.TaskRepository
import com.duyvv.android.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository {
        return taskRepositoryImpl
    }
}
