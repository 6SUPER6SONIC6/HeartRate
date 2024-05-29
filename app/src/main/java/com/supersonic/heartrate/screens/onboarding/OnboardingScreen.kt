package com.supersonic.heartrate.screens.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.supersonic.heartrate.navigation.NavigationDestination

object OnboardingScreenDestination : NavigationDestination {
    override val route = "onboarding"
}
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onNavigationToHomepage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNavigationToHomepage.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "OnboardingScreen")
    }
}