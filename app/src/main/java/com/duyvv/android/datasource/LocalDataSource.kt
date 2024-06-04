package com.duyvv.android.datasource

import android.content.SharedPreferences
import com.duyvv.android.base.BaseDataSource
import javax.inject.Inject

interface LocalDataSource

class LocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseDataSource(), LocalDataSource
