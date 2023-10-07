package com.camc.media


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.camc.media.login.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToNextScreen: () -> Unit
) {
    var username by remember { mutableStateOf("wuxl1") }
    var password by remember { mutableStateOf("123456") }
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val topPadding = if (screenWidthDp < 600) {
        480.dp // 大屏幕设置为80dp的顶部距离
    } else {
        360.dp // 小屏幕设置为40dp的顶部距离
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = topPadding)
            .width(480.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 添加登录 Logo

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username",
                    modifier = Modifier.padding(end = 8.dp)
                )
                BasicTextField(
                    value = username,
                    onValueChange = { value -> username = value },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VpnKey,
                    contentDescription = "Password",
                    modifier = Modifier.padding(end = 8.dp)
                )
                BasicTextField(
                    value = password,
                    onValueChange = { value -> password = value },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(fontSize = 24.sp)
                )
            }

            // 添加登录按钮
            Button(
                onClick = { viewModel.login(username, password) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Login")
            }

            // 监听登录结果
            val loginResult by viewModel.loginResult.observeAsState()
            loginResult?.let { result ->
                if (result.success) {
                    LaunchedEffect(Unit) {
                        navigateToNextScreen()
                    }
                } else {
                    // 登录失败，显示错误消息
                    Toast.makeText(
                        LocalContext.current,
                        "Login failed: ${result.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // 监听登录进度
            val loading by viewModel.loading.observeAsState(false)
            if (loading) {
                // 显示加载进度条或其他加载提示
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
        }
    }
}