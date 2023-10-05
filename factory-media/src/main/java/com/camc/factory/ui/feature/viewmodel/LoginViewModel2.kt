package com.camc.factory.ui.feature.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camc.factory.data.network.dto.LoginDto
import com.camc.factory.data.network.repository.RetrofitHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val isSuccessLoading = mutableStateOf(value = false)
    val imageErrorAuth = mutableStateOf(value = false)
    val progressBar = mutableStateOf(value = false)
    private val loginRequestLiveData = MutableLiveData<Boolean>()
    val loginError = mutableStateOf(value = false) // 修改为 MutableState<Boolean>
    var onLoginSuccess: ((String) -> Unit)? = null
    fun login(email: String, password: String) {
        // 创建 SharedPrefsManager 实例
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressBar.value = true
                val authService = RetrofitHelper.getAuthService()
                val responseService =
                    authService.getLogin(LoginDto(username = email, password = password))

                if (responseService.isSuccessful) {
                    delay(1500L)
                    isSuccessLoading.value = true
                    responseService.body()?.let { tokenDto ->
                        val token = tokenDto.accessTokenVerify
                        Log.d("Logging", "Response TokenDto: $tokenDto")
                        onLoginSuccess?.invoke(token)
                    }
                } else {
                    responseService.errorBody()?.let { error ->
                        imageErrorAuth.value = true
                        delay(1500L)
                        imageErrorAuth.value = false
                        error.close()
                        loginError.value = true // 设置登录错误状态为true
                    }
                }

                loginRequestLiveData.postValue(responseService.isSuccessful)
                progressBar.value = false
            } catch (e: Exception) {
                Log.d("Logging", "授权失败", e)
                progressBar.value = false
                loginError.value = true // 设置登录错误状态为truee
            }
        }
    }
}