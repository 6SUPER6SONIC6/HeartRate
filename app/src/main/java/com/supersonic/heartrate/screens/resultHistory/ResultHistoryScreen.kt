package com.supersonic.heartrate.screens.resultHistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.supersonic.heartrate.navigation.NavigationDestination

object ResultHistoryScreenDestination : NavigationDestination {
    override val route = "result_history"
}

@Composable
fun ResultHistoryScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "ResultHistoryScreen")
    }

}