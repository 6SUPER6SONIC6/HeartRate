package com.supersonic.heartrate.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.supersonic.heartrate.navigation.RootAppNavigation
import com.supersonic.heartrate.ui.theme.HeartRateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartRateTheme {
                RootAppNavigation()
            }
        }
    }
}
