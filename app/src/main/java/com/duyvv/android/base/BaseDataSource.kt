package com.duyvv.android.base

import android.database.sqlite.SQLiteException
import com.duyvv.android.util.app.AppConstants
import java.io.IOException

open class BaseDataSource {

    suspend fun <T> safeCallDao(call: suspend () -> T): Response<T> {
        return try {
            val response = call.invoke()
            Response.Success(response)
        } catch (e: SQLiteException) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        } catch (e: IOException) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        } catch (e: Exception) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        }
    }
}
