package com.camc.factory.ui.feature.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camc.factory.data.network.LoginApi
import com.camc.factory.data.network.LoginRequest
import com.camc.factory.data.network.RetrofitHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel2 @Inject constructor(
    @ApplicationContext private val context: Context
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
                    Log.d("Logging", "Response Token: $token")
                    onLoginSuccess?.invoke(token)
                } else {
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