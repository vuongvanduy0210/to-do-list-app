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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskEntity)

    @Query("delete from tasks where id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Update(entity = TaskEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTask(task: TaskEntity)
}
