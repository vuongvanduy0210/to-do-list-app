package com.duyvv.android.di

import com.duyvv.android.repository.RoomRepository
import com.duyvv.android.repository.RoomRepositoryImpl
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
    fun provideRoomRepository(): RoomRepository {
        return RoomRepositoryImpl()
    }
}
