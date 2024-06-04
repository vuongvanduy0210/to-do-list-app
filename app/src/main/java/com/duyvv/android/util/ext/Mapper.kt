package com.duyvv.android.util.ext

import com.duyvv.android.database.entity.TaskEntity
import com.duyvv.android.domain.Task

fun Task.toTaskEntity() = TaskEntity(

)

fun TaskEntity.toDomainModel() = Task(

)
