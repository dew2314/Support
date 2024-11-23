package com.eseul.support.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JoinUserModel(
    @SerializedName("statusCode")
    @Expose
    var joinStCode:Int,
    @SerializedName("statusMessage")
    @Expose
    var joinMessage:String,
    @SerializedName("responseTime")
    @Expose
    var joinTime:String,
    @SerializedName("data")
    @Expose
    var joinData:String,
    @SerializedName("code")
    @Expose
    var joinCode:String,
)

data class ErrorJoinUserModel(
    @SerializedName("statusCode")
    @Expose
    var erJoinStCode: Int,
    @SerializedName("statusMessage")
    @Expose
    var erJoinMessage: String,
    @SerializedName("responseTime")
    @Expose
    var erJoinTime: String,
    @SerializedName("data")
    @Expose
    var erJoinData: String,
    @SerializedName("code")
    @Expose
    var erJoinCode: String
)
data class ErJoinIdModel(
    @SerializedName("statusCode")
    @Expose
    var erJoinIdStCode: Int,
    @SerializedName("statusMessage")
    @Expose
    var erJoinIdMessage: String,
    @SerializedName("responseTime")
    @Expose
    var erJoinIdTime: String,
    @SerializedName("data")
    @Expose
    var erJoinIdData: String,
    @SerializedName("code")
    @Expose
    var erJoinIdCode: String
)
