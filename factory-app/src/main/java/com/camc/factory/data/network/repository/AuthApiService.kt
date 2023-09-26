package com.camc.factory.data.network.repository

import com.camc.factory.data.network.dto.LoginDto
import com.camc.factory.data.network.dto.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("Login")
    suspend fun getLogin(@Body loginDto: LoginDto) : Response<TokenDto>

    /*@POST("auth")
    suspend fun getLogin(@Body loginDto: LoginDto) : Response<TokenDto>*/
}