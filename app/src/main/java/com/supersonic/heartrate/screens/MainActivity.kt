package com.supersonic.heartrate.screens

import android.content.pm.ActivityInfo
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
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            HeartRateTheme {
                RootAppNavigation()
            }
        }
    }
}
