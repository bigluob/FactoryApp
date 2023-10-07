package com.camc.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.camc.media.ui.theme.FactoryAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactoryAppTheme {
                // A surface container using the 'background' color from the theme

            }
        }
    }
}
