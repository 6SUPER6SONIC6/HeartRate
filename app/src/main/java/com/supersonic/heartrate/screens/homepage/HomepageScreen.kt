package com.supersonic.heartrate.screens.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.supersonic.heartrate.navigation.NavigationDestination

object HomepageScreenDestination : NavigationDestination {
    override val route = "homepage"
}

@Composable
fun HomepageScreen(
    modifier: Modifier = Modifier,
    onNavigationToResult: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNavigationToResult.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "HomepageScreen")
    }

}