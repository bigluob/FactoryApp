package com.camc.factory.ui.feature.page.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.camc.factory.ui.feature.page.login.components.ButtonLogin
import com.camc.factory.ui.feature.page.login.components.ErrorImageAuth
import com.camc.factory.ui.feature.page.login.components.ImageLogin
import com.camc.factory.ui.feature.page.login.components.PasswordOutTextField
import com.camc.factory.ui.feature.page.login.components.UsernameTextFiled
import com.camc.factory.ui.feature.viewmodel.LoginViewModel
import com.camc.factory.ui.feature.viewmodel.LoginViewModelFactory
import com.camc.factory.utils.EncryptionUtils
import com.camc.factory.utils.SharedPreferencesManager
import com.camc.facttory.R
import com.davidorellana.logincomposeretrofit2.ui.login.components.ProgressBarLoading

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loadingProgressBar: Boolean,
    onclickLogin: (username: String, password: String) -> Unit,
    imageError: Boolean,
    onLoginSuccess: (String) -> Unit
) {
    var username by rememberSaveable { mutableStateOf(value = "wuxl1") }
    var password by rememberSaveable { mutableStateOf(value = "123456") }
    val isValidate by derivedStateOf { username.isNotBlank() && password.isNotBlank() }
    val focusManager = LocalFocusManager.current
    var loginError by remember { mutableStateOf(false) }
    var token by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
    // 创建 LoginViewModel 实例
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(sharedPreferencesManager)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = Color.White)
    ) {
        Column(
            modifier = modifier
                .width(600.dp)
                .padding(horizontal = dimensionResource(id = R.dimen.column_horizontal_padding))
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageLogin()
            Spacer(modifier = Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(4.dp)
            ) {
                UsernameTextFiled(
                    textValue = username,
                    onValueChange = { username = it },
                    onClickButton = { username = "" },
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(Color.LightGray)
            ) {
                PasswordOutTextField(
                    textValue = password,
                    onValueChange = { password = it },
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            }

            val encryptedPassword = remember(password) { EncryptionUtils.encryptMD5(password) }

            ButtonLogin(
                onclick = { onclickLogin(username, encryptedPassword) },
                enabled = isValidate
            )

            // 登录失败提示
            if (imageError) {
                Text(text = "登录失败，请检查用户名和密码", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    ErrorImageAuth(isImageValidate = imageError)

    ProgressBarLoading(isLoading = loadingProgressBar)

    // 登录成功时调用回调函数并传递 Token
    LaunchedEffect(Unit) {
        if (!loadingProgressBar && !imageError && username.isNotBlank() && password.isNotBlank()) {

            loginViewModel.onLoginSuccess = { token ->
                sharedPreferencesManager.saveToken(token)
                onLoginSuccess.invoke(token)
                Log.d("LoginActivity", "登录成功" + sharedPreferencesManager.getToken())
            }
            loginViewModel.login(username, password)
        }
    }
    // 显示保存的令牌
    //  TokenDisplayScreen(sharedPreferencesManager)
}