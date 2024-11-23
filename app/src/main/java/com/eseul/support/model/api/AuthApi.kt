package com.eseul.support.model.api

import com.eseul.support.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {
    companion object {
        fun create(): AuthApi {
            return ApiClient.retrofit.create(AuthApi::class.java)
        }
    }

    @POST("/member/login")
    suspend fun login(@Body body: HashMap<String, String>): Response<UserModel>
}

