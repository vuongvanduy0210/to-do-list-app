package com.duyvv.android.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: String,
    val url: String
): Parcelable
