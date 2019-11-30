package com.lrm.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("userName") val id : String?,
                @SerializedName("password") val password : String?,
                @SerializedName("userType") val userType : String,
                @SerializedName("en") val appLanguagePref : String)