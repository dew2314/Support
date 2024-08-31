package com.eseul.support.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("statusCode")
    @Expose
    var loginStCode: Int,
    @SerializedName("statusMessage")
    @Expose
    var loginMessage: String,
    @SerializedName("responseTime")
    @Expose
    var loginTime: String,
    @SerializedName("data")
    @Expose
    var loginData: LoginData,
    @SerializedName("code")
    @Expose
    var loginCode: String,
)

data class LoginData(
    @SerializedName("accessToken")
    @Expose
    var loginAcToken: String,
    @SerializedName("refreshToken")
    @Expose
    var loginReToken: String,
)

data class ErrorUserModel(
    @SerializedName("statusCode")
    var ErloginStCode: Int,
    @SerializedName("statusMessage")
    var ErloginMessage: String,
    @SerializedName("responseTime")
    var ErloginTime: String,
    @SerializedName("data")
    var ErloginData: String,
    @SerializedName("code")
    var ErloginCode: String,
)
