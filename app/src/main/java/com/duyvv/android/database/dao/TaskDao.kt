package com.duyvv.android.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.duyvv.android.database.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("select * from tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("select * from tasks where priority = :priority and isCompleted = :status")
    suspend fun getTasks(priority: Int, status: Boolean): List<TaskEntity>

    @Query("select * from tasks where priority = :priority")
    suspend fun getTasks(priority: Int): List<TaskEntity>

    @Query("select * from tasks where isCompleted = :status")
    suspend fun getTasks(status: Boolean): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskEntity)

    @Query("delete from tasks where id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Update(entity = TaskEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTask(task: TaskEntity)
}
