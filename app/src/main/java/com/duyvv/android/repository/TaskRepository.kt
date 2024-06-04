package com.duyvv.android.repository

import com.duyvv.android.datasource.LocalDataSource
import javax.inject.Inject

interface TaskRepository

class TaskRepositoryImpl @Inject constructor(
    localDataSource: LocalDataSource
) : TaskRepository
