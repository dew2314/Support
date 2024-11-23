package com.eseul.support.model.api

import com.eseul.support.model.EmailAuthModel
import com.eseul.support.model.EmailCheckModel
import com.eseul.support.model.JoinUserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinApi {
    companion object{
        fun create(): JoinApi{
            return ApiClient.retrofit.create(JoinApi::class.java)
        }
    }

    // 회원가입 API
    @POST("/member/signup")
    fun signup(@Body body: HashMap<String, String>): Call<JoinUserModel>

    // 이메일 인증 API
    @POST("/member/mail")
    fun mail(@Body body: HashMap<String, String>): Call<EmailAuthModel>

    // 이메일 인증 코드 검증 API
    @POST("/member/mailcheck")
    fun mailCheck(@Body body: HashMap<String, String>): Call<EmailCheckModel>
}