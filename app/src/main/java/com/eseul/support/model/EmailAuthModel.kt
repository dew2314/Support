package com.eseul.support.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmailAuthModel(
    @SerializedName("statusCode")
    @Expose
    var emailStCode: Int,
    @SerializedName("statusMessage")
    @Expose
    var emailMessage: String,
    @SerializedName("responseTime")
    @Expose
    var emailTime: String,
    @SerializedName("data")
    @Expose
    var emailData: String,
    @SerializedName("code")
    @Expose
    var emailCode: String
)

data class ErrorEmailServerModel(
    @SerializedName("statusCode")
    @Expose
    var erEmailStCode: Int,
    @SerializedName("statusMessage")
    @Expose
    var erEmailMessage: String,
    @SerializedName("responseTime")
    @Expose
    var erEmailTime: String,
    @SerializedName("data")
    @Expose
    var erEmailData: String,
    @SerializedName("code")
    @Expose
    var erEmailCode: String
)

data class ErrorEmailIdModel(
    @SerializedName("statusCode")
    @Expose
    var erEmailIdSt: Int,
    @SerializedName("statusMessage")
    @Expose
    var erEmailIdMessage: String,
    @SerializedName("responseTime")
    @Expose
    var erEmailIdTime: String,
    @SerializedName("data")
    @Expose
    var erEmailIdData: String,
    @SerializedName("code")
    @Expose
    var erEmailCode: String
)
