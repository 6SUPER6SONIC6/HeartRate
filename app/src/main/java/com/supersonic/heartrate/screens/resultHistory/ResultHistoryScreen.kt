package com.supersonic.heartrate.screens.resultHistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.supersonic.heartrate.R
import com.supersonic.heartrate.components.BackgroundedSurface
import com.supersonic.heartrate.components.HistoryCard
import com.supersonic.heartrate.components.TopBar
import com.supersonic.heartrate.models.HeartRate
import com.supersonic.heartrate.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ResultHistoryScreenDestination : NavigationDestination {
    override val route = "result_history"
}

@Composable
fun ResultHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultHistoryViewModel,
    onBackClick: () -> Unit
) {
    val heartRatesList by viewModel.heartRatesList.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ResultHistoryTopBar(
                onBackClick = onBackClick
            )
        }
    ) {
        ResultHistoryScreenContent(
            modifier = Modifier
                .padding(it),
            heartRatesList = heartRatesList,
            onClearHistoryClick = {
                scope.launch {
                    viewModel.clearHistory()
                }
            }
        )
    }

}

@Composable
private fun ResultHistoryTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    TopBar(
        modifier = modifier,
        title = stringResource(id = R.string.topAppBar_title_history),
        isBackIconEnabled = true,
        onBackClick = onBackClick
    )
}
@Composable
private fun ResultHistoryScreenContent(
    modifier: Modifier = Modifier,
    heartRatesList: List<HeartRate>,
    onClearHistoryClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BackgroundedSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5F),
            ){}

            Box(
                modifier = Modifier
                    .weight(1F),
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
        ) {
            items(heartRatesList, key = { it.id }) { heartRate ->
                HistoryCard(
                    heartRate = heartRate,
                    modifier = Modifier.padding(8.dp)
                )
            }

            item {
                Button(
                    onClick = onClearHistoryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.historyPage_button1),
                        modifier = Modifier,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}