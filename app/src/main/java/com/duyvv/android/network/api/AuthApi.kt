package com.duyvv.android.network.api

import com.duyvv.android.network.model.login.LoginRequest
import com.duyvv.android.network.model.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface AuthApi {
    @GET("/api/signin")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
