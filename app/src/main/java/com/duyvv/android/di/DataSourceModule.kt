package com.duyvv.android.di

import com.duyvv.android.datasource.LocalDataSource
import com.duyvv.android.datasource.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource {
        return localDataSourceImpl
    }
}
