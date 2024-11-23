package com.eseul.support.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eseul.support.model.EmailAuthModel
import com.eseul.support.model.EmailCheckModel
import com.eseul.support.model.JoinUserModel
import com.eseul.support.model.api.JoinApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinRepository(private val joinApi: JoinApi) {

    fun signup(
        portalId: String,
        authCode: String,
        password: String,
        confirmPassword: String,
        nickname: String,
        gender: String,
        dormType: String,
        dormNo: String,
        roomNo: String
    ): LiveData<Result<JoinUserModel>> {
        val result = MutableLiveData<Result<JoinUserModel>>()
        val body = hashMapOf(
            "loginId" to portalId,
            "password" to password,
            "nickname" to nickname,
            "gender" to gender,
            "dormType" to dormType,
            "dormNo" to dormNo,
            "roomNo" to roomNo
        )

        joinApi.signup(body).enqueue(object : Callback<JoinUserModel> {
            override fun onResponse(call: Call<JoinUserModel>, response: Response<JoinUserModel>) {
                if (response.isSuccessful && response.body() != null) {
                    result.postValue(Result.success(response.body()!!))
                } else {
                    result.postValue(Result.failure(Exception("회원가입 실패: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<JoinUserModel>, t: Throwable) {
                result.postValue(Result.failure(t))
            }
        })

        return result
    }


    // 이메일 인증 요청
    fun sendEmailAuthCode(email: String): LiveData<Result<EmailAuthModel>> {
        val result = MutableLiveData<Result<EmailAuthModel>>()
        val body: HashMap<String, String> = hashMapOf("loginId" to email)

        joinApi.mail(body).enqueue(object : Callback<EmailAuthModel> {
            override fun onResponse(call: Call<EmailAuthModel>, response: Response<EmailAuthModel>) {
                if (response.isSuccessful && response.body() != null) {
                    result.postValue(Result.success(response.body()!!))
                } else {
                    result.postValue(Result.failure(Exception("인증코드 전송 실패: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<EmailAuthModel>, t: Throwable) {
                result.postValue(Result.failure(t))
            }
        })

        return result
    }

    // 이메일 인증 코드 확인 요청
    fun verifyEmailCode(email: String, code: String): LiveData<Result<EmailCheckModel>> {
        val result = MutableLiveData<Result<EmailCheckModel>>()
        val body: HashMap<String, String> = hashMapOf("loginId" to email, "authCode" to code)

        joinApi.mailCheck(body).enqueue(object : Callback<EmailCheckModel> {
            override fun onResponse(call: Call<EmailCheckModel>, response: Response<EmailCheckModel>) {
                if (response.isSuccessful && response.body() != null) {
                    result.postValue(Result.success(response.body()!!))
                } else {
                    result.postValue(Result.failure(Exception("이메일 인증 실패: ${response.message()}")))
                }
            }

            override fun onFailure(call: Call<EmailCheckModel>, t: Throwable) {
                result.postValue(Result.failure(t))
            }
        })

        return result
    }
}


