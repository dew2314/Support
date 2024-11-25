package com.eseul.support.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserModel (
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
    var erloginStCode: Int,
    @SerializedName("statusMessage")
    var erloginMessage: String,
    @SerializedName("responseTime")
    var erloginTime: String,
    @SerializedName("data")
    var erloginData: String,
    @SerializedName("code")
    var erloginCode: String,
)
