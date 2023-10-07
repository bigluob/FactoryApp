package com.camc.factorymediav2.login

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val loginApiService: LoginApiService) {
    suspend fun login(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password, "string", "string", "string")
        return loginApiService.login(request)
    }
}
