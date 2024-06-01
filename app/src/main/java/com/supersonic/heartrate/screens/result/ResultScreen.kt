package com.supersonic.heartrate.screens.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.BackgroundedSurface
import com.supersonic.heartrate.components.ResultCard
import com.supersonic.heartrate.components.TopBar
import com.supersonic.heartrate.models.HeartRate
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
    onNavigateToHomepage: () -> Unit,
    onNavigationToHistory: () -> Unit
) {
    val heartRate = viewModel.heartRate

    Scaffold(
        topBar = {
            ResultTopBar(
                onNavigationToResultsHistory = onNavigationToHistory
            )
        }
    ){
        ResultScreenContent(
            modifier = modifier.padding(it),
            heartRate = heartRate,
            onNavigateToHomepage = onNavigateToHomepage
        )
    }

}

@Composable
fun ResultTopBar(
    modifier: Modifier = Modifier,
    onNavigationToResultsHistory: () -> Unit
) {
    TopBar(
        title = stringResource(id = R.string.topAppBar_title_result),
        actions = {
            Text(
                text = stringResource(R.string.topAppBar_title_history),
                style = typography.titleSmall,
                color = colorScheme.onPrimary
            )
            IconButton(
                onClick = onNavigationToResultsHistory,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = colorScheme.onPrimary
                )
            ) {
                Icon(painter = painterResource(id = R.drawable.icon_time_machine), contentDescription = null)
            }
        }
    )
}

@Composable
private fun ResultScreenContent(
    modifier: Modifier = Modifier,
    heartRate: HeartRate,
    onNavigateToHomepage: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BackgroundedSurface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5F),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                ResultCard(
                    modifier = Modifier.padding(12.dp),
                    heartRate = heartRate
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onNavigateToHomepage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.resultPage_button2),
                    modifier = Modifier,
                    style = typography.labelMedium
                )
            }
        }
    }
}