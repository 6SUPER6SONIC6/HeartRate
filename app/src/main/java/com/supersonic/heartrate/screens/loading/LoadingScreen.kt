package com.supersonic.heartrate.screens.loading

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.supersonic.heartrate.navigation.NavigationDestination

object LoadingScreenDestination : NavigationDestination {
    override val route = "loading"
}
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    onNavigationNext: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNavigationNext.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "LoadingScreen")
    }
}