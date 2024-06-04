package com.duyvv.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duyvv.android.database.dao.EvaluationDao
import com.duyvv.android.database.dao.ImageDao
import com.duyvv.android.database.dao.RoomDao
import com.duyvv.android.database.dao.UserDao
import com.duyvv.android.database.table.DatabaseEvaluationImage
import com.duyvv.android.database.table.DatabaseRoom
import com.duyvv.android.database.table.DatabaseRoomEvaluation
import com.duyvv.android.database.table.DatabaseRoomImage
import com.duyvv.android.database.table.DatabaseUser


@Database(
    entities = [
        DatabaseRoom::class,
        DatabaseEvaluationImage::class,
        DatabaseRoomEvaluation::class,
        DatabaseRoomImage::class,
        DatabaseUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDatabase : RoomDatabase() {
    abstract val roomsDao: RoomDao
    abstract val evaluationsDao: EvaluationDao
    abstract val imagesDao: ImageDao
    abstract val usersDao: UserDao
}


