package com.duyvv.android.datasource

import com.duyvv.android.base.BaseDataSource
import com.duyvv.android.base.Response
import com.duyvv.android.database.dao.TaskDao
import com.duyvv.android.database.entity.TaskEntity
import com.duyvv.android.domain.Task
import com.duyvv.android.util.ext.toDomainModel
import javax.inject.Inject

interface LocalDataSource {
    suspend fun getAllTasks(): Response<List<Task>>

    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTask(taskId: Int)

    suspend fun updateTask(task: TaskEntity)
}

class LocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao
) : BaseDataSource(), LocalDataSource {

    override suspend fun getAllTasks(): Response<List<Task>> {
        return safeCallDao {
            taskDao.getAllTasks().map {
                it.toDomainModel()
            }
        }
    }

    override suspend fun insertTask(task: TaskEntity) {
        safeCallDao {
            taskDao.insertTask(task)
        }
    }

    override suspend fun deleteTask(taskId: Int) {
        safeCallDao {
            taskDao.deleteTask(taskId)
        }
    }

    override suspend fun updateTask(task: TaskEntity) {
        safeCallDao {
            taskDao.updateTask(task)
        }
    }
}
