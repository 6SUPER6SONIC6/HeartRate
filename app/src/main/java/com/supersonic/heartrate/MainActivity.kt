package com.supersonic.heartrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.supersonic.heartrate.navigation.RootAppNavigation
import com.supersonic.heartrate.ui.theme.HeartRateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeartRateTheme {
                RootAppNavigation()
            }
        }
    }
}
