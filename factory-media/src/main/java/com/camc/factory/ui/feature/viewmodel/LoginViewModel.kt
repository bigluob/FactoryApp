package com.camc.factory.ui.feature.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camc.factory.data.network.LoginApi
import com.camc.factory.data.network.LoginRequest
import com.camc.factory.data.network.RetrofitHelper
import com.camc.factory.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {
    val isSuccessLoading = mutableStateOf(value = false)
    val imageErrorAuth = mutableStateOf(value = false)
    val progressBar = mutableStateOf(value = false)
    private val loginRequestLiveData = MutableLiveData<Boolean>()
    val loginError = mutableStateOf(value = false) // 修改为 MutableState<Boolean>
    var onLoginSuccess: ((String) -> Unit)? = null

    private val loginApi: LoginApi = RetrofitHelper.loginApi

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressBar.value = true
                val loginRequest = LoginRequest(
                    userName = email,
                    password = password,
                    code = "",
                    uuid = "",
                    loginIP = ""
                )
                val response = loginApi.login(loginRequest)

                if (response.code == 200) {
                    delay(1500L)
                    isSuccessLoading.value = true
                    val token = response.data.token
                    // Save the token using SharedPreferencesManager
                    sharedPreferencesManager.saveToken(token)
                    onLoginSuccess?.invoke(token)
                    imageErrorAuth.value = true
                    delay(1500L)
                    imageErrorAuth.value = false
                    loginError.value = true
                }

                progressBar.value = false
            } catch (e: Exception) {
                Log.d("Logging", "授权失败", e)
                progressBar.value = false
                loginError.value = true
            }
        }
    }
}