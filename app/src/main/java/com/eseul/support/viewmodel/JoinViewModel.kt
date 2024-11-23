package com.eseul.support.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eseul.support.model.EmailAuthModel
import com.eseul.support.model.EmailCheckModel
import com.eseul.support.model.JoinUserModel
import com.eseul.support.repository.JoinRepository

class JoinViewModel(private val joinRepository: JoinRepository) : ViewModel() {

    // 회원가입
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
        return joinRepository.signup(portalId, authCode, password, confirmPassword, nickname, gender, dormType, dormNo, roomNo)
    }


    // 이메일 인증 코드 발송
    fun mail(email: String): LiveData<Result<EmailAuthModel>> {
        return joinRepository.sendEmailAuthCode(email)
    }

    // 이메일 인증 코드 확인
    fun mailCheck(email: String, code: String): LiveData<Result<EmailCheckModel>> {
        return joinRepository.verifyEmailCode(email, code)
    }
}
