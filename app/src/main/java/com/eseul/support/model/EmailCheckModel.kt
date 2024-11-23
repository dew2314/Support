package com.eseul.support.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmailCheckModel(
    @SerializedName("statusCode")
    @Expose
    var emailCheckSt: Int,
    @SerializedName("statusMessage")
    @Expose
    var emailCheckMessage: String,
    @SerializedName("responseTime")
    @Expose
    var emailCheckTime: String,
    @SerializedName("data")
    @Expose
    var emailCheckData: String,
    @SerializedName("code")
    @Expose
    var emailCheckCode: String
)

data class ErrorEmailCheckModel(
    @SerializedName("statusCode")
    @Expose
    var erEmailCheckSt: Int,
    @SerializedName("statusMessage")
    @Expose
    var erEmailCheckMessage: String,
    @SerializedName("responseTime")
    @Expose
    var erEmailCheckTime: String,
    @SerializedName("data")
    @Expose
    var erEmailCheckData: String,
    @SerializedName("code")
    @Expose
    var erEmailCheckCode: String
)
