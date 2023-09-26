package com.camc.factory.data.network.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
