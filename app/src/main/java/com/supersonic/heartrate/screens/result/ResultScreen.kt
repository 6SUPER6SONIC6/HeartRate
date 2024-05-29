package com.supersonic.heartrate.screens.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.supersonic.heartrate.navigation.NavigationDestination

object ResultScreenDestination : NavigationDestination {
    override val route = "result"
}

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    onNavigationToHistory: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNavigationToHistory.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "ResultScreen")
    }

}