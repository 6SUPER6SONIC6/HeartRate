package com.supersonic.heartrate.screens.result

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.supersonic.heartrate.navigation.NavigationDestination

object ResultScreenDestination : NavigationDestination {
    override val route = "result"
    const val heartRateIdArg = "heartRateId"
    val routeWithArgs = "$route/{$heartRateIdArg}"
}

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultScreenViewModel,
    onNavigationToHistory: () -> Unit
) {

    val heartRate = viewModel.heartRate
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNavigationToHistory.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(text = "BPM: ${heartRate.bpm}")
            Text(text = "TIME: ${heartRate.time}")
            Text(text = "DATE: ${heartRate.date}")
        }
    }

}