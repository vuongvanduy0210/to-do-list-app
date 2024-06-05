package com.duyvv.android.repository

import com.duyvv.android.base.Response
import com.duyvv.android.datasource.LocalDataSource
import com.duyvv.android.domain.Task
import com.duyvv.android.util.ext.toTaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TaskRepository {
    suspend fun getAllTasks(): Response<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(id: Int)

    suspend fun updateTask(task: Task)
}

class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : TaskRepository {
    override suspend fun getAllTasks(): Response<List<Task>> = withContext(Dispatchers.IO) {
        localDataSource.getAllTasks()
    }

    override suspend fun insertTask(task: Task) = withContext(Dispatchers.IO) {
        localDataSource.insertTask(task.toTaskEntity())
    }

    override suspend fun deleteTask(id: Int) = withContext(Dispatchers.IO) {
        localDataSource.deleteTask(id)
    }

    override suspend fun updateTask(task: Task) {
        localDataSource.updateTask(
            task.toTaskEntity().apply {
                this.id = task.id ?: 0
            }
        )
    }
}
