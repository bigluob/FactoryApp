package com.camc.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.camc.media.login.LoginViewModel
import com.camc.media.ui.theme.FactoryAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactoryAppTheme {
                // A surface container using the 'background' color from the theme
                LoginScreen(viewModel = loginViewModel) {
                }
            }
        }
    }
}
