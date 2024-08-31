package com.eseul.support.model.api

import com.eseul.support.model.UserModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface AuthApi {
    companion object {
        fun create(): AuthApi {
            return ApiClient.retrofit.create(AuthApi::class.java)
        }
    }

    @FormUrlEncoded
    @POST("/member/login")
    fun login(@FieldMap body: HashMap<String, String>): Call<UserModel>
}

