package com.duyvv.android.util.ext

import com.duyvv.android.database.entity.TaskEntity
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.util.app.FormatUtils

fun Task.toTaskEntity() = TaskEntity(
    title = title,
    description = description,
    time = FormatUtils.convertCalendarToString(time)
)

fun TaskEntity.toDomainModel() = Task(
    id = id,
    title = title,
    description = description,
    time = FormatUtils.convertStringToCalendar(time),
    status = TaskStatus.UPCOMING
)
